package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.mapper.PrintParamsMapper;
import org.dows.printer.entity.PrintParamsEntity;
import org.dows.printer.service.PrintParamsService;
import org.springframework.stereotype.Service;


/**
 * 打印参数(PrintParams)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:02
 */
@Service("printParamsService")
public class PrintParamsServiceImpl extends MybatisCrudServiceImpl<PrintParamsMapper, PrintParamsEntity> implements PrintParamsService {

}

