package org.dows.printer.rest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.entity.PrintMetaEntity;
import org.dows.printer.form.PrintMetaForm;
import org.dows.printer.service.PrintMetaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 参数元数据(PrintMeta)表控制层
*
* @author 
* @date 
*/
@Api(tags = "参数元数据")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printMeta")
public class PrintMetaRest implements MybatisCrudRest<PrintMetaForm, PrintMetaEntity, PrintMetaService> {


}

