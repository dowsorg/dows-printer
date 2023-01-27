package org.dows.print;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.print.entity.SlwPrintInfo;
import org.dows.print.service.SlwPrintInfoService;
import org.dows.print.utils.PandStringUtils;
import org.dows.print.utils.third.print.*;
import org.dows.print.utils.third.print.DaquPrintVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrinterBiz {

    private static final Logger logger = LoggerFactory.getLogger(PrinterBiz.class);
    private final SlwPrintInfoService printInfoService;
//    private final OrderInstanceService orderInstanceService;
//    private final OrderItemService orderItemService;

    /**
     * 商户获取打印机
     *
     * @param userId  登录用户id String必填
     * @param storeId 门店id String必填
     */
    public Response getPrintList(String userId, String storeId) {
        List<SlwPrintInfo> prints = new ArrayList<>();
        try {
            prints = printInfoService.lambdaQuery().eq(SlwPrintInfo::getStoreId, storeId).list();
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
    public Response bindPrint(SlwPrintInfo printInfo) {
        try {
            if (printInfo.getMakingStatus() == null) {
                printInfo.setMakingStatus(2);
            }
            if (PandStringUtils.isBlank(printInfo.getPrintName())) {
                printInfo.setPrintName(printInfo.getPrintSn());
            }
            //进行绑定
            boolean newBind = false;
            //检测设备是否绑定过
            List<DaquPrintVo> voList = Lists.newArrayList();
            DaquPrintVo vo = new DaquPrintVo();
            vo.setKey(printInfo.getPrintKey());
            vo.setName(printInfo.getPrintName());
            vo.setSn(printInfo.getPrintSn());
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
                DaquzhinengPrintUtils.setDensity(printInfo.getPrintSn(), 5);
            }
            //保存绑定打印机信息
            printInfo.setCreateTime(new Date());
            printInfo.setPrintCount(0);
            printInfo.setDataStatus(1);
            printInfoService.save(printInfo);
        } catch (Exception e) {
            logger.error("订单打印异常" + e.getMessage(), e);
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
            logger.info("聚合呗-查看打印机状态  res:{}", res);
        } catch (Exception e) {
            logger.error("查看打印机状态异常" + e.getMessage(), e);
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
            voList.add(new DetailDishesVo("龙虾泡饭", 1, 38.00));
            voList.add(new DetailDishesVo("芝士焗咖喱牛腩牛腩饭", 1, 38.00));
            voList.add(new DetailDishesVo("炙烤猪扒饭", 1, 38.00));
            //组装打印内容-总单
            DaquPrintContentVo daquPrintContentVo = new DaquPrintContentVo("五天日记【松江印象城店】", "A01", "6", "刘大牙", voList, "98.60", "苏庆北路246号印象城B1-901", "021-09001234", null);
            String content = DaquPrintContentUtils.getMasterContent(daquPrintContentVo);
            logger.info("打印信息：" + content);
            String print = DaquzhinengPrintUtils.print(printSn, content, true);
            logger.info(print);
            return Response.ok(1, "打印中");
        } catch (Exception e) {
            logger.error("测试打印异常" + e.getMessage(), e);
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
//            List<SlwPrintInfo> prints = printInfoService.list(printInfoService.lambdaQuery().eq(SlwPrintInfo::getStoreId, storeId).eq(SlwPrintInfo::getPrintStatus, 2));
//            //获取需要进行打印任务的打印机
//            List<SlwPrintInfo> needs = new LinkedList<>();
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
//            DaquPrintContentVo daquPrintContentVo = new DaquPrintContentVo(
//                    "店名",
//                    orderInstance.getTableNo(),
//                    "" + orderInstance.getPeoples(),
//                    "操作员",
//                    voList,
//                    "" + orderInstance.getAmount(),
//                    "地址",
//                    "联系电话",
//                    null);
//            String content = DaquPrintContentUtils.getMasterContent(daquPrintContentVo);
//            logger.info("打印信息：" + content);
//            //进行打印
//            if (CollectionUtils.isNotEmpty(needs)) {
//                for (SlwPrintInfo printInfo : needs) {
//                    String print = DaquzhinengPrintUtils.print(printInfo.getPrintSn(), content, true);
//                    logger.info(print);
//                }
//            }
            return Response.ok(1, "打印中");
        } catch (Exception e) {
            logger.error("状态异常" + e.getMessage(), e);
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
            logger.error("状态异常" + e.getMessage(), e);
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
            logger.error("状态异常" + e.getMessage(), e);
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
            logger.error("状态异常" + e.getMessage(), e);
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
            logger.error("状态异常" + e.getMessage(), e);
        }
        return Response.ok();
    }


}
