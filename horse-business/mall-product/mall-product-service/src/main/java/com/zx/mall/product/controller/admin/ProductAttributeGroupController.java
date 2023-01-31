package com.zx.mall.product.controller.admin;

import com.zx.mall.product.model.dto.admin.ProductAttributeDTO;
import com.zx.mall.product.model.dto.admin.ProductAttributeGroupDTO;
import com.zx.mall.product.model.dto.admin.SortModifyDTO;
import com.zx.mall.product.model.vo.admin.ProductAttributeGroupVO;
import com.zx.mall.product.service.ProductAttributeGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zhangxin
 * @date 2023/1/10 14:08
 */
@Api(tags = "后管-商品属性组")
@RestController
@RequestMapping("admin/productAttributeGroup")
public class ProductAttributeGroupController {


    @Resource
    private ProductAttributeGroupService productAttributeGroupService;

    @ApiOperation("根据商品id查询商品属性组列表")
    @GetMapping("/getProductAttributeGroupList/{productId}")
    public List<ProductAttributeGroupVO> list(@PathVariable Long productId) {
        return this.productAttributeGroupService.getVOByProductId(productId);
    }

    @ApiOperation("新增商品属性组，返回id")
    @PostMapping("addProductAttributeGroup")
    public Long save(@RequestBody @Valid ProductAttributeGroupDTO productAttributeGroupDTO) {
        return this.productAttributeGroupService.save(productAttributeGroupDTO);
    }

    @ApiOperation("更新商品属性组")
    @PutMapping("/updateProductAttributeGroup/{id}")
    public Boolean update(@PathVariable Long id, @RequestBody @Valid ProductAttributeGroupDTO productAttributeGroupDTO) {
        return this.productAttributeGroupService.update(id, productAttributeGroupDTO);
    }

    @ApiOperation("更新商品属性组排序")
    @PutMapping("/updateSort")
    public Boolean updateSort(@RequestBody @Valid SortModifyDTO sortModifyDTO) {
        return this.productAttributeGroupService.updateSort(sortModifyDTO);
    }

    @ApiOperation("删除商品属性")
    @DeleteMapping
    public Boolean delete(@ApiParam("id（多个用逗号分隔）") @RequestParam("id") List<Long> idList) {
        return productAttributeGroupService.delete(idList);
    }


}
