package ua.vitalii.bella.deep_translator_ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.vitalii.bella.deep_translator_ai.model.entity.Translation;
import ua.vitalii.bella.deep_translator_ai.model.entity.TranslationRequest;
import ua.vitalii.bella.deep_translator_ai.model.gpt.request.GPTTranslation;

@Controller
public class HomeController {

    @Autowired
    GPTTranslation gptTranslation;

    private final String ERROR_MESSAGE = "Something wrong, please try again later";

    @GetMapping("/")
    public ModelAndView index(@ModelAttribute TranslationRequest translationRequest) {
        return new ModelAndView("index");
    }

    @PostMapping("/translation")
    public String postTranslation(@ModelAttribute TranslationRequest translationRequest, Model model) {
        Translation translation = gptTranslation.getTranslation(translationRequest.getWord());
        if (translation.getTranslations() == null || translation.getExamples() == null)
            model.addAttribute("error", ERROR_MESSAGE);
        else
            model.addAttribute("translation", translation);
        return "translate";
    }
}
