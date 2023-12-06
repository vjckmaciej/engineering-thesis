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

    @PostMapping("/analyzeVisit")
    public String analyzeVisit(@RequestBody String query) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(
                "Prosze przeanalizowac raport z podanej wizyty kobiety u ginekologa i ocenic ryzyko zagrozenia ciazy na podstawie podanych danych:"
                        + query);

        log.info("Starting query: " + queryRequest.getQuery());

        return openAIAnalysisService.analyzeReport(queryRequest.getQuery());
    }

    @PostMapping("/generateDietPlan")
    public String generateDietPlan(@RequestBody String query) {
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(
                "Wygeneruj plan dietetyczny dla kobiety w ciazy z podanymi ponizej wynikami badan." +
                        " Uwzglednij wyniki badan, wiek kobiety, zalecenia lekarza, liczbe podanych dni dla diety, " +
                        " liczbe dan w ciagu dnia, przedzial kaloryczny oraz podane alergeny. Oto lista rzeczy ktore ta kobieta wymaga od diety: "
                        + query);

        log.info("Starting query: " + queryRequest.getQuery());

        return openAIAnalysisService.analyzeReport(queryRequest.getQuery());
    }
}
