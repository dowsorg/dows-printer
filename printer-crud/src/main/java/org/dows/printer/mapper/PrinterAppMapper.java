package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrinterAppEntity;

/**
 * 打印机应用(PrinterApp)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:03
 */
@Mapper
public interface PrinterAppMapper extends MybatisCrudMapper<PrinterAppEntity> {

}

