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
                "Prosze przeanalizowac raporty z wizyt kobiety u ginekologa i ocenic ryzyko zagrozenia ciazy na podstawie podanych raportow z wizyt:"
                + query);

        log.info("Starting query: " + queryRequest.getQuery());

        return openAIAnalysisService.analyzeReport(queryRequest.getQuery());
    }

    @PostMapping("/generateDietPlan")
    public String generateDietPlan(@RequestBody String query) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(
                "Wygeneruj plan dietetyczny dla kobiety w ciazy z podanymi ponizej wynikami badan." +
                        "Podaj to w formacie: [Dzien tygodnia]: [Nazwa dania na sniadanie], [Nazwa dania na obiad], [Nazwa dania na kolacje]." +
                        "Uwzglednij wyniki badan, wiek kobiety i zalecenia lekarza "
                        + query);

        log.info("Starting query: " + queryRequest.getQuery());

        return openAIAnalysisService.analyzeReport(queryRequest.getQuery());
    }
}
