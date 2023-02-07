package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrintMetaMapper;
import org.dows.printer.entity.PrintMetaEntity;
import org.dows.printer.service.PrintMetaService;
import org.springframework.stereotype.Service;


/**
 * 参数元数据(PrintMeta)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:01
 */
@Service("printMetaService")
public class PrintMetaServiceImpl extends MybatisCrudServiceImpl<PrintMetaMapper, PrintMetaEntity> implements PrintMetaService {

}

