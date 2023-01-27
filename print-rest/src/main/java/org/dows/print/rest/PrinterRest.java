package org.dows.print.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.print.PrinterBiz;
import org.dows.print.entity.SlwPrintInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印机(PrintInfo)表控制层
 *
 * @author AZ
 * @since 2023-1-17 13:27:24
 */
@Api(tags = "打印机")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("print")
public class PrinterRest {

    private final PrinterBiz printInfoBiz;

    /**
     * 商户获取打印机
     *
     * @param userId  登录用户id String必填
     * @param storeId 门店id String必填
     */
    @GetMapping("/getPrintList")
    public Response getPrintList(String userId, String storeId) {
        return printInfoBiz.getPrintList(userId, storeId);
    }

    /**
     * 选择打印类型
     *
     * @param userId  登录用户id String必填
     * @param storeId 门店id String必填
     */
    @GetMapping("/getPrintAttrList")
    public Response getPrintAttrList(String userId, String storeId) {
        return printInfoBiz.getPrintAttrList(userId, storeId);
    }

    /**
     * 选择打印区域
     *
     * @param userId  登录用户id String必填
     * @param storeId 门店id String必填
     */
    @GetMapping("/getPrintAreaList")
    public Response getPrintAreaList(String userId, String storeId) {
        return printInfoBiz.getPrintAreaList(userId, storeId);
    }


    /**
     * 打印机绑定
     * 进入界面前需要调用商户打印机接口获取已有打印机。如果已有，需要把id传进来以便修改
     */
    @PostMapping("/bindPrint")
    @ApiOperation(value = "打印机绑定")
    public Response bindPrint(SlwPrintInfo printInfo) {
        return printInfoBiz.bindPrint(printInfo);
    }


    /**
     * 查看打印机状态
     *
     * @param printSn 设备编号
     * @return
     */
    @GetMapping("/getPrintStatus")
    public Response getPrintStatus(String printSn) {
        return printInfoBiz.getPrintStatus(printSn);
    }

    /**
     * 测试打印机
     *
     * @param userId  登录用户id String必填
     * @param printSn 打印机序列号 String必填
     */
    @PostMapping("/printTest")
    public Response printTest(String userId, String printSn) {
        return printInfoBiz.printTest(userId, printSn);
    }

    /**
     * 打印总单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    @PostMapping("/printMasterOrder")
    public Response printMasterOrder(String userId, String orderId) {
        return printInfoBiz.printMasterOrder(userId, orderId);
    }

    /**
     * 打印制作单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    @PostMapping("/printMakeOrder")
    public Response printMakeOrder(String userId, String orderId) {
        return printInfoBiz.printMakeOrder(userId, orderId);
    }

    /**
     * 打印加菜单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    @PostMapping("/printAddOrder")
    public Response printAddOrder(String userId, String orderId) {
        return printInfoBiz.printAddOrder(userId, orderId);
    }

    /**
     * 打印退菜单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    @PostMapping("/printRemoveOrder")
    public Response printRemoveOrder(String userId, String orderId) {
        return printInfoBiz.printRemoveOrder(userId, orderId);
    }

    /**
     * 打印结账单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    @PostMapping("/printCheckoutOrder")
    public Response printCheckoutOrder(String userId, String orderId) {
        return printInfoBiz.printCheckoutOrder(userId, orderId);
    }


}

