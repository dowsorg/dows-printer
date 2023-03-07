package org.dows.printer.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.dows.printer.entity.PrinterInstanceEntity;
import org.dows.framework.crud.mybatis.MybatisCrudService;
import org.dows.printer.vo.PrintTypeVo;

import java.util.List;


/**
 * 打印机(PrinterInstance)表服务接口
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
public interface PrinterInstanceService extends MybatisCrudService<PrinterInstanceEntity> {

    List<PrintTypeVo> getSpuPrintType(QueryWrapper wrapper);
}

