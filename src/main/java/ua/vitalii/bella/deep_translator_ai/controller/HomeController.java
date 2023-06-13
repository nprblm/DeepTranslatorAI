package ua.vitalii.bella.deep_translator_ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.vitalii.bella.deep_translator_ai.model.entity.Translation;
import ua.vitalii.bella.deep_translator_ai.model.entity.Word;
import ua.vitalii.bella.deep_translator_ai.model.gpt.request.GPTTranslation;

@Controller
public class HomeController {

    @Autowired
    GPTTranslation gptTranslation;

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @PostMapping("/translation")
    public String postTranslation(Word word, Model model) {
        Translation translation = gptTranslation.getTranslation(word);
        if (translation.getTranslations() == null || translation.getExamples() == null)
            model.addAttribute("error", "Something wrong, please try again later");
        else
            model.addAttribute("translation", translation);
        return "translate";
    }
}
