package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrintTemplatesEntity;
import org.dows.printer.form.PrintTemplatesForm;
import org.dows.printer.service.PrintTemplatesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 打印模板库(PrintTemplates)表控制层
*
* @author 
* @date 
*/
@Api(tags = "打印模板库")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printTemplates")
public class PrintTemplatesRest implements MybatisCrudRest<PrintTemplatesForm, PrintTemplatesEntity, PrintTemplatesService> {


}

