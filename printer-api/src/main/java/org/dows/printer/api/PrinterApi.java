package org.dows.printer.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dows.framework.api.Response;
import org.dows.printer.api.request.PrintContentRequest;
import org.dows.printer.vo.JuhePrintContentVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 门店-排队(StoreQueuing)表控制层
 *
 * @author lait.zhang
 * @since 2022-10-29 13:27:26
 */
@Api(tags = "打印机")
@RequestMapping("printer")
public interface PrinterApi {

    /**
     * 根据内容打印
     *
     * @param printContentRequest
     * @param -printSn            打印机编号
     * @param -content            打印内容
     */
    @PostMapping("/printByContent")
    @ApiOperation(value = "根据内容打印")
    Response printByContent(@RequestBody PrintContentRequest printContentRequest);

    /**
     * 组装小票内容
     *
     * @param printContentVo      小票组装内容
     * @param -storeName          店名    五天日记【松江印象城店】
     * @param -tableNo            桌号    “A01”
     * @param -pepNum             人数  "6"
     * @param -operator           操作员
     * @param -dishesVoList       菜品集合
     * @param -amountTotal        总计金额
     * @param -address            地址
     * @param -phone              电话
     * @param -affiliatePriceJson 优惠信息
     * @param -type               小票类型    1-总单  2-结账单（优惠信息）  3、制作单  4、加菜  5退菜
     * @return
     */
    @PostMapping("/getModelContent")
    @ApiOperation(value = "组装小票内容")
    Response getModelContent(@RequestBody JuhePrintContentVo printContentVo);


}


