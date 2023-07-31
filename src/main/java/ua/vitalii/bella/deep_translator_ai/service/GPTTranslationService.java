package ua.vitalii.bella.deep_translator_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.vitalii.bella.deep_translator_ai.model.entity.Prompt;
import ua.vitalii.bella.deep_translator_ai.model.entity.Translation;
import ua.vitalii.bella.deep_translator_ai.model.gpt.response.ChatGPTResponse;
import ua.vitalii.bella.deep_translator_ai.utils.JsonParser;

@Component
@RequiredArgsConstructor
public class GPTTranslationService {

    private final ChatGPTService chatGPTService;

    private final ImageSearchService imageSearchService;

    public Translation getTranslation(String word) {
        Translation translation = new Translation();

        String prompt = Prompt.getPrompt(word);

        ChatGPTResponse chatGPTResponse = chatGPTService.getChatGPTResponse(prompt);

        String jsonString = chatGPTResponse.getChoices().get(0).getMessage().getContent();
        try {
            jsonString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
            translation = JsonParser.fromJSONToTranslation(jsonString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        translation.setImageURL(imageSearchService.getURLImage(word));
        return translation;
    }
}