package org.dows.printer.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.printer.PrintTemplateBiz;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 打印机-打印模板
 *
 * @author AZ
 * @since 2023-1-17 13:27:24
 */
@Api(tags = "打印模板")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printTemplate")
public class PrintTemplateRest {

    private final PrintTemplateBiz printTemplateBiz;

    /**
     * 获取模板组装内容
     *
     * @param temId 模板id
     */
    @GetMapping("/getConByTem")
    @ApiOperation(value = "获取模板组装内容")
    public Response getConByTem(String temId) {
        return printTemplateBiz.getConByTem(temId);
    }


}

