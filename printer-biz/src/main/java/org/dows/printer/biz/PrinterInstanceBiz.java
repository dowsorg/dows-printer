package org.dows.printer.biz;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.order.api.OrderInstanceBizApiService;
import org.dows.order.bo.OrderInstanceQueryBo;
import org.dows.order.vo.OrderInstanceInfoVo;
import org.dows.printer.api.request.PrintContentRequest;
import org.dows.printer.dto.PrintContentDTO;
import org.dows.printer.dto.PrintNoticeDTO;
import org.dows.printer.entity.PrintHistoryEntity;
import org.dows.printer.entity.PrinterInstanceEntity;
import org.dows.printer.entity.PrinterStateEntity;
import org.dows.printer.enums.PrintStateEnum;
import org.dows.printer.service.PrintHistoryService;
import org.dows.printer.service.PrinterInstanceService;
import org.dows.printer.service.PrinterStateService;
import org.dows.printer.utils.PandStringUtils;
import org.dows.printer.utils.third.print.DaquPrintVo;
import org.dows.printer.utils.third.print.JuhePrintContentUtils;
import org.dows.printer.utils.third.print.JuhePrintUtils;
import org.dows.printer.vo.DetailDishesVo;
import org.dows.printer.vo.JuhePrintContentVo;
import org.dows.printer.vo.PrintTypeVo;
import org.dows.printer.vo.PrinterInstanceVO;
import org.dows.store.api.StoreInstanceApi;
import org.dows.store.api.response.StoreResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrinterInstanceBiz {

    private final PrinterInstanceService printerInstanceService;
    private final PrintHistoryService printHistoryService;
    private final PrinterStateService printerStateService;
    private final JuhePrintUtils juhePrintUtils;
    private final OrderInstanceBizApiService orderInstanceBizApiService;
    private final StoreInstanceApi storeInstanceApi;

    private static final Map<String, String> PRINTERSEAT = new ConcurrentHashMap<>() {{
        put("1", "前厅打印机");
        put("2", "后厨打印机");
    }};

    /**
     * 商户获取打印机
     *
     * @param storeId 门店id String必填
     */
    public Response getPrinterList(String storeId) {
        List<PrinterInstanceVO> printvos = new LinkedList<>();
        try {
            List<PrinterInstanceEntity> prints = printerInstanceService.lambdaQuery().eq(PrinterInstanceEntity::getStoreId, storeId).list();
            //添加打印机状态和打印机关联属性
            printvos = prints.stream().map(p -> {
                PrinterInstanceVO vo = BeanUtil.copyProperties(p, PrinterInstanceVO.class);
                //添加打印机状态
                vo.setPrinterStatus(setPrinterStatus(p.getPrinterNo()));
                //打印机关联属性
                return vo;
            }).collect(Collectors.toList());

            //异步更新打印机状态
            syncUpPrintStatus(prints);
        } catch (Exception e) {
            log.info("打印机列表获取异常: {}", e.getMessage());
        }
        return Response.ok(printvos);
    }


    /**
     * 打印机绑定
     *
     * @param
     * @return
     */
    @Transactional
    public Response bindPrinter(PrinterInstanceEntity printerInstance) {
        try {
            //获取打印机位置
            if (PandStringUtils.isBlank(printerInstance.getPrinterSeat())) {
                printerInstance.setPrinterSeat("1");//默认前厅
            }
            //判断打印机前厅和后厨是否存在多个
            Long printerCount = printerInstanceService.lambdaQuery()
                    .eq(PrinterInstanceEntity::getStoreId, printerInstance.getStoreId())
                    .eq(PrinterInstanceEntity::getPrinterSeat, printerInstance.getPrinterSeat())
                    .count();
            //添加打印机名称
            String printerName = PRINTERSEAT.get(printerInstance.getPrinterSeat());
            if (printerCount > 0) {
                printerName = printerName + (printerCount + 1);
            }

            printerInstance.setPrinterName(printerName);

            //进行绑定
            boolean newBind = true;
            //检测设备是否绑定过
            List<DaquPrintVo> voList = Lists.newArrayList();
            DaquPrintVo vo = new DaquPrintVo();
            vo.setKey(printerInstance.getPrinterKey());
            vo.setName(printerInstance.getPrinterName());
            vo.setSn(printerInstance.getPrinterNo());
            voList.add(vo);
            //进行绑定操作
            JSONObject addPrinterRes = juhePrintUtils.addPrinter(voList);
            if (ObjectUtils.isEmpty(addPrinterRes) || 0 != addPrinterRes.getInteger("code")) {//不成功
                if (31412 == addPrinterRes.getInteger("code")) {//设备已绑定
                    newBind = false;
                } else {
                    String message = addPrinterRes.getString("message");
                    log.info("addPrinterRes失败: {}", addPrinterRes);
                    return Response.fail("绑定异常");
                }
            }
            //新设备进行墨浓度设置
            juhePrintUtils.setDensity(printerInstance.getPrinterNo(), 5);
            //保存绑定打印机信息
            printerInstanceService.saveOrUpdate(printerInstance);
            //保存打印机状态
            PrinterStateEntity printerStateEntity = PrinterStateEntity.builder().printerNo(printerInstance.getPrinterNo()).build();
            if (0 == printerStateService.count(Wrappers.lambdaQuery(printerStateEntity))) {
                printerStateService.save(printerStateEntity);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("打印机绑定异常" + e.getMessage(), e);
            return Response.fail("操作失败");
        }
        return Response.ok("操作成功");
    }


    /**
     * 查看打印机状态
     *
     * @param sn 设备编号
     * @return
     */
    public Response<JSONObject> getPrinterStatus(String sn) {
        try {
            JSONObject res = juhePrintUtils.getPrinterStatus(sn);
            if (ObjectUtils.isNotEmpty(res) && res.getInteger("code") == 0) {
                return Response.ok(res.getJSONObject("data"));
            } else {
                return Response.fail(res.getString("message"));
            }
        } catch (Exception e) {
            log.error("查看打印机状态异常" + e.getMessage(), e);
            return Response.fail(e.getMessage());
        }
    }


    /**
     * 测试打印机
     *
     * @param printSn 打印机序列号 String必填
     */
    public Response printerTest(String printSn) {
        try {
            JSONObject printParse = juhePrintUtils.getPrinterStatus(printSn);
            Integer bindCode = printParse.getInteger("code");//{"message":"无效设备SN编码","code":31414}
            if (bindCode != 0) {
                return Response.fail(printParse.getString("message"));
            }
            //	   - onlineStatus	设备联网状态: 0 当前离线 1 在线
            //     - workStatus	设备工作状态: 0 就绪, 1 打印中, 2 缺纸, 3 过温, 4 打印故障
            //     - workStatusDesc	设备工作状态说明
            JSONObject jsonObject = printParse.getJSONObject("data");
            if (jsonObject.getInteger("onlineStatus") == 1 && jsonObject.getInteger("workStatus") == 0) {
//				return PandResponseUtil.printJson("操作成功", "可尝试打印");
            } else {
                return Response.fail(jsonObject.getString("workStatusDesc"));
            }

            //开始打印
            //菜品
            List<DetailDishesVo> voList = new LinkedList<>();
            voList.add(new DetailDishesVo("龙虾泡饭", 1, 38.00, null, null));
            voList.add(new DetailDishesVo("芝士焗咖喱牛腩牛腩饭", 1, 38.00, null, null));
            voList.add(new DetailDishesVo("炙烤猪扒饭", 1, 38.00, null, null));
            //组装打印内容-总单
            JuhePrintContentVo JuhePrintContentVo = new JuhePrintContentVo("五天日记【松江印象城店】", "A01", "6", "刘大牙", voList, "98.60", "苏庆北路246号印象城B1-901", "021-09001234", 1, null);
            String content = JuhePrintContentUtils.getMasterContent(JuhePrintContentVo);
            log.info("打印信息：{}" + content);
            JSONObject print = juhePrintUtils.print(printSn, content, true);
            log.info("打印测试返回：{}", print);
            return Response.ok(1, "打印中");
        } catch (Exception e) {
            log.error("测试打印异常：{}" + e.getMessage(), e);
            return Response.fail("服务器升级");
        }
    }

    /**
     * 进行打印
     *
     * @param -sn      打印机编号
     * @param -content 内容
     */
    public Response printByContent(PrintContentRequest printContentRequest) {
        syncPrint(printContentRequest.getPrinterNo(), "", printContentRequest.getContent());
        return Response.ok("打印中");
    }

    /**
     * 进行打印
     *
     * @param -sn      打印机编号
     * @param -content 内容
     */
    public Response printByContent(PrintContentDTO printContentDTO) {
        syncPrint(printContentDTO.getPrinterNo(), "", printContentDTO.getContent());
        return Response.ok("打印中");
    }

    /**
     * 异步通知小票打印状态
     *
     * @param -type  回调消息类型  5 打印请求状态变更, 6 设备状态发生更新(开发中)
     * @param -rtime 回调时间(unix timestamp 秒)
     * @param -data  回调业务内容  json string 例: "{"printId":"819001823847712734","status":"0"}" [0 待打印, 1 打印中, 2 成功, 3 失败, 4 已取消]
     */
    public JSONObject synPrintNotice(PrintNoticeDTO printNoticeDTO) {
        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "fail");
        try {
            if (5 == printNoticeDTO.getType()) {
                JSONObject object = JSONObject.parseObject(printNoticeDTO.getData());
                Long printId = object.getLong("printId");
                Integer status = object.getInteger("status");

                //变更历史状态
                LambdaUpdateChainWrapper<PrintHistoryEntity> updateWrapper = printHistoryService.lambdaUpdate()
                        .eq(PrintHistoryEntity::getPrintId, printId)
                        .set(PrintHistoryEntity::getStateCode, status);

                //判断是否成功
                if (2 == status) {
                    updateWrapper.set(PrintHistoryEntity::getFlag, true);
                }

                if (updateWrapper.update()) {
                    result.put("code", 0);
                    result.put("message", "ok");
                }

            }
        } catch (Exception e) {
            log.error("异步通知小票打印状态失败：{}" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 打印总单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    public Response printMasterOrder(String orderNo) {
        try {
            //通过订单id获取订单信息
            OrderInstanceQueryBo orderInstanceQueryBo = new OrderInstanceQueryBo();
            orderInstanceQueryBo.setOrderNo(orderNo);
            OrderInstanceInfoVo orderInstanceInfoVo = orderInstanceBizApiService.queryOrderInfoOne(orderInstanceQueryBo);
            if (ObjectUtils.isEmpty(orderInstanceInfoVo)) {
                return Response.fail("订单不存在！");
            }
            //获取订单的商铺id
            String storeId = orderInstanceInfoVo.getStoreId();
            //菜品
            List<DetailDishesVo> voList = orderInstanceInfoVo.getGoodSpuInfoList().stream().map(g -> new DetailDishesVo(g.getGoodName(), g.getQuantity(), g.getPrice().doubleValue(), g.getRemark(), null)).collect(Collectors.toList());
            //获取店铺信息
            StoreResponse storeResponse = storeInstanceApi.getStoreById(storeId);
            //组装打印内容-总单
            JuhePrintContentVo juhePrintContentVo = new JuhePrintContentVo(
                    storeResponse.getName(),
                    orderInstanceInfoVo.getTableNo(),
                    "" + orderInstanceInfoVo.getPeoples(),
                    ObjectUtils.isNotEmpty(orderInstanceInfoVo.getUserInfo()) ? orderInstanceInfoVo.getUserInfo().getName() : "",
                    voList,
                    "" + orderInstanceInfoVo.getTotalAmount(),
                    storeResponse.getAddress(),
                    storeResponse.getContactPhone(),
                    1, null);

            //通过商铺id获取绑定的打印机
            List<PrinterInstanceEntity> prints = printerInstanceService.lambdaQuery().eq(PrinterInstanceEntity::getStoreId, storeId).list();
            if (CollectionUtils.isEmpty(prints)) {
                return Response.fail("无可用在线就绪打印机！");
            }

            Map<String, String> printMap = getprintMap(prints, juhePrintContentVo, voList, orderInstanceInfoVo);

            if (printMap.isEmpty()) {
                return Response.fail("无可用打印机！");
            }

            //进行打印
            printMap.forEach((k, v) -> syncPrint(k, orderNo, v));

            return Response.ok(1, "打印中");
        } catch (Exception e) {
            log.error("状态异常" + e.getMessage(), e);
            return Response.fail("打印失败");
        }
    }

    /**
     * 获取打印任务
     */
    private Map<String, String> getprintMap(List<PrinterInstanceEntity> printers, JuhePrintContentVo juhePrintContentVo, List<DetailDishesVo> voList, OrderInstanceInfoVo orderInstanceInfoVo) {
        //创建打印任务
        Map<String, String> printMap = new ConcurrentHashMap<String, String>();
        //获取打印机编号
        List<String> printerNos = printers.stream().map(p -> p.getPrinterNo()).collect(Collectors.toList());
        //获取在线就绪的打印机编号
        List<String> workPrinterNos = printerStateService.lambdaQuery()
                .in(PrinterStateEntity::getPrinterNo, printerNos)
                .eq(PrinterStateEntity::getWorkStatus, 0)
                .list()
                .stream()
                .map(p -> p.getPrinterNo())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(workPrinterNos)) {
            return printMap;
        }
        //将前台后厨打印机分组
        Map<String, List<PrinterInstanceEntity>> printersMap = printers.stream().filter(p -> workPrinterNos.contains(p.getPrinterNo())).collect(Collectors.groupingBy(PrinterInstanceEntity::getPrinterSeat));
        //前厅打印机
        List<PrinterInstanceEntity> printerQT = printersMap.get("1");
        //后厨打印机
        List<PrinterInstanceEntity> printerHC = printersMap.get("2");

        //匹配打印机和打印内容
        //前厅任务
        setPrintQT(printMap, printerQT, juhePrintContentVo, voList, orderInstanceInfoVo);
        //后厨任务
        setPrintHC(printMap, printerHC, juhePrintContentVo, voList, orderInstanceInfoVo);
        //如果没有匹配打印机任务-找一台就绪的进行打印
        if (printMap.isEmpty()) {
            printMap.put(workPrinterNos.get(0), JuhePrintContentUtils.getContent(juhePrintContentVo));
        }
        return printMap;
    }

    /**
     * 添加前厅打印任务
     */
    private Map<String, String> setPrintQT(Map<String, String> printMap, List<PrinterInstanceEntity> printerQT, JuhePrintContentVo juhePrintContentVo, List<DetailDishesVo> voList, OrderInstanceInfoVo orderInstanceInfoVo) {
        //前厅判断
        if (CollectionUtils.isNotEmpty(printerQT)) {
            Integer type = juhePrintContentVo.getType();
            if (printerQT.size() == 1) {
                PrinterInstanceEntity pi = printerQT.get(0);
                //获取打印内容 1-总单  2-结账单（优惠信息）  3、制作单  4、加菜  5退菜
                if (1 == type || 2 == type) {
                    String content = JuhePrintContentUtils.getContent(juhePrintContentVo);
                    printMap.put(pi.getPrinterNo(), content);
                }
                //是否打印制作单
                if (1 == pi.getMakingStatus()) {
                    voList.forEach(v -> {
                        List<DetailDishesVo> dv = new ArrayList<>();
                        dv.add(v);
                        juhePrintContentVo.setDishesVoList(dv);
                        if (1 == type || 2 == type) {
                            juhePrintContentVo.setType(3);
                        }
                        printMap.put(pi.getPrinterNo(), JuhePrintContentUtils.getContent(juhePrintContentVo));
                    });
                }
            } else {
                //获取桌号
                String tableNo = orderInstanceInfoVo.getTableNo();
                //根据桌号获取桌号对应桌号区域
                String roomName = printerInstanceService.getRoomNameByTableNo(Wrappers.query()
                        .eq("table_no", tableNo)
                        .eq("store_id", orderInstanceInfoVo.getStoreId()).last("LIMIT 1"));
                if (ObjectUtils.isEmpty(roomName)) {
                    return printMap;
                }
                //筛选对应打印机
                printerQT.stream()
                        .filter(qt -> qt.getPrintArea().contains(roomName))
                        .collect(Collectors.toList())
                        .forEach(p -> {
                            //获取打印内容
                            if (1 == type || 2 == type) {
                                String content = JuhePrintContentUtils.getContent(juhePrintContentVo);
                                printMap.put(p.getPrinterNo(), content);
                            }
                            //是否打印制作单
                            if (1 == p.getMakingStatus()) {
                                voList.forEach(v -> {
                                    List<DetailDishesVo> dv = new ArrayList<>();
                                    dv.add(v);
                                    juhePrintContentVo.setDishesVoList(dv);
                                    if (1 == type || 2 == type) {
                                        juhePrintContentVo.setType(3);
                                    }
                                    printMap.put(p.getPrinterNo(), JuhePrintContentUtils.getContent(juhePrintContentVo));
                                });
                            }
                        });
            }
        }
        return printMap;
    }

    /**
     * 添加后厨打印任务
     */
    private Map<String, String> setPrintHC(Map<String, String> printMap, List<PrinterInstanceEntity> printerHC, JuhePrintContentVo juhePrintContentVo, List<DetailDishesVo> voList, OrderInstanceInfoVo orderInstanceInfoVo) {
        //后厨判断
        if (CollectionUtils.isNotEmpty(printerHC)) {
            Integer type = juhePrintContentVo.getType();
            if (printerHC.size() == 1) {
                PrinterInstanceEntity pi = printerHC.get(0);
                //获取打印内容
                if (1 == type || 2 == type) {
                    String content = JuhePrintContentUtils.getContent(juhePrintContentVo);
                    printMap.put(pi.getPrinterNo(), content);
                }
                //是否打印制作单
                if (1 == pi.getMakingStatus()) {
                    voList.forEach(v -> {
                        List<DetailDishesVo> dv = new ArrayList<>();
                        dv.add(v);
                        juhePrintContentVo.setDishesVoList(dv);
                        if (1 == type || 2 == type) {
                            juhePrintContentVo.setType(3);
                        }
                        printMap.put(pi.getPrinterNo(), JuhePrintContentUtils.getContent(juhePrintContentVo));
                    });
                }
            } else {
                //打印机类型与编号
                Map<String, String> printTypeMap = new ConcurrentHashMap<String, String>();
                printerHC.forEach(p -> {
                    String printType = p.getPrintType();
                    if (ObjectUtils.isNotEmpty(printType)) {
                        String[] split = printType.split(",");
                        for (String s : split) {
                            printTypeMap.put(s, p.getPrinterNo());
                        }
                    }
                });
                //获取菜品类型
                List<Long> orderItemIds = orderInstanceInfoVo.getGoodSpuInfoList().stream().map(g -> g.getOrderItemId()).collect(Collectors.toList());
                //菜品与类型
                List<PrintTypeVo> spuPrintType = printerInstanceService.getSpuPrintType(Wrappers.query().in("oi.id", orderItemIds).groupBy("oi.spu_name"));
                Map<String, String> categoryMap = spuPrintType.stream().collect(Collectors.toMap(PrintTypeVo::getSpuName, PrintTypeVo::getCategoryName));
                //将菜品通过类型与打印机类型匹配
                for (DetailDishesVo dishesVo : voList) {
                    String category = categoryMap.get(dishesVo.getDishesName());
                    if (ObjectUtils.isNotEmpty(category)) {
                        String[] split = category.split(",");
                        for (String s : split) {
                            String printerNo = printTypeMap.get(s);
                            if (ObjectUtils.isNotEmpty(printTypeMap.get(s))) {
                                dishesVo.setPrinterNo(printTypeMap.get(s));
                                break;
                            }
                        }
                    }
                    if (ObjectUtils.isEmpty(dishesVo.getPrinterNo())) {
                        dishesVo.setPrinterNo(printerHC.get(0).getPrinterNo());
                    }
                }
                //获取打印机对应打印菜单
                Map<String, List<DetailDishesVo>> printerHCMap = voList.stream().collect(Collectors.groupingBy(DetailDishesVo::getPrinterNo));
                printerHC.forEach(p -> {
                    List<DetailDishesVo> detailDishesVos = printerHCMap.get(p.getPrinterNo());
                    if (CollectionUtils.isNotEmpty(detailDishesVos)) {
                        if (1 == type || 2 == type) {
                            juhePrintContentVo.setDishesVoList(detailDishesVos);
                            printMap.put(p.getPrinterNo(), JuhePrintContentUtils.getContent(juhePrintContentVo));
                        }
                        if (1 == p.getMakingStatus()) {
                            detailDishesVos.forEach(v -> {
                                List<DetailDishesVo> dv = new ArrayList<>();
                                dv.add(v);
                                juhePrintContentVo.setDishesVoList(dv);
                                if (1 == type || 2 == type) {
                                    juhePrintContentVo.setType(3);
                                }
                                printMap.put(p.getPrinterNo(), JuhePrintContentUtils.getContent(juhePrintContentVo));
                            });
                        }
                    }
                });
            }
        }
        return printMap;
    }

    /**
     * 打印制作单
     *
     * @param orderNo 订单id String必填
     */
    public Response printMakeOrder(String orderNo, Long orderItemId, Integer type) {
        try {
            //通过订单id获取订单信息
            OrderInstanceQueryBo orderInstanceQueryBo = new OrderInstanceQueryBo();
            orderInstanceQueryBo.setOrderNo(orderNo);
            OrderInstanceInfoVo orderInstanceInfoVo = orderInstanceBizApiService.queryOrderInfoOne(orderInstanceQueryBo);
            if (ObjectUtils.isEmpty(orderInstanceInfoVo)) {
                return Response.fail("订单不存在！");
            }
            //获取订单的商铺id
            String storeId = orderInstanceInfoVo.getStoreId();
            //菜品
            List<DetailDishesVo> voList = orderInstanceInfoVo.getGoodSpuInfoList().stream().filter(g -> orderItemId == g.getOrderItemId()).map(g -> new DetailDishesVo(g.getGoodName(), g.getQuantity(), g.getPrice().doubleValue(), g.getRemark(), null)).collect(Collectors.toList());
            //组装打印内容-制作单、
            JuhePrintContentVo contentVo = JuhePrintContentVo.builder().tableNo(orderInstanceInfoVo.getTableNo()).dishesVoList(voList).type(type).build();
            //打印机
            List<PrinterInstanceEntity> prints = printerInstanceService.lambdaQuery().eq(PrinterInstanceEntity::getStoreId, storeId).eq(PrinterInstanceEntity::getMakingStatus, 1).list();

            if (CollectionUtils.isEmpty(prints)) {
                return Response.fail("无可用打印机！");
            }

            Map<String, String> printMap = getprintMap(prints, contentVo, voList, orderInstanceInfoVo);

            if (printMap.isEmpty()) {
                return Response.fail("无可用打印机！");
            }

            //进行打印
            printMap.forEach((k, v) -> syncPrint(k, orderNo, v));

            return Response.ok(1, "打印中");
        } catch (Exception e) {
            log.error("printMakeOrder失败：{}", e.getMessage());
            return Response.fail("打印失败：" + e.getMessage());
        }
    }


    /**
     * 查看打印状态
     *
     * @param printerNo 设备编号
     * @param printId   打印id
     * @return
     */
    public Response getPrintStatus(String printerNo, Long printId) {
        JSONObject printStatus = juhePrintUtils.getPrintStatus(printerNo, printId);
        if (ObjectUtils.isNotEmpty(printStatus) && 0 == printStatus.getInteger("code")) {
            //查询成功 更新历史状态
            printHistoryService.lambdaUpdate()
                    .eq(PrintHistoryEntity::getPrinterNo, printerNo)
                    .eq(PrintHistoryEntity::getPrintId, printId)
                    .set(PrintHistoryEntity::getStateCode, printStatus.getJSONObject("data").getString("status"))
                    .update();
        }
        return Response.ok(printStatus);
    }

    /**
     * 查看订单打印状态
     *
     * @param orderNo 订单号 String必填
     * @return
     */
    public Response getPrintResByOrderNo(String orderNo) {
        //打印状态码  0 待打印，1 打印中，2 打印成功 3 打印失败
        //根据订单号查询打印历史
        List<PrintHistoryEntity> phs = printHistoryService.lambdaQuery().eq(PrintHistoryEntity::getOrderNo, orderNo).eq(PrintHistoryEntity::getFlag, false).list();
        if (CollectionUtils.isEmpty(phs)) {
            return Response.fail();
        }
        phs.forEach(p -> {
            JSONObject printStatus = juhePrintUtils.getPrintStatus(p.getOrderNo(), p.getPrintId());
            if (ObjectUtils.isNotEmpty(printStatus) && 0 == printStatus.getInteger("code")) {
                //查询成功 更新历史状态
                String status = printStatus.getJSONObject("data").getString("status");
                p.setStateCode(status);
            }
        });
        printHistoryService.updateBatchById(phs);

        //成功打印的
        Map<String, List<PrintHistoryEntity>> collect = phs.stream().collect(Collectors.groupingBy(PrintHistoryEntity::getStateCode));
        List<PrintHistoryEntity> printoks = collect.get(PrintStateEnum.print_ok.getCode());
        if (CollectionUtils.isNotEmpty(printoks) && phs.size() == printoks.size()) {
            return Response.ok(collect, PrintStateEnum.print_ok.getName());
        } else {
            return Response.ok(collect, PrintStateEnum.print_unok.getName());
        }
    }

    /**
     * 异步订单打印
     *
     * @param sn      打印机编号
     * @param orderId 订单id
     * @param content 打印内容
     */
    private void syncPrint(String sn, String orderNo, String content) {
        try {
            Thread thread = new Thread(() -> {
                log.info("订单{}异步打印开始", orderNo);
                //如果打印成功，更改打印状态以及记录打印条数
                JSONObject parseObject = juhePrintUtils.print(sn, content, true);
                log.info("订单{}异步打印返回结果：{}", orderNo, parseObject);
                PrintHistoryEntity ph = PrintHistoryEntity.builder().printerNo(sn).params(content).orderNo(orderNo).flag(Boolean.FALSE).build();
                if (parseObject.getInteger("code") == 0) {
                    //检测排队数量，如果超过10个不进行排队
                    JSONObject dataJsonObj = parseObject.getJSONObject("data");
                    if (dataJsonObj != null) {
                        Integer queueSize = dataJsonObj.getInteger("queueSize");
                        log.info("打印信息：当前打印队列数量：{}", queueSize);
                        if (queueSize > 10) {
                            return;
                        }
                        // 打印请求受理ID
                        Long printId = dataJsonObj.getLong("printId");
                        ph.setPrintId(printId);
                    }
                }
                //打印历史保存
                printHistoryService.update(Wrappers.<PrintHistoryEntity>lambdaUpdate()
                        .eq(PrintHistoryEntity::getOrderNo, orderNo).set(PrintHistoryEntity::getFlag, Boolean.TRUE));
                printHistoryService.save(ph);
            });
            thread.start();
        } catch (Exception e) {
            log.error("异步拉起打印" + e.getMessage(), e);
        }
    }


    /**
     * 异步更新打印机状态
     *
     * @param prints 打印机对象
     */
    private void syncUpPrintStatus(List<PrinterInstanceEntity> prints) {
        try {
            List<String> printerNos = prints.stream().map(PrinterInstanceEntity::getPrinterNo).collect(Collectors.toList());
            printerNos.forEach(n -> {
                log.info("打印机{}异步更新状态开始", n);
                //查询打印机状态
                JSONObject res = juhePrintUtils.getPrinterStatus(n);
                //查询成功 进行更新
                if (ObjectUtils.isNotEmpty(res) && res.getInteger("code") == 0) {
                    JSONObject printerStatus = res.getJSONObject("data");
                    Integer onlineStatus = printerStatus.getInteger("onlineStatus");
                    Integer workStatus = printerStatus.getInteger("workStatus");
                    printerStateService.lambdaUpdate().eq(PrinterStateEntity::getPrinterNo, n)
                            .set(PrinterStateEntity::getOnlineStatus, onlineStatus)
                            .set(PrinterStateEntity::getWorkStatus, workStatus)
                            .update();
                }
            });
        } catch (Exception e) {
            log.error("异步拉起打印" + e.getMessage(), e);
        }
    }

    private Integer setPrinterStatus(String printerNo) {
        Integer printerStatus = -1;
        try {
            //调用接口查询状态
            JSONObject res = juhePrintUtils.getPrinterStatus(printerNo);
            //查询成功 进行更新
            if (ObjectUtils.isNotEmpty(res) && res.getInteger("code") == 0) {
                printerStatus = res.getJSONObject("data").getInteger("workStatus");
            } else {
                //打印机状态-直接库表查询
                printerStatus = printerStateService.lambdaQuery()
                        .eq(PrinterStateEntity::getPrinterNo, printerNo)
                        .last("limit 1")
                        .one()
                        .getWorkStatus();
                log.info("列表打印机printerStatus:{}", printerStatus);
            }
        } catch (Exception e) {
            log.info("打印机列表状态获取异常: {}", e.getMessage());
        }
        return printerStatus;
    }

}
