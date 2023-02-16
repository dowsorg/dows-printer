package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrinterStateEntity;

/**
 * 打印机(PrinterInstance)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
@Mapper
public interface PrinterStateMapper extends MybatisCrudMapper<PrinterStateEntity> {

}

