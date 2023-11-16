package com.xxxxxx.web;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ValidDemo {

    @Min(value = 1, message = "订单编号过小")
    @Max(value = 100, message = "订单编号过大")
    @NotNull(message = "订单编号不能为空")
    private Integer orderId;

    @NotBlank(message = "订单名称不能为空")
    private String orderName;

    @Size(min = 1, max = 10)
    private List<String> goodsList;

    @DecimalMin(value = "1", message = "订单金额必须大于等于1")
    private BigDecimal amount;
}