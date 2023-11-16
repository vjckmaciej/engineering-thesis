package com.engineeringthesis.openaianalysisservice.model;

import lombok.Data;

import java.util.List;

@Data
public class ChatGPTResponse {
    private List<ChatGptChoice> choices;
}
