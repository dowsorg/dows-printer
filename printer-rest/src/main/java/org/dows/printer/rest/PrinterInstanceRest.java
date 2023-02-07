package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrinterInstanceEntity;
import org.dows.printer.form.PrinterInstanceForm;
import org.dows.printer.service.PrinterInstanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

