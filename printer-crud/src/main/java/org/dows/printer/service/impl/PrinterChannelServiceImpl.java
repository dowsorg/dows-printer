package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrinterChannelMapper;
import org.dows.printer.entity.PrinterChannelEntity;
import org.dows.printer.service.PrinterChannelService;
import org.springframework.stereotype.Service;


/**
 * 打印机通道(PrinterChannel)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:03
 */
@Service("printerChannelService")
public class PrinterChannelServiceImpl extends MybatisCrudServiceImpl<PrinterChannelMapper, PrinterChannelEntity> implements PrinterChannelService {

}

