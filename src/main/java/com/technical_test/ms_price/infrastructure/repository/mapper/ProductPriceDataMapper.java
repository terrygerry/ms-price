package com.technical_test.ms_price.infrastructure.repository.mapper;

import com.technical_test.ms_price.domain.model.ProductPrice;
import com.technical_test.ms_price.infrastructure.repository.data.ProductPriceData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductPriceDataMapper {
    ProductPrice toProductPrice(ProductPriceData productPriceData);
}
