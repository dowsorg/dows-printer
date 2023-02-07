package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrintMappingEntity;

/**
 * 接口映射(PrintMapping)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:01
 */
@Mapper
public interface PrintMappingMapper extends MybatisCrudMapper<PrintMappingEntity> {

}

