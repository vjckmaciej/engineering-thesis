package com.engineeringthesis.openaianalysisservice.controller;

import com.engineeringthesis.openaianalysisservice.model.QueryRequest;
import com.engineeringthesis.openaianalysisservice.service.OpenAIAnalysisServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class OpenAIAnalysisControllerImpl {
    private final OpenAIAnalysisServiceImpl openAIAnalysisService;

    @PostMapping("/analyzeReport")
    public String analyzeReport(@RequestBody String query) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(
                "Proszę przeanalizować raporty z wizyt kobiety u ginekologa i ocenić ryzyko ciąży na podstawie podanych raportów z wizyt:"
                + query);

        log.info("Starting query: " + queryRequest.getQuery());

        return openAIAnalysisService.analyzeReport(queryRequest.getQuery());
    }
}
