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
 * 接口映射(PrintMapping)实体类
 *
 * @author lait
 * @since 2023-02-06 18:25:01
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrintMapping对象", description = "接口映射")
@TableName("print_mapping")
public class PrintMappingEntity implements CrudEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("映射ID")
    private String mappingId;

    @ApiModelProperty("通道ID")
    private String channelId;

    @ApiModelProperty("映射通道ID")
    private String toChannelId;

    @ApiModelProperty("接口ID")
    private String apiId;

    @ApiModelProperty("映射接口ID")
    private String toApIdi;

    @ApiModelProperty("接口URI")
    private String api;

    @ApiModelProperty("映射接口URI")
    private String toApi;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

}

