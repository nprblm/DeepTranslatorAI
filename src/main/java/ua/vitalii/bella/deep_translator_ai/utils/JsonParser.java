package ua.vitalii.bella.deep_translator_ai.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import ua.vitalii.bella.deep_translator_ai.model.entity.Example;
import ua.vitalii.bella.deep_translator_ai.model.entity.Translation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonParser {

    //Парсер json-gpt-response
    public static Translation fromJSONToTranslation(String jsonString) {
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

        //Uppercase перших букв речення
        result.setTranslations(translatedWordsList.stream()
                .map(String::trim)
                .map(x -> x.substring(0, 1).toUpperCase() + x.substring(1))
                .collect(Collectors.toList()));
        result.setExamples(examplesList);

        return result;
    }
}
