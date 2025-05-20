package com.technical_test.ms_price.application.mapper;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductPriceQueryDtoMapper {
    PriceSearchCriteria toPriceSearchCriteria(ProductPriceQueryDTO productPriceQueryDTO);
}
