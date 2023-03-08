package org.dows.printer.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.printer.entity.PrinterInstanceEntity;
import org.dows.printer.vo.PrintTypeVo;

import java.util.List;

/**
 * 打印机(PrinterInstance)表数据库访问层
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
@Mapper
public interface PrinterInstanceMapper extends MybatisCrudMapper<PrinterInstanceEntity> {

    @Select("SELECT room_name FROM store_table")
    String getRoomNameByTableNo(@Param(Constants.WRAPPER) QueryWrapper wrapper);

    @Select("SELECT oi.spu_name,gs.category_name FROM order_item oi LEFT JOIN goods_spu gs ON oi.spu_id = gs.id ${ew.customSqlSegment}")
    List<PrintTypeVo> getSpuPrintType(@Param(Constants.WRAPPER) QueryWrapper wrapper);


}

