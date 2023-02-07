package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrintMappingMapper;
import org.dows.printer.entity.PrintMappingEntity;
import org.dows.printer.service.PrintMappingService;
import org.springframework.stereotype.Service;


/**
 * 接口映射(PrintMapping)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:01
 */
@Service("printMappingService")
public class PrintMappingServiceImpl extends MybatisCrudServiceImpl<PrintMappingMapper, PrintMappingEntity> implements PrintMappingService {

}

