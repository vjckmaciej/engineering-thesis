package com.engineeringthesis.openaianalysisservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationRequest {
    private List<String> conversationHistory;
}
