package ua.vitalii.bella.deep_translator_ai.model.gpt.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Usage {

    private int prompt_tokens;

    private int completion_tokens;

    private int total_tokens;

}