package org.dows.printer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
 * 打印机信息
 * 由GenEntityMysql类自动生成
 * Fri Mar 04 16:36:33 CST 2022
 *
 * @xuanxy
 */
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrintApi对象", description = "打印三方接口")
public class PrintApi {

    /**
     * 主键id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private String apiId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 编辑时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;

    /**
     * 门店id
     */
    private String storeId;

    /**
     * 打印机种类 1大趋
     */
    private Integer printType;

    /**
     * 接口url
     */
    private String url;

    /**
     * 数据状态 1正常 2已删除
     */
    private Integer dataStatus;


}

