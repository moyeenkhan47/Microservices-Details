package com.user.configuration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.model.OrderReport;

@FeignClient(name = "PRODUCT-SERVICE") 
public interface FeignClientCall {

    @PostMapping("/api/product/order")
    OrderReport order(@RequestBody OrderReport orderReport);
}
