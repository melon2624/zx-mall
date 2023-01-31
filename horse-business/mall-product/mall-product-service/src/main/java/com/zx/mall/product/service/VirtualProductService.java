package com.zx.mall.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zx.mall.product.mapper.VirtualProductMapper;
import com.zx.mall.product.model.dto.admin.VirtualProductModifyDTO;
import com.zx.mall.product.model.entity.VirtualProduct;

import com.zx.mall.product.model.vo.admin.VirtualProductVO;
import com.zx.mall.product.service.convert.VirtualProductConvert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 虚拟商品表(VirtualProduct)表服务实现类
 *
 * @author zhangxin
 * @date 2023/1/6 20:22
 */
@Service("virtualProductService")
public class VirtualProductService extends ServiceImpl<VirtualProductMapper, VirtualProduct> {

    @Resource
    VirtualProductConvert virtualProductConvert;

    @Resource
    VirtualProductMapper virtualProductMapper;

    public void deleteAllAndAdd(Long productId, List<VirtualProductModifyDTO> virtualProductModifyDTOList) {
        virtualProductMapper.deleteByProductId(productId);
        List<VirtualProduct> virtualProducts = virtualProductConvert.toEntity(virtualProductModifyDTOList);
        this.saveBatch(virtualProducts);
    }

    public List<VirtualProductVO> listVirtualProduct(Long productId) {
        return virtualProductConvert.toVo(this.lambdaQuery().eq(Objects.nonNull(productId), VirtualProduct::getProductId, productId).list());
    }
}
