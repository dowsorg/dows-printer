package org.dows.printer.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 打印模板库(PrintTemplates)实体类
 *
 * @author lait
 * @since 2023-02-06 18:25:03
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrintTemplates对象", description = "打印模板库")
public class PrintTemplatesForm {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;


    @ApiModelProperty("模板ID")
    private String templateId;


    @ApiModelProperty("模板分类名称")
    private String categName;


    @ApiModelProperty("模板名称")
    private String templateName;


    @ApiModelProperty("模板content")
    private String content;


}

