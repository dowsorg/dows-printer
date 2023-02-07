package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrinterAppEntity;
import org.dows.printer.form.PrinterAppForm;
import org.dows.printer.service.PrinterAppService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 打印机应用(PrinterApp)表控制层
*
* @author 
* @date 
*/
@Api(tags = "打印机应用")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printerApp")
public class PrinterAppRest implements MybatisCrudRest<PrinterAppForm, PrinterAppEntity, PrinterAppService> {


}

