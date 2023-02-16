package org.dows.printer.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.dows.framework.crud.mybatis.CrudEntity;

/**
 * 打印参数(PrintParams)实体类
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
@ApiModel(value = "PrintParams对象", description = "打印参数")
@TableName("print_params")
public class PrintParamsEntity implements CrudEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("接口ID")
    private String apiId;

    @ApiModelProperty("通道ID")
    private String channelId;

    @ApiModelProperty("参数名")
    private String paramName;

    @ApiModelProperty("参数code")
    private String paramCode;

    @ApiModelProperty("参数默认值")
    private String defaultValue;

    @ApiModelProperty("获取 参数的接口")
    private String dataApi;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

    /**
     * 参数类型   1、请求头    2、请求体   3、请求行    4、响应体
     */
    @ApiModelProperty("参数类型 1、请求头 2、请求体 3、请求行 4、响应体")
    private Integer type;

}

