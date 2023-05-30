package ua.vitalii.bella.deep_translator_ai.model.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Translation {
    private List<String> translations;
    private String imageURL;
    private List<Example> examples;
}
