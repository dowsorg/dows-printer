package org.dows.printer.biz;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.printer.api.request.PrintContentRequest;
import org.dows.printer.dto.PrintContentDTO;
import org.dows.printer.dto.PrintNoticeDTO;
import org.dows.printer.entity.PrintHistoryEntity;
import org.dows.printer.entity.PrinterInstanceEntity;
import org.dows.printer.entity.PrinterStateEntity;
import org.dows.printer.service.PrintHistoryService;
import org.dows.printer.service.PrinterInstanceService;
import org.dows.printer.service.PrinterStateService;
import org.dows.printer.utils.PandStringUtils;
import org.dows.printer.utils.third.print.DaquPrintVo;
import org.dows.printer.utils.third.print.JuhePrintContentUtils;
import org.dows.printer.utils.third.print.JuhePrintUtils;
import org.dows.printer.vo.DetailDishesVo;
import org.dows.printer.vo.JuhePrintContentVo;
import org.dows.printer.vo.PrinterInstanceVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrinterInstanceBiz {

    private final PrinterInstanceService printerInstanceService;
    private final PrintHistoryService printHistoryService;
    private final PrinterStateService printerStateService;
    private final JuhePrintUtils juhePrintUtils;

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
            if (PandStringUtils.isBlank(printerInstance.getPrinterName())) {
                printerInstance.setPrinterName(printerInstance.getPrinterNo());
            }
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
        JSONObject res = null;
        try {
            res = juhePrintUtils.getPrintStatus(sn);
            if (ObjectUtils.isNotEmpty(res) && res.getInteger("code") == 0) {
                res = res.getJSONObject("data");
            }
            log.info("聚合呗-查看打印机状态  res:{}", res);
        } catch (Exception e) {
            log.error("查看打印机状态异常" + e.getMessage(), e);
        }
        return Response.ok(res);
    }


    /**
     * 测试打印机
     *
     * @param printSn 打印机序列号 String必填
     */
    public Response printerTest(String printSn) {
        try {
            JSONObject printParse = juhePrintUtils.getPrintStatus(printSn);
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
            voList.add(new DetailDishesVo("龙虾泡饭", 1, 38.00, null));
            voList.add(new DetailDishesVo("芝士焗咖喱牛腩牛腩饭", 1, 38.00, null));
            voList.add(new DetailDishesVo("炙烤猪扒饭", 1, 38.00, null));
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
     * @param sn      打印机编号
     * @param content 内容
     */
    public Response printByContent(PrintContentRequest printContentRequest) {
        syncPrint(printContentRequest.getPrintSn(), "", printContentRequest.getContent());
        return Response.ok("打印中");
    }

    /**
     * 进行打印
     *
     * @param sn      打印机编号
     * @param content 内容
     */
    public Response printByContent(PrintContentDTO printContentDTO) {
        syncPrint(printContentDTO.getPrintSn(), "", printContentDTO.getContent());
        return Response.ok("打印中");
    }

    /**
     * 异步通知小票打印状态
     *
     * @param type  回调消息类型  5 打印请求状态变更, 6 设备状态发生更新(开发中)
     * @param rtime 回调时间(unix timestamp 秒)
     * @param data  回调业务内容  json string 例: "{"printId":"819001823847712734","status":"0"}" [0 待打印, 1 打印中, 2 成功, 3 失败, 4 已取消]
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
     * 异步订单打印
     *
     * @param sn      打印机编号
     * @param orderId 订单id
     * @param content 打印内容
     */
    private void syncPrint(String sn, String orderId, String content) {
        try {
            Thread thread = new Thread(() -> {
                log.info("订单{}异步打印开始", orderId);
                //如果打印成功，更改打印状态以及记录打印条数
                JSONObject parseObject = juhePrintUtils.print(sn, content, true);
                log.info("订单{}异步打印返回结果：{}", orderId, parseObject);
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
                        //打印机历史保存
                        printHistoryService.save(PrintHistoryEntity.builder().printerNo(sn).params(content).printId(printId).build());
                    }
                }
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
                JSONObject printerStatus = getPrinterStatus(n).getData();
                log.info("打印机{}异步更新状态返回结果：{}", n, printerStatus);
                Integer onlineStatus = printerStatus.getInteger("onlineStatus");
                Integer workStatus = printerStatus.getInteger("workStatus");
                printerStateService.lambdaUpdate().eq(PrinterStateEntity::getPrinterNo, n)
                        .set(PrinterStateEntity::getOnlineStatus, onlineStatus)
                        .set(PrinterStateEntity::getWorkStatus, workStatus)
                        .update();
            });
        } catch (Exception e) {
            log.error("异步拉起打印" + e.getMessage(), e);
        }
    }

    private Integer setPrinterStatus(String printerNo) {
        Integer printerStatus = 5;
        try {
            //调用接口查询状态
            JSONObject data = getPrinterStatus(printerNo).getData();
            if (ObjectUtils.isNotEmpty(data)) {
                printerStatus = data.getInteger("workStatus");
            } else {
                //打印机状态-直接库表查询
                printerStatus = printerStateService.lambdaQuery()
                        .eq(PrinterStateEntity::getPrinterNo, printerNo)
                        .last("limit 1")
                        .one()
                        .getWorkStatus();
            }
        } catch (Exception e) {
            log.info("打印机列表状态获取异常: {}", e.getMessage());
        }
        return printerStatus;
    }

}
