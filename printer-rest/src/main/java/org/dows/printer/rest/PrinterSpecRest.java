package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrinterSpecEntity;
import org.dows.printer.form.PrinterSpecForm;
import org.dows.printer.service.PrinterSpecService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 打印机规格(PrinterSpec)表控制层
*
* @author 
* @date 
*/
@Api(tags = "打印机规格")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printerSpec")
public class PrinterSpecRest implements MybatisCrudRest<PrinterSpecForm, PrinterSpecEntity, PrinterSpecService> {


}

