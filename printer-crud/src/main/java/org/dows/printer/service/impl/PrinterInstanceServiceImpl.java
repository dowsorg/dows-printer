package org.dows.printer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.entity.PrinterInstanceEntity;
import org.dows.printer.mapper.PrinterInstanceMapper;
import org.dows.printer.service.PrinterInstanceService;
import org.dows.printer.vo.PrintTypeVo;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 打印机(PrinterInstance)表服务实现类
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
@Service("printerInstanceService")
public class PrinterInstanceServiceImpl extends MybatisCrudServiceImpl<PrinterInstanceMapper, PrinterInstanceEntity> implements PrinterInstanceService {

    @Override
    public List<PrintTypeVo> getSpuPrintType(QueryWrapper wrapper) {
        return baseMapper.getSpuPrintType(wrapper);
    }
}

