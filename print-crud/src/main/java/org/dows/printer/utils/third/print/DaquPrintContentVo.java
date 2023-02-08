package org.dows.printer.utils.third.print;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaquPrintContentVo {

    //店名
    private String storeName;

    //桌号
    private String tableNo;

    //人数
    private String pepNum;

    //操作员
    private String operator;

    //菜品集合
    private List<DetailDishesVo> dishesVoList;

    //总计金额
    private String amountTotal;

    //地址
    private String address;

    //电话
    private String phone;

    //小票类型   1-总单  2-结账单（优惠信息）  3、制作单  4、加菜  5退菜
    private int type;

    //优惠信息
    private String affiliatePriceJson;

}
