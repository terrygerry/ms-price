package com.technical_test.ms_price.application.mapper;

import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.domain.model.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceEntityMapper {
    ProductPriceDTO toProductPriceDTO(PriceEntity priceEntity);
}
