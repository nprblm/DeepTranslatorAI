package ua.vitalii.bella.deep_translator_ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.vitalii.bella.deep_translator_ai.service.ImageSearchService;

@SpringBootApplication
public class DeepTranslatorAiApplication extends ImageSearchService {

    public static void main(String[] args) {
        SpringApplication.run(DeepTranslatorAiApplication.class, args);
    }

}