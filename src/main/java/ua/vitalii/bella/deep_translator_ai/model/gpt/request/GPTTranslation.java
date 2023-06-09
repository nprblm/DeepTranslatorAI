package ua.vitalii.bella.deep_translator_ai.model.gpt.request;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.vitalii.bella.deep_translator_ai.model.entity.Example;
import ua.vitalii.bella.deep_translator_ai.model.entity.Prompt;
import ua.vitalii.bella.deep_translator_ai.model.entity.Translation;
import ua.vitalii.bella.deep_translator_ai.model.entity.Word;
import ua.vitalii.bella.deep_translator_ai.model.gpt.response.ChatGPTResponse;
import ua.vitalii.bella.deep_translator_ai.service.ChatGPTService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GPTTranslation {

    @Autowired
    private ChatGPTService chatGPTService;

    @Value("${x.rapid.api.key}")
    private String XRapidAPIKey;

    @Value("${x.rapid.api.host}")
    private String XRapidAPIHost;

    public Translation getTranslation(Word word) {
        Translation translation = new Translation();

        String prompt = Prompt.getPrompt(word);

        ChatGPTResponse chatGPTResponse = chatGPTService.getChatGPTResponse(prompt);

        String jsonString = chatGPTResponse.getChoices().get(0).getMessage().getContent();
        try {
            jsonString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
            translation = fromJSONToTranslation(jsonString);
        } catch (Exception e) {

        }
        translation.setImageURL(getURLImage(word));
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
                .map(x->x.substring(0, 1).toUpperCase() + x.substring(1))
                .collect(Collectors.toList()));
        result.setExamples(examplesList);
        return result;
    }

    private String getURLImage(Word word) {
        String prompt = String.format("https://joj-image-search.p.rapidapi.com/v2/?q=%s&hl=en", word.getWord().replaceAll(" ", "_"));
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(prompt))
                    .header("X-RapidAPI-Key", XRapidAPIKey)
                    .header("X-RapidAPI-Host", XRapidAPIHost)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            String responseBody = response.body();
            final JSONObject obj = new JSONObject(responseBody).getJSONObject("response");
            final JSONArray images = obj.getJSONArray("images");
            final JSONObject item = images.getJSONObject(0).getJSONObject("image");
            final String stringUrl = item.getString("url");
            return stringUrl;
        } catch (Exception e) {
            return "Error";
        }
    }

}
