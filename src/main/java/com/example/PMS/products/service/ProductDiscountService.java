package com.example.PMS.products.service;
import com.example.PMS.products.entity.ProductEntity;
import com.example.PMS.products.enums.DiscountType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ProductDiscountService {

    public BigDecimal calculateDiscountAmount(ProductEntity product){
        BigDecimal price = product.getPrice();
        BigDecimal discountValue = product.getDiscountValue();
        DiscountType discountType = product.getDiscountType();

        if (price == null || discountValue == null || discountType == null){
            throw new IllegalArgumentException("Price, discount value and discount type are required");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException(
                    "Price must be greater than zero"
            );
        }

        if (discountValue.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException(
                    "Discount value cannot be negative"
            );

        }

        BigDecimal discountAmount;

        switch (discountType){
            case NONE -> discountAmount = BigDecimal.ZERO;
            case PERCENTAGE -> {
                if (discountValue.compareTo(new BigDecimal("100"))>0){
                    throw new IllegalArgumentException(
                            "Percentage discount cannot exceed 100%"
                    );
                }

                discountAmount = price
                        .multiply(discountValue)
                        .divide(new BigDecimal("100"),2, RoundingMode.HALF_UP);
            }
            case FIXED -> {
                if (discountValue.compareTo(price)>0){
                    throw new IllegalArgumentException(
                            "Fixed discount cannot exceed product price"
                    );
                }
                discountAmount = discountValue;
            }
            default -> throw new IllegalArgumentException("Unsupported discount type");
        }
        return discountAmount;
    }

    public BigDecimal calculateFinalPrice (ProductEntity product){
        BigDecimal discountAmount = calculateDiscountAmount(product);
        return product.getPrice()
                .subtract(discountAmount)
                .setScale(2,RoundingMode.HALF_UP);
    }
}
