package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrinterChannelEntity;

/**
 * 打印机通道(PrinterChannel)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:03
 */
@Mapper
public interface PrinterChannelMapper extends MybatisCrudMapper<PrinterChannelEntity> {

}

