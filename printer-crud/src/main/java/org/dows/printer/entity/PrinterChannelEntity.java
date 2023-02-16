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
 * 打印机通道(PrinterChannel)实体类
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
@ApiModel(value = "PrinterChannel对象", description = "打印机通道")
@TableName("printer_channel")
public class PrinterChannelEntity implements CrudEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("通道ID")
    private String channelId;

    @ApiModelProperty("通道名")
    private String channelName;

    @ApiModelProperty("通道code")
    private String channelCode;

    @ApiModelProperty("通道联系人")
    private String channelContact;

    @ApiModelProperty("通道开发文档地址")
    private String channelWiki;

    @ApiModelProperty("通道商户号")
    private String channelAccount;

    @ApiModelProperty("通道密钥")
    private String channelSecret;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

}

