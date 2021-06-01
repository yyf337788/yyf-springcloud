package com.xb.common.entity.dto.goods;

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
@ApiModel(value="Product对象", description="")
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer pageSize;
    private Integer pageNum;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "商品价格")
    private Integer minPrice;
    @ApiModelProperty(value = "商品价格")
    private Integer maxPrice;
    @ApiModelProperty(value = "库存数量")
    private Integer stock;

    @ApiModelProperty(value = "分类1")
    private Integer level1Id;

    @ApiModelProperty(value = "分类2")
    private Integer level2Id;

    @ApiModelProperty(value = "分类3")
    private Integer level3Id;

    @ApiModelProperty(value = "主图名称")
    private String mainImg;

    @ApiModelProperty(value = "副图地址")
    private String subImgs;

    @ApiModelProperty(value = "状态(1：在售 2：下架 3：已删除)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
