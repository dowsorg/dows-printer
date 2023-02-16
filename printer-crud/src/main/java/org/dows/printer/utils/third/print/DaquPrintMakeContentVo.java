package org.dows.printer.utils.third.print;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DaquPrintMakeContentVo {

    //桌号
    private String tableNo;

    //菜名
    private String dishesName;

    //份数
    private String num;

    //备注
    private String comment;

    //类型 1、制作单  2、加菜  3退菜
    private Integer type;

}
