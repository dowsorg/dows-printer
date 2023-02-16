package org.dows.printer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrintTemplateEntity;

/**
 * 应用打印模板(PrintTemplate)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:02
 */
@Mapper
public interface PrintTemplateMapper extends MybatisCrudMapper<PrintTemplateEntity> {

}

