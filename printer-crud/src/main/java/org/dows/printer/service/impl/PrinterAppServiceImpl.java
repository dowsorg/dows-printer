package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrinterAppMapper;
import org.dows.printer.entity.PrinterAppEntity;
import org.dows.printer.service.PrinterAppService;
import org.springframework.stereotype.Service;


/**
 * 打印机应用(PrinterApp)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:03
 */
@Service("printerAppService")
public class PrinterAppServiceImpl extends MybatisCrudServiceImpl<PrinterAppMapper, PrinterAppEntity> implements PrinterAppService {

}

