package org.dows.print.utils.third.print;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
    private List<DetailDishesVo> voList;

    //总计金额
    private String amountTotal;

    //地址
    private String address;

    //电话
    private String phone;

    //其他消费信息
    private String affiliatePriceJson;

}
