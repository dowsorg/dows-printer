package org.dows.printer.rest;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.biz.PrinterInstanceBiz;
import org.dows.printer.dto.PrintContentDTO;
import org.dows.printer.dto.PrintNoticeDTO;
import org.dows.printer.entity.PrinterInstanceEntity;
import org.dows.printer.form.PrinterInstanceForm;
import org.dows.printer.service.PrinterInstanceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 打印机(PrinterInstance)表控制层
 *
 * @author
 * @date
 */
@Api(tags = "打印机")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printerInstance")
public class PrinterInstanceRest implements MybatisCrudRest<PrinterInstanceForm, PrinterInstanceEntity, PrinterInstanceService> {

    private final PrinterInstanceBiz printerInstanceBiz;

    /**
     * 商户获取打印机列表
     *
     * @param storeId 门店id String必填
     */
    @GetMapping("/getPrinterList")
    @ApiOperation(value = "商户获取打印机列表")
    public Response getPrinterList(String storeId) {
        return printerInstanceBiz.getPrinterList(storeId);
    }

    /**
     * 打印机绑定
     * 进入界面前需要调用商户打印机接口获取已有打印机。如果已有，需要把id传进来以便修改
     */
    @PostMapping("/bindPrinter")
    @ApiOperation(value = "打印机绑定")
    public Response bindPrinter(@Validated @RequestBody PrinterInstanceEntity entity) {
        return printerInstanceBiz.bindPrinter(entity);
    }

    /**
     * 查看打印机状态
     *
     * @param printerNo 设备编号
     * @return
     */
    @GetMapping("/getPrinterStatus")
    @ApiOperation(value = "查看打印机状态")
    public Response getPrinterStatus(String printerNo) {
        return printerInstanceBiz.getPrinterStatus(printerNo);
    }

    /**
     * 测试打印机
     *
     * @param printerNo 打印机序列号 String必填
     */
    @PostMapping("/printerTest")
    @ApiOperation(value = "测试打印机")
    public Response printerTest(String printerNo) {
        return printerInstanceBiz.printerTest(printerNo);
    }


    /**
     * 根据内容打印
     *
     * @param printContentDTO
     * @param -printSn        打印机编号
     * @param -content        打印内容
     */
    @PostMapping("/printByContent")
    @ApiOperation(value = "根据内容打印")
    public Response printByContent(@RequestBody PrintContentDTO printContentDTO) {
        return printerInstanceBiz.printByContent(printContentDTO);
    }

    /**
     * 异步通知小票打印状态
     *
     * @param printNoticeDTO 异步通知小票
     * @param -type          回调消息类型  5 打印请求状态变更, 6 设备状态发生更新(开发中)
     * @param -rtime         回调时间(unix timestamp 秒)
     * @param -data          回调业务内容  json string 例: "{"printId":"819001823847712734","status":"0"}" [0 待打印, 1 打印中, 2 成功, 3 失败, 4 已取消]
     */
    @PostMapping("/synPrintNotice")
    @ApiOperation(value = "异步通知小票打印状态")
    public JSONObject synPrintNotice(@RequestBody PrintNoticeDTO printNoticeDTO) {
        return printerInstanceBiz.synPrintNotice(printNoticeDTO);
    }

    /**
     * 打印总单
     *
     * @param orderNo 订单号 String必填
     */
    @PostMapping("/printMasterOrder")
    @ApiOperation(value = "打印总单")
    public Response printMasterOrder(@RequestParam String orderNo) {
        return printerInstanceBiz.printMasterOrder(orderNo);
    }


}

