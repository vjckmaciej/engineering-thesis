package com.engineeringthesis.openaianalysisservice.controller;

import com.engineeringthesis.openaianalysisservice.model.ConversationRequest;
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

    private String replacePolishCharacters(String input) {
        String[][] chars = {{"ą", "a"}, {"ć", "c"}, {"ę", "e"}, {"ł", "l"}, {"ń", "n"}, {"ó", "o"}, {"ś", "s"}, {"ź", "z"}, {"ż", "z"},
                {"Ą", "A"}, {"Ć", "C"}, {"Ę", "E"}, {"Ł", "L"}, {"Ń", "N"}, {"Ó", "O"}, {"Ś", "S"}, {"Ź", "Z"}, {"Ż", "Z"}};
        for (String[] aChar : chars) {
            input = input.replace(aChar[0], aChar[1]);
        }
        return input;
    }

    @PostMapping("/analyzeReport")
    public String analyzeReport(@RequestBody String query) {
        query = replacePolishCharacters(query);
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(
                "Prosze przeanalizowac raporty z wizyt kobiety u ginekologa i ocenic ryzyko " +
                        "zagrozenia ciazy na podstawie podanych raportow z wizyt:"
                + query);

        log.info("Starting query: " + queryRequest.getQuery());

        return openAIAnalysisService.analyzeReport(queryRequest.getQuery());
    }

    @PostMapping("/analyzeVisit")
    public String analyzeVisit(@RequestBody String query) {
        query = replacePolishCharacters(query);
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(
                "Prosze przeanalizowac raport z podanej wizyty kobiety u ginekologa i ocenic ryzyko zagrozenia ciazy na podstawie podanych danych:"
                        + query);

        log.info("Starting query: " + queryRequest.getQuery());

        return openAIAnalysisService.analyzeReport(queryRequest.getQuery());
    }

    @PostMapping("/generateDietPlan")
    public String generateDietPlan(@RequestBody String query) {
        query = replacePolishCharacters(query);
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(
                "Wygeneruj plan dietetyczny dla kobiety w ciazy z podanymi ponizej wynikami badan." +
                        " Uwzglednij wyniki badan, wiek kobiety, zalecenia lekarza, liczbe podanych dni dla diety, " +
                        " liczbe dan w ciagu dnia, przedzial kaloryczny oraz podane alergeny. Oto lista rzeczy ktore ta kobieta wymaga od diety: "
                        + query);

        log.info("Starting query: " + queryRequest.getQuery());

        return openAIAnalysisService.analyzeReport(queryRequest.getQuery());
    }

    @PostMapping("/askQuestion")
    public String askQuestion(@RequestBody ConversationRequest conversationRequest) {
        String conversationHistory = String.join("\n", conversationRequest.getConversationHistory());
        conversationHistory = replacePolishCharacters(conversationHistory);
        log.info("Starting analyzing question from patient: " + conversationHistory);
        return openAIAnalysisService.analyzeReport(conversationHistory);
    }
}
