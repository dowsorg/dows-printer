package org.dows.printer.rest;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.printer.api.PrinterApi;
import org.dows.printer.api.request.PrintContentRequest;
import org.dows.printer.biz.PrintTemplateBiz;
import org.dows.printer.biz.PrinterBiz;
import org.dows.printer.biz.PrinterInstanceBiz;
import org.dows.printer.vo.JuhePrintContentVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印机(Printer)控制层
 *
 * @author AZ
 * @since 2023-1-17 13:27:24
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class PrinterRest implements PrinterApi {

    private final PrinterBiz printInfoBiz;
    private final PrinterInstanceBiz printerInstanceBiz;
    private final PrintTemplateBiz printTemplateBiz;


    /**
     * 选择打印类型
     *
     * @param userId  登录用户id String必填
     * @param storeId 门店id String必填
     */
    @GetMapping("/getPrintAttrList")
    @ApiOperation(value = "选择打印类型")
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
    @ApiOperation(value = "选择打印区域")
    public Response getPrintAreaList(String userId, String storeId) {
        return printInfoBiz.getPrintAreaList(userId, storeId);
    }

    /**
     * 打印总单
     *
     * @param userId  登录用户id String必填
     * @param orderId 订单id String必填
     */
    @PostMapping("/printMasterOrder")
    @ApiOperation(value = "打印总单")
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
    @ApiOperation(value = "打印制作单")
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
    @ApiOperation(value = "打印加菜单")
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
    @ApiOperation(value = "打印退菜单")
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
    @ApiOperation(value = "打印结账单")
    public Response printCheckoutOrder(String userId, String orderId) {
        return printInfoBiz.printCheckoutOrder(userId, orderId);
    }


    @Override
    public Response printByContent(@RequestBody PrintContentRequest printContentRequest) {
        return printerInstanceBiz.printByContent(printContentRequest);
    }

    @Override
    public Response getModelContent(JuhePrintContentVo printContentVo) {
        return printTemplateBiz.getModelContent(printContentVo);
    }


}

