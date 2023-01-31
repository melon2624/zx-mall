package com.zx.mall.product.controller.admin;

import com.zx.mall.product.model.dto.admin.ProductCategoryModifyDTO;
import com.zx.mall.product.model.dto.admin.ProductCategoryQueryDTO;
import com.zx.mall.product.model.dto.admin.SortModifyDTO;
import com.zx.mall.product.model.vo.admin.ProductCategoryVO;
import com.zx.mall.product.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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

    @ApiOperation("转移商品")
    @PutMapping("transfer/{oldId}/{newId}")
    public Integer transfer(@PathVariable Long oldId, @PathVariable Long newId) {
        return this.productCategoryService.transfer(oldId, newId);
    }

    @ApiOperation("更新排序")
    @PutMapping("updateSort")
    //TODO
    public Boolean updateSort(@RequestBody @Validated SortModifyDTO sortModifyDTO) {
        return this.productCategoryService.updateSort(sortModifyDTO);
    }


    @ApiOperation("更新分类")
    @PutMapping("updateProductCategory/{id}")
    public Boolean update(@PathVariable Long id, @RequestBody @Valid ProductCategoryModifyDTO productCategoryModifyDTO) {
        return this.productCategoryService.update(id, productCategoryModifyDTO);
    }


    @ApiOperation("删除分类")
    @DeleteMapping("/deleteProductCategory")
    public Boolean delete(@ApiParam("分类id（多个用逗号分隔）") @RequestParam("id") List<Long> idList) {
        System.out.println("zzz");
        return null;
    }


}
