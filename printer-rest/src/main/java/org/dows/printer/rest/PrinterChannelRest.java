package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrinterChannelEntity;
import org.dows.printer.form.PrinterChannelForm;
import org.dows.printer.service.PrinterChannelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 打印机通道(PrinterChannel)表控制层
*
* @author 
* @date 
*/
@Api(tags = "打印机通道")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printerChannel")
public class PrinterChannelRest implements MybatisCrudRest<PrinterChannelForm, PrinterChannelEntity, PrinterChannelService> {


}

