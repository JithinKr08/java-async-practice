package com.learnjava.service;

import com.learnjava.domain.ProductInfo;
import com.learnjava.domain.ProductOption;

import java.util.List;

import static com.learnjava.util.CommonUtil.delay;

public class ProductInfoService {

    public ProductInfo retrieveProductInfo(String productId) {
        System.out.println("----->Strated ProductInfo");
        delay(20000);
        List<ProductOption> productOptions = List.of(new ProductOption(1, "64GB", "Black", 699.99),
                new ProductOption(2, "128GB", "Black", 749.99));
        System.out.println("<-----returning ProductInfo");
        return ProductInfo.builder().productId(productId)
                .productOptions(productOptions)
                .build();
    }
}
