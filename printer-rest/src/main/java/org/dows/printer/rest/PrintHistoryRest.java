package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrintHistoryEntity;
import org.dows.printer.form.PrintHistoryForm;
import org.dows.printer.service.PrintHistoryService;
import org.springframework.web.bind.annotation.RequestMapping;
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


}

