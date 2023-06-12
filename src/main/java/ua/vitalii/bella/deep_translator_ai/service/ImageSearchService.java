package ua.vitalii.bella.deep_translator_ai.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.vitalii.bella.deep_translator_ai.model.entity.Word;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor()
public class ImageSearchService {

    @Value("${x.rapid.api.key}")
    private String XRapidAPIKey;

    @Value("${x.rapid.api.host}")
    private String XRapidAPIHost;

    public String getURLImage(Word word) {
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
            return item.getString("url");
        } catch (Exception e) {
            return "Error";
        }
    }
}