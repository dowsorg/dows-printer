package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrintMetaEntity;

/**
 * 参数元数据(PrintMeta)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:01
 */
@Mapper
public interface PrintMetaMapper extends MybatisCrudMapper<PrintMetaEntity> {

}

