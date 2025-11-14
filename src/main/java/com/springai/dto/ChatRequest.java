package com.springai.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRequest {

    private String query;
    private String model = "gpt-3.5-turbo";
}