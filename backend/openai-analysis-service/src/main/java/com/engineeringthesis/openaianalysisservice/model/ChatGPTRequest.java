package com.engineeringthesis.openaianalysisservice.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ChatGPTRequest {
    private String model = "gpt-3.5-turbo-instruct";
    private String prompt;
    private int temperature = 1;

    @SerializedName(value = "max_tokens")
    private int maxTokens = 2000;
}
