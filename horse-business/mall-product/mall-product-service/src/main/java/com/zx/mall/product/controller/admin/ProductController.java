package com.zx.mall.product.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zx.mall.product.model.dto.admin.ProductModifyDTO;
import com.zx.mall.product.model.dto.admin.ProductQueryDTO;
import com.zx.mall.product.model.vo.admin.ProductModifyVO;
import com.zx.mall.product.model.vo.admin.ProductVO;
import com.zx.mall.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zhangxin
 * @date 2023/1/3 16:00
 */
@Api(tags = "后管-商品管理")
@RestController
@RequestMapping("admin/product")
public class ProductController {

    /**
     * 服务对象
     */
    @Resource
    private ProductService productService;

    @ApiOperation("查询单个商品")
    @GetMapping("{id}")
    public ProductModifyVO getOne(@PathVariable Long id) {
        return productService.getOne(id);
    }

    @ApiOperation("商品列表")
    @GetMapping("page")
    public IPage<ProductVO> page(ProductQueryDTO productQueryDTO) {
        return productService.page(productQueryDTO);
    }

    @ApiOperation("新增商品并返回id（保存第一页商品信息，商品处于未上架状态）")
    @PostMapping("/addProduct")
    public Long save(@RequestBody @Valid ProductModifyDTO productModifyDTO) {
        return productService.save(productModifyDTO);
    }

    @ApiOperation("删除商品")
    @Delete("/deleteProduct")
    public Boolean delete(@ApiParam("商品id（多个用逗号分隔）") @RequestParam("id") List<Long> idList) {
        return productService.delete(idList);
    }

    @ApiOperation("修改商品")
    @PutMapping("updateProduct/{id}")
    public Boolean update(@PathVariable Long id, @RequestBody @Valid ProductModifyDTO productModifyDTO) {
        return productService.update(id, productModifyDTO);
    }

    @ApiOperation("商品上下架")
    @PutMapping("enable/{id}")
    public Boolean enable(@PathVariable Long id, @RequestParam("isEnable") Boolean isEnable) {
        return productService.enableOrDisable(id, isEnable);
    }


}
