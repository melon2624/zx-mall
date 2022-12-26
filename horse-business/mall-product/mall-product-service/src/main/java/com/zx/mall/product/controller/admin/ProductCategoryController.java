package com.zx.mall.product.controller.admin;

import com.zx.mall.product.model.dto.admin.ProductCategoryModifyDTO;
import com.zx.mall.product.model.dto.admin.ProductCategoryQueryDTO;
import com.zx.mall.product.model.vo.admin.ProductCategoryVO;
import com.zx.mall.product.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.License;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangxin
 * @date 2022/12/20 11:29
 */
@Api(tags = "后台管理-商品分类")
@RestController
@RequestMapping("admin/productCategory")
public class ProductCategoryController {

    @Resource
    ProductCategoryService productCategoryService;

    @ApiOperation("查询分类列表（树状结构）")
    public List<ProductCategoryVO> list(ProductCategoryQueryDTO productCategoryQueryDTO) {

        return productCategoryService.listUserTree(productCategoryQueryDTO);
    }

    @ApiOperation("添加分类")
    @PostMapping("/addProductCategory")
    public Boolean save(@RequestBody @Validated ProductCategoryModifyDTO productCategoryModifyDTO) {

        return this.productCategoryService.save(productCategoryModifyDTO);
    }


}
