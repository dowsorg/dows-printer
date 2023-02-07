package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrintApiEntity;
import org.dows.printer.form.PrintApiForm;
import org.dows.printer.service.PrintApiService;
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


}

