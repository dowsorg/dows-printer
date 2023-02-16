package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrintTemplateMapper;
import org.dows.printer.entity.PrintTemplateEntity;
import org.dows.printer.service.PrintTemplateService;
import org.springframework.stereotype.Service;


/**
 * 应用打印模板(PrintTemplate)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:02
 */
@Service("printTemplateService")
public class PrintTemplateServiceImpl extends MybatisCrudServiceImpl<PrintTemplateMapper, PrintTemplateEntity> implements PrintTemplateService {

}

