package com.zx.mall.product.controller.admin;

import com.zx.mall.product.model.dto.admin.ProductSkuDTO;
import com.zx.mall.product.model.vo.admin.ProductSkuVO;
import com.zx.mall.product.service.ProductSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author zhangxin
 * @date 2023/1/29 13:26
 */
@Api(tags = "后管-商品sku和库存")
@RestController
@RequestMapping("admin/productSku")
public class ProductSkuController {

    /**
     * 服务对象
     */
    @Resource
    ProductSkuService productSkuService;

    @ApiOperation("根据商品id查询sku列表")
    @GetMapping("list/{productId}")
    public List<ProductSkuVO> listByProductId(@PathVariable Long productId) {
        return this.productSkuService.listVo(productId);
    }


    @PostMapping("saveProductSku/{productId}")
    public Boolean save(@PathVariable Long productId, @RequestBody @Valid List<ProductSkuDTO> productSkuDTOList) {
        return this.productSkuService.save(productId, productSkuDTOList);
    }

    @ApiOperation("删除所有sku并新增sku（新增或删除属性组后保存sku列表时使用）")
    @PostMapping("deleteAllAndAdd/{productId}")
    public Boolean deleteAllAndAdd(@PathVariable Long productId, @RequestBody @Valid List<ProductSkuDTO> productSkuDTOList) {
        productSkuService.deleteAllByProductId(productId);
        return productSkuService.save(productId, productSkuDTOList);
    }

}
