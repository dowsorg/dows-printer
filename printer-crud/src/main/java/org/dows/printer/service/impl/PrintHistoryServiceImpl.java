package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrintHistoryMapper;
import org.dows.printer.entity.PrintHistoryEntity;
import org.dows.printer.service.PrintHistoryService;
import org.springframework.stereotype.Service;


/**
 * 打印记录(PrintHistory)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:01
 */
@Service("printHistoryService")
public class PrintHistoryServiceImpl extends MybatisCrudServiceImpl<PrintHistoryMapper, PrintHistoryEntity> implements PrintHistoryService {

}

