package org.dows.printer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrintStateEnum {
    //打印状态码  0 待打印，1 打印中，2 打印成功 3 打印失败
    print_ready("待打印",0),
    print_ing("打印中",1),
    print_ok("打印成功",2),
    print_fail("打印失败",3),
    print_unok("打印未完成",4),
    ;

    private String name;

    private Integer code;


}
