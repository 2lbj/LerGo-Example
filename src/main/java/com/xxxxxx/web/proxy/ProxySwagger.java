package com.xxxxxx.web.proxy;

import com.lergo.framework.annotation.RawResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.lergo.framework.entity.CommonResult.gson;

@RestController
@Hidden
public class ProxySwagger {

    //@Value("${domain}")
    String domain;
    //@Value("${atomic-uri.xxx}")
    String atomicURI_XXX;

    RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/v3/api-docs/api-xxx-docs", produces = "application/json")
    @RawResponse
    public String eosSwagger() {
        OpenAPI openAPI = gson.fromJson(restTemplate.exchange(
                atomicURI_XXX + "/v3/api-docs/api",
                HttpMethod.GET, null, String.class).getBody(), OpenAPI.class);
        openAPI.getServers().forEach(server -> server.setUrl("https://%s/mgnt-eos-atomic".formatted(domain)));
        return gson.toJson(openAPI);
    }

}

