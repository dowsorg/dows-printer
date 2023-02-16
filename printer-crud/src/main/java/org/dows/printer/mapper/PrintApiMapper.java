package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrintApiEntity;

/**
 * 打印接口(PrintApi)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:00
 */
@Mapper
public interface PrintApiMapper extends MybatisCrudMapper<PrintApiEntity> {

}

