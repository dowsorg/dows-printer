package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrinterSpecMapper;
import org.dows.printer.entity.PrinterSpecEntity;
import org.dows.printer.service.PrinterSpecService;
import org.springframework.stereotype.Service;


/**
 * 打印机规格(PrinterSpec)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
@Service("printerSpecService")
public class PrinterSpecServiceImpl extends MybatisCrudServiceImpl<PrinterSpecMapper, PrinterSpecEntity> implements PrinterSpecService {

}

