package org.dows.printer.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.printer.entity.PrinterInstanceEntity;
import org.dows.printer.service.PrinterInstanceService;
import org.dows.printer.utils.PandStringUtils;
import org.dows.printer.utils.third.print.*;
import org.dows.printer.vo.DetailDishesVo;
import org.dows.printer.vo.JuhePrintContentVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrinterBiz {

    private final PrinterInstanceService printerInstanceService;

    /**
     * 商户获取打印机
     *
     * @param userId  登录用户id String必填
     * @param storeId 门店id String必填
     */
    public Response getPrintList(String userId, String storeId) {
        List<PrinterInstanceEntity> prints = new ArrayList<>();
        try {
//            prints = printerInstanceService.lambdaQuery().eq(PrinterInstanceEntity::getStoreId, storeId).list();
        } catch (Exception e) {

        }
        return Response.ok(prints);
    }

    /**
     * 选择打印类型
     *
     * @param userId  登录用户id String必填
     * @param storeId 门店id String必填
     */
    public Response getPrintAttrList(String userId, String storeId) {
        return Response.ok();
    }


    /**
     * 选择打印区域
     *
     * @param userId  登录用户id String必填
     * @param storeId 门店id String必填
     */
    public Response getPrintAreaList(String userId, String storeId) {
        return Response.ok();
    }

    /**
     * 打印机绑定
     *
     * @param
     * @return
     */
    public Response bindPrint(PrinterInstanceEntity printerInstance) {
        try {
            if (PandStringUtils.isBlank(printerInstance.getPrinterName())) {
                printerInstance.setPrinterName(printerInstance.getPrinterNo());
            }
            //进行绑定
            boolean newBind = false;
            //检测设备是否绑定过
            List<DaquPrintVo> voList = Lists.newArrayList();
            DaquPrintVo vo = new DaquPrintVo();
            vo.setKey(printerInstance.getPrinterKey());
            vo.setName(printerInstance.getPrinterName());
            vo.setSn(printerInstance.getPrinterNo());
            voList.add(vo);
            //进行绑定操作
            String addPrinter = DaquzhinengPrintUtils.addPrinter(voList);
            if (addPrinter.indexOf("设备已绑定") != -1) {

            } else {
                JSONObject addPrinterJson = JSON.parseObject(addPrinter);
                JSONObject addprintdatajson = addPrinterJson.getJSONObject("data");
                JSONArray jsonArray = addprintdatajson.getJSONArray("fail");
                List<DaquAddFailVo> list = JSON.parseArray(jsonArray.toJSONString(), DaquAddFailVo.class);
                if (list != null && !list.isEmpty()) {
                    DaquAddFailVo daquvo = list.get(0);
                    if (!daquvo.getReason().equals("设备已绑定")) {
                        log.info("daquvo.getReason: {}", daquvo.getReason());
                        return Response.fail("绑定异常");
                    }
                }
                newBind = true;
                //新设备进行墨浓度设置
                DaquzhinengPrintUtils.setDensity(printerInstance.getPrinterNo(), 5);
            }
            //保存绑定打印机信息
            printerInstanceService.save(printerInstance);
            //保存打印机与店铺绑定关系


        } catch (Exception e) {
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
    public Response getPrintStatus(String sn) {
        String res = null;
        try {
            res = DaquzhinengPrintUtils.getPrintStatus(sn);
            log.info("聚合呗-查看打印机状态  res:{}", res);
        } catch (Exception e) {
            log.error("查看打印机状态异常" + e.getMessage(), e);
        }
        return Response.ok(res);
    }


    /**
     * 测试打印机
     *
     * @param userId  登录用户id String必填
     * @param printSn 打印机序列号 String必填
     */
    public Response printTest(String userId, String printSn) {
        try {
            String printStatus = DaquzhinengPrintUtils.getPrintStatus(printSn);
            JSONObject printParse = JSON.parseObject(printStatus);
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
            log.info("打印信息：" + content);
            String print = DaquzhinengPrintUtils.print(printSn, content, true);
            log.info(print);
            return Response.ok(1, "打印中");
        } catch (Exception e) {
            log.error("测试打印异常" + e.getMessage(), e);
            return Response.fail("服务器升级");
        }
    }

    /**
     * 打印总单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    public Response printMasterOrder(String userId, String orderId) {
        try {
//            //通过订单id获取订单信息
//            OrderInstance orderInstance = orderInstanceService.getById(orderId);
//            //获取订单的商铺id
//            String storeId = orderInstance.getStoreId();
//            //通过商铺id获取绑定的打印机
//            List<SlwprinterInstance> prints = printerInstanceService.list(printerInstanceService.lambdaQuery().eq(SlwprinterInstance::getStoreId, storeId).eq(SlwprinterInstance::getPrintStatus, 2));
//            //获取需要进行打印任务的打印机
//            List<SlwprinterInstance> needs = new LinkedList<>();
//            //判断打印机是否存在多台在线
//            if (CollectionUtils.isNotEmpty(prints)) {
//                if (prints.size() == 1) {//一台就用这台打印
//                    needs.add(prints.get(0));
//                } else {
//                    //多台根据桌台分类已经订单的菜品的属性获取对应的打印机进行打印
//
//                }
//            }
//            //获取打印内容
//            //菜品
//            List<DetailDishesVo> voList = orderItemService.lambdaQuery().eq(OrderItem::getOrderId, orderId).list().stream().map(o-> new DetailDishesVo(o.getSpuName(),o.getQuantity(),o.getPrice().doubleValue())).collect(Collectors.toList());
//            //组装打印内容-总单
//            JuhePrintContentVo JuhePrintContentVo = new JuhePrintContentVo(
//                    "店名",
//                    orderInstance.getTableNo(),
//                    "" + orderInstance.getPeoples(),
//                    "操作员",
//                    voList,
//                    "" + orderInstance.getAmount(),
//                    "地址",
//                    "联系电话",
//                    null);
//            String content = JuhePrintContentUtils.getMasterContent(JuhePrintContentVo);
//            log.info("打印信息：" + content);
//            //进行打印
//            if (CollectionUtils.isNotEmpty(needs)) {
//                for (SlwprinterInstance printerInstance : needs) {
//                    String print = DaquzhinengPrintUtils.print(printerInstance.getPrintSn(), content, true);
//                    log.info(print);
//                }
//            }
            return Response.ok(1, "打印中");
        } catch (Exception e) {
            log.error("状态异常" + e.getMessage(), e);
            return Response.fail("打印失败");
        }
    }

    /**
     * 打印制作单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    public Response printMakeOrder(String userId, String orderId) {
        try {
        } catch (Exception e) {
            log.error("状态异常" + e.getMessage(), e);
        }
        return Response.ok();
    }

    /**
     * 打印加菜单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    public Response printAddOrder(String userId, String orderId) {
        try {
        } catch (Exception e) {
            log.error("状态异常" + e.getMessage(), e);
        }
        return Response.ok();
    }

    /**
     * 打印退菜单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    public Response printRemoveOrder(String userId, String orderId) {
        try {
        } catch (Exception e) {
            log.error("状态异常" + e.getMessage(), e);
        }
        return Response.ok();
    }

    /**
     * 打印结账单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    public Response printCheckoutOrder(String userId, String orderId) {
        try {
        } catch (Exception e) {
            log.error("状态异常" + e.getMessage(), e);
        }
        return Response.ok();
    }


    /**
     * 打印结账单
     *
     * @param printContentVo 小票内容对象 1-总单  2-结账单（优惠信息）  3、制作单  4、加菜  5退菜
     */
    public Response getModelContent(JuhePrintContentVo printContentVo) {
        String result = "";
        try {
            Integer type = printContentVo.getType();
            if (ObjectUtils.isEmpty(type)) {
                return Response.fail("小票类型不能为空");
            }
            result = JuhePrintContentUtils.getContent(printContentVo);
        } catch (Exception e) {
            log.error("状态异常" + e.getMessage(), e);
        }
        return Response.ok(result);
    }

    /**
     * 进行打印
     *
     * @param sn      打印机编号
     * @param content 内容
     */
    public Response PrintContent(String sn, String content) {
        syncPrint(sn, "", content);
        return Response.ok("打印中");
    }

    /**
     * 异步通知小票打印状态
     *
     * @param type  回调消息类型  5 打印请求状态变更, 6 设备状态发生更新(开发中)
     * @param rtime 回调时间(unix timestamp 秒)
     * @param data  回调业务内容  json string 例: "{"printId":"819001823847712734","status":"0"}" [0 待打印, 1 打印中, 2 成功, 3 失败, 4 已取消]
     */
    public Response PrintNotice(int type, int rtime, String data) {
        JSONObject object = JSONObject.parseObject(data);
        String printId = object.getString("printId");
        Integer status = object.getInteger("status");

        return Response.ok("打印中");
    }

    private void syncPrint(String sn, String orderId, String content) {
        try {
            Thread thread = new Thread(() -> {
                log.info("订单{}异步打印开始", orderId);
                String printMsg = DaquzhinengPrintUtils.print(sn, content, true);
                log.info("订单{}异步打印返回结果：{}", orderId, printMsg);
                //如果打印成功，更改打印状态以及记录打印条数
                JSONObject parseObject = JSON.parseObject(printMsg);
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
                        String printId = dataJsonObj.getString("printId");
                        //打印机历史保存

                    }
                }
            });
            thread.start();
        } catch (Exception e) {
            log.error("异步拉起打印" + e.getMessage(), e);
        }
    }

}
