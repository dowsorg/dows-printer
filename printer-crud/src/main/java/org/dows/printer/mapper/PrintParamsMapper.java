package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrintParamsEntity;

/**
 * 打印参数(PrintParams)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:02
 */
@Mapper
public interface PrintParamsMapper extends MybatisCrudMapper<PrintParamsEntity> {

}

