package org.dows.printer.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 应用打印模板(PrintTemplate)实体类
 *
 * @author lait
 * @since 2023-02-06 18:25:02
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrintTemplate对象", description = "应用打印模板")
public class PrintTemplateForm {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;


    @ApiModelProperty("应用打印机ID")
    private String appPirnterId;


    @ApiModelProperty("打印机ID")
    private String printerId;


    @ApiModelProperty("模板名称")
    private String templateName;


    @ApiModelProperty("模板content")
    private String content;


    @ApiModelProperty("接口映射ID")
    private String printMappingId;


}

