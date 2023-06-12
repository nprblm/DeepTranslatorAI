package ua.vitalii.bella.deep_translator_ai.model.gpt.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.vitalii.bella.deep_translator_ai.model.gpt.common.Message;

@Getter
@Setter
@NoArgsConstructor
public class Choice {

    private int index;

    private Message message;

    private String finish_reason;

}