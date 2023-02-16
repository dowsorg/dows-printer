package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.entity.PrinterStateEntity;
import org.dows.printer.mapper.PrinterStateMapper;
import org.dows.printer.service.PrinterStateService;
import org.springframework.stereotype.Service;


/**
 * 打印机(PrinterState)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
@Service("PrinterStateService")
public class PrinterStateServiceImpl extends MybatisCrudServiceImpl<PrinterStateMapper, PrinterStateEntity> implements PrinterStateService {

}

