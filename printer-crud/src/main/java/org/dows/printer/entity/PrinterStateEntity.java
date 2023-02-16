package org.dows.printer.entity;

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

import java.util.Date;

/**
 * 打印机(PrinterInstance)实体类
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
@ApiModel(value = "PrinterState对象", description = "打印机-状态")
@TableName("printer_state")
public class PrinterStateEntity implements CrudEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("打印机ID")
    private String printerNo;

    @ApiModelProperty("设备联网状态: 0 当前离线 1 在线")
    private Integer onlineStatus;

    @ApiModelProperty("设备工作状态: 0 就绪, 1 打印中, 2 缺纸, 3 过温, 4 打印故障 -1 当前离线")
    private Integer workStatus;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("时间戳")
    private Date dt;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

}

