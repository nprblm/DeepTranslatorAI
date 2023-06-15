package ua.vitalii.bella.deep_translator_ai.model.gpt.request;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.vitalii.bella.deep_translator_ai.model.entity.Example;
import ua.vitalii.bella.deep_translator_ai.model.entity.Prompt;
import ua.vitalii.bella.deep_translator_ai.model.entity.Translation;
import ua.vitalii.bella.deep_translator_ai.model.gpt.response.ChatGPTResponse;
import ua.vitalii.bella.deep_translator_ai.service.ChatGPTService;
import ua.vitalii.bella.deep_translator_ai.service.ImageSearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GPTTranslation {

    @Autowired
    private ChatGPTService chatGPTService;

    @Autowired
    private ImageSearchService imageSearchService;

    public Translation getTranslation(String word) {
        Translation translation = new Translation();

        String prompt = Prompt.getPrompt(word);

        ChatGPTResponse chatGPTResponse = chatGPTService.getChatGPTResponse(prompt);

        String jsonString = chatGPTResponse.getChoices().get(0).getMessage().getContent();
        try {
            jsonString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
            translation = fromJSONToTranslation(jsonString);
        } catch (Exception e) {

        }
        translation.setImageURL(imageSearchService.getURLImage(word));
        return translation;
    }

    private Translation fromJSONToTranslation(String jsonString) {
        Translation result = new Translation();

        final JSONObject obj = new JSONObject(jsonString);
        final JSONArray translatedWords = obj.getJSONObject("translation").getJSONArray("translatedWords");
        final JSONArray examples = obj.getJSONObject("translation").getJSONArray("examples");

        List<String> translatedWordsList = new ArrayList<>();
        List<Example> examplesList = new ArrayList<>();

        for (int i = 0; i < translatedWords.length(); ++i) {
            JSONObject item = translatedWords.getJSONObject(i);
            translatedWordsList.add(item.getString("translation"));
        }

        for (int i = 0; i < examples.length(); ++i) {
            JSONObject item = examples.getJSONObject(i);
            examplesList.add(new Example(item.getString("exampleEnglish"), item.getString("exampleUkrainian")));
        }

        result.setTranslations(translatedWordsList.stream()
                .map(String::trim)
                .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1))
                .collect(Collectors.toList()));
        result.setExamples(examplesList);

        return result;
    }

}