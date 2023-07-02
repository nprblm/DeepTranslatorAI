package ua.vitalii.bella.deep_translator_ai.model.gpt.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.vitalii.bella.deep_translator_ai.model.entity.Prompt;
import ua.vitalii.bella.deep_translator_ai.model.entity.Translation;
import ua.vitalii.bella.deep_translator_ai.model.gpt.response.ChatGPTResponse;
import ua.vitalii.bella.deep_translator_ai.service.ChatGPTService;
import ua.vitalii.bella.deep_translator_ai.service.ImageSearchService;

@Component
public class GPTTranslation {

    private final ChatGPTService chatGPTService;

    private final ImageSearchService imageSearchService;

    @Autowired
    public GPTTranslation(ChatGPTService chatGPTService, ImageSearchService imageSearchService) {
        this.chatGPTService = chatGPTService;
        this.imageSearchService = imageSearchService;
    }

    public Translation getTranslation(String word) {
        Translation translation = new Translation();

        String prompt = Prompt.getPrompt(word);

        ChatGPTResponse chatGPTResponse = chatGPTService.getChatGPTResponse(prompt);

        String jsonString = chatGPTResponse.getChoices().get(0).getMessage().getContent();
        try {
            jsonString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
            translation = JsonParser.fromJSONToTranslation(jsonString);
        } catch (Exception e) {

        }
        translation.setImageURL(imageSearchService.getURLImage(word));
        return translation;
    }
}