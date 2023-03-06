package org.dows.printer.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.biz.PrintHistoryBiz;
import org.dows.printer.entity.PrintHistoryEntity;
import org.dows.printer.form.PrintHistoryForm;
import org.dows.printer.service.PrintHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印记录(PrintHistory)表控制层
 *
 * @author
 * @date
 */
@Api(tags = "打印记录")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printHistory")
public class PrintHistoryRest implements MybatisCrudRest<PrintHistoryForm, PrintHistoryEntity, PrintHistoryService> {

    private final PrintHistoryBiz printerInstanceBiz;

    /**
     * 查看订单打印历史根据订单号
     *
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("/getHistoryByOrderNo")
    @ApiOperation(value = "查看订单打印历史根据订单号")
    public Response getHistoryByOrderNo(@RequestParam String orderNo) {
        return printerInstanceBiz.getHistoryByOrderNo(orderNo);
    }

}

