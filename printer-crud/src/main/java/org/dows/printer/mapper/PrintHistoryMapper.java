package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrintHistoryEntity;

/**
 * 打印记录(PrintHistory)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:01
 */
@Mapper
public interface PrintHistoryMapper extends MybatisCrudMapper<PrintHistoryEntity> {

}

