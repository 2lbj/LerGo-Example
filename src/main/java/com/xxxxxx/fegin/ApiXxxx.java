package com.xxxxxx.fegin;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("http://localhost:9999")
public interface ApiXxxx {

    @HttpExchange(method = "GET", url = "//{id}")
    String getById(@PathVariable Integer id);
}
