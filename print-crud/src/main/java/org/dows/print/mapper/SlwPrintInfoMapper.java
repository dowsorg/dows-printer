package org.dows.print.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.print.entity.SlwPrintInfo;

/**
 * 门店-业态字典(StoreEco)表数据库访问层
 *
 * @author lait.zhang
 * @since 2022-10-31 13:56:49
 */
@Mapper
public interface SlwPrintInfoMapper extends MybatisCrudMapper<SlwPrintInfo> {

}

