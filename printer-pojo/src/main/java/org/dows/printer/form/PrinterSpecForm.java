package org.dows.printer.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 打印机规格(PrinterSpec)实体类
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrinterSpec对象", description = "打印机规格")
public class PrinterSpecForm {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;


    @ApiModelProperty("规格名称")
    private String specName;


    @ApiModelProperty("机器颜色[]")
    private String color;


    @ApiModelProperty("机器大小[长*宽*高]")
    private String pkgSize;


    @ApiModelProperty("打印支持大小[长*宽]")
    private String printSize;


    @ApiModelProperty("其他规格")
    private String otherSpec;


    @ApiModelProperty("打印份数")
    private Integer count;


    @ApiModelProperty("")
    private Boolean multiColors;


}

