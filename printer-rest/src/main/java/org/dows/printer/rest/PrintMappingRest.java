package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrintMappingEntity;
import org.dows.printer.form.PrintMappingForm;
import org.dows.printer.service.PrintMappingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 接口映射(PrintMapping)表控制层
*
* @author 
* @date 
*/
@Api(tags = "接口映射")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printMapping")
public class PrintMappingRest implements MybatisCrudRest<PrintMappingForm, PrintMappingEntity, PrintMappingService> {


}

