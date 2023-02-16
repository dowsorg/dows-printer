package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrinterInstanceEntity;

/**
 * 打印机(PrinterInstance)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
@Mapper
public interface PrinterInstanceMapper extends MybatisCrudMapper<PrinterInstanceEntity> {

}

