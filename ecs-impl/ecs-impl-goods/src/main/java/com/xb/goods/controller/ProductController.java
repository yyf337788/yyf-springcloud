package com.xb.goods.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xb.common.base.controller.BaseController;
import com.xb.common.base.ret.ApiResult;
import com.xb.common.entity.dto.goods.ProductDto;
import com.xb.common.entity.dto.member.UserDto;
import com.xb.common.entity.model.goods.Product;
import com.xb.common.utils.FieldsFilter;
import com.xb.goods.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author com/xb
 * @since 2021-05-19
 */
@Slf4j
@RestController
@RequestMapping("goods/product/api/v1")
@Api(tags = "商品服务API")
public class ProductController extends BaseController {

    @Autowired
    private IProductService productService;

    @ApiOperation(value = "查询所有的商品列表")
    @GetMapping("getProductList")
    public ApiResult getProductList(){
        List<Product> products = productService.list();
        log.info("products:   "+products);
        return ApiResult.success().put("products", products);
    }

    @ApiOperation(value = "根据条件查询商品列表")
    @GetMapping("getProductInfo")
    public ApiResult getProductInfo(ProductDto dto){
        IPage page = new Page(dto.getPageNum(),dto.getPageSize());
        QueryWrapper queryWrapper = new QueryWrapper();
        //queryWrapper.like("name", dto.getName());
        queryWrapper.ge("price", dto.getMinPrice());
        queryWrapper.le("price", dto.getMaxPrice());
        queryWrapper.eq("status", dto.getStatus());

        IPage page1 = productService.page(page, queryWrapper);
        List pageList = page1.getRecords();
        return ApiResult.success().put("products", page.setRecords(FieldsFilter.filter(pageList, Arrays.asList("id","name","price"))));
    }

    @ApiOperation(value="根据ID查询商品")
    @GetMapping("getProductById")
    public ApiResult getProductById(Integer id){
        return ApiResult.success().put("product",productService.getById(id));
    }

    @ApiOperation(value = "添加商品")
    @PostMapping("addProduct")   //Product.Save.class校验规则分组；@RequestBody json对象转换成java对象
    public ApiResult addProduct(@Validated(Product.Save.class) @RequestBody Product product){
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        product.setStatus(1);
        if(productService.save(product)){
            return ApiResult.success();
        }else {
            return ApiResult.error("保存失败");
        }
    }

    @ApiOperation(value = "更新商品")
    @PostMapping("updateProduct")
    public ApiResult updateProduct(@Validated(Product.Update.class) @RequestBody  Product product){
        product.setUpdateTime(new Date());
        if(productService.updateById(product)){
            return ApiResult.success();
        }else {
            return  ApiResult.error("商品更新失败");
        }
    }

    @ApiOperation(value = "删除商品")
    @PostMapping("deleteProduct")
    public ApiResult deleteProduct(Integer id){
        if(productService.removeById(id)){
            return ApiResult.success();
        }else {
            return ApiResult.error("删除失败");
        }
    }

}
