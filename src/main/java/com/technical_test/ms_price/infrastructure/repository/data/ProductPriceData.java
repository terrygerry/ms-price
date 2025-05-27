package com.technical_test.ms_price.infrastructure.repository.data;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("prices")
public class ProductPriceData {

    @Id
    private Integer id;
    @Column("brand_id")
    private Integer brandId;
    @Column("start_date")
    private LocalDateTime startDate;
    @Column("end_date")
    private LocalDateTime endDate;
    @Column("price_list")
    private Integer priceList;
    @Column("product_id")
    private Integer productId;
    @Column("priority")
    private int priority;
    @Column("price")
    private BigDecimal price;
    @Column("currency")
    private String currency;

}
