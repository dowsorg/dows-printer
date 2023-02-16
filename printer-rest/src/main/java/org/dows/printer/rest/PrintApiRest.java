package org.dows.printer.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.biz.PrintApiBiz;
import org.dows.printer.entity.PrintApiEntity;
import org.dows.printer.form.PrintApiForm;
import org.dows.printer.service.PrintApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印接口(PrintApi)表控制层
 *
 * @author
 * @date
 */
@Api(tags = "打印接口")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printApi")
public class PrintApiRest implements MybatisCrudRest<PrintApiForm, PrintApiEntity, PrintApiService> {


    private final PrintApiBiz printApiBiz;

    /**
     * 获取接口请求结果
     *
     * @param apiId  接口id String必填
     */
    @PostMapping("/reqApi")
    @ApiOperation(value = "获取接口请求结果")
    public Response reqApi(String apiId) {
        return printApiBiz.reqJHApi(apiId);
    }

    /**
     * 获取请求时间戳
     */
    @GetMapping("/getStime")
    @ApiOperation(value = "获取请求时间戳")
    public Long getPrintList() {
        return System.currentTimeMillis();
    }


}

