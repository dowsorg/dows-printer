package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrintTemplatesMapper;
import org.dows.printer.entity.PrintTemplatesEntity;
import org.dows.printer.service.PrintTemplatesService;
import org.springframework.stereotype.Service;


/**
 * 打印模板库(PrintTemplates)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:03
 */
@Service("printTemplatesService")
public class PrintTemplatesServiceImpl extends MybatisCrudServiceImpl<PrintTemplatesMapper, PrintTemplatesEntity> implements PrintTemplatesService {

}

