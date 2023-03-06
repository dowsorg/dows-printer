package org.dows.printer.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "JuhePrintContentVo对象", description = "小票组装内容")
public class JuhePrintContentVo {

    @ApiModelProperty("店名")
    private String storeName;

    @ApiModelProperty("桌号")
    private String tableNo;

    @ApiModelProperty("人数")
    private String pepNum;

    @ApiModelProperty("操作员")
    private String operator;

    @ApiModelProperty("菜品集合")
    private List<DetailDishesVo> dishesVoList;

    @ApiModelProperty("总计金额")
    private String amountTotal;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("小票类型    1-总单  2-结账单（优惠信息）  3、制作单  4、加菜  5退菜")
    private Integer type;

    @ApiModelProperty("优惠信息")
    private String affiliatePriceJson;

}
