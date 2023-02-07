package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrintApiMapper;
import org.dows.printer.entity.PrintApiEntity;
import org.dows.printer.service.PrintApiService;
import org.springframework.stereotype.Service;


/**
 * 打印接口(PrintApi)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:00
 */
@Service("printApiService")
public class PrintApiServiceImpl extends MybatisCrudServiceImpl<PrintApiMapper, PrintApiEntity> implements PrintApiService {

}

