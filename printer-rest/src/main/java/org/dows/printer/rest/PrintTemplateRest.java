package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrintTemplateEntity;
import org.dows.printer.form.PrintTemplateForm;
import org.dows.printer.service.PrintTemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 应用打印模板(PrintTemplate)表控制层
*
* @author 
* @date 
*/
@Api(tags = "应用打印模板")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printTemplate")
public class PrintTemplateRest implements MybatisCrudRest<PrintTemplateForm, PrintTemplateEntity, PrintTemplateService> {


}

