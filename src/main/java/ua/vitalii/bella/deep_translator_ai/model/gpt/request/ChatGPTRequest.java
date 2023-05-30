package ua.vitalii.bella.deep_translator_ai.model.gpt.request;


import lombok.Getter;
import lombok.Setter;
import ua.vitalii.bella.deep_translator_ai.model.gpt.common.Message;

import java.util.List;


@Getter
@Setter
public class ChatGPTRequest {

    private String model;

    private List<Message> messages;

    private Integer max_tokens;

}
