package com.engineeringthesis.visitservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "openai-analysis-service", url = "http://localhost:8083")
public interface OpenAIClient {
    @PostMapping("/api/v1/analyzeReport")
    String analyzeReport(@RequestBody String query);
}
