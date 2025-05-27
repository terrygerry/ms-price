package com.technical_test.ms_price.domain.model;

import java.time.LocalDateTime;

public class PriceSearchCriteria {
    private Integer productId;
    private Integer brandId;
    private LocalDateTime dateFrom;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }
}
