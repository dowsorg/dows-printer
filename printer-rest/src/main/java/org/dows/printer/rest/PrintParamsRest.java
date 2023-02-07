package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrintParamsEntity;
import org.dows.printer.form.PrintParamsForm;
import org.dows.printer.service.PrintParamsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 打印参数(PrintParams)表控制层
*
* @author 
* @date 
*/
@Api(tags = "打印参数")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printParams")
public class PrintParamsRest implements MybatisCrudRest<PrintParamsForm, PrintParamsEntity, PrintParamsService> {


}

