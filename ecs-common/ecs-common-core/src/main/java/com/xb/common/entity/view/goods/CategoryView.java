package com.xb.common.entity.view.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author xb
 * @since 2019-09-21
 */
@Data
@ApiModel(value="Category对象", description="")
public class CategoryView implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父级目录id")
    private Integer parentId;

    @ApiModelProperty(value = "级别(1:一级 2：二级 3：三级)")
    private Integer level;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "状态(1：正常 2：删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
