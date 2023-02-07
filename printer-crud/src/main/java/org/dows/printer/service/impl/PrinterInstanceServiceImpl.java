package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrinterInstanceMapper;
import org.dows.printer.entity.PrinterInstanceEntity;
import org.dows.printer.service.PrinterInstanceService;
import org.springframework.stereotype.Service;


/**
 * 打印机(PrinterInstance)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
@Service("printerInstanceService")
public class PrinterInstanceServiceImpl extends MybatisCrudServiceImpl<PrinterInstanceMapper, PrinterInstanceEntity> implements PrinterInstanceService {

}

