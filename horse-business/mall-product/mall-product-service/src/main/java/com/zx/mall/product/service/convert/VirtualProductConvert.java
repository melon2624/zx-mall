package com.zx.mall.product.service.convert;

import com.zx.mall.product.model.dto.admin.VirtualProductModifyDTO;
import com.zx.mall.product.model.entity.VirtualProduct;
import com.zx.mall.product.model.vo.admin.VirtualProductVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author zhangxin
 * @date 2023/1/7 9:22
 */
@Mapper(componentModel = "spring")
public interface VirtualProductConvert {


    List<VirtualProduct> toEntity(List<VirtualProductModifyDTO> virtualProductModifyDTOList);

    List<VirtualProductVO> toVo(List<VirtualProduct> virtualProductList);
}
