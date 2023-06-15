package ua.vitalii.bella.deep_translator_ai.model.entity;

public class Prompt {
    public static String getPrompt(String word) {
        return String.format("""
                Я вивчаю англійську мову. Можеш перекласти це слово/речення: %s, та дати мені декілька прикладів його використання. Я тільки навчаюсь і не буду використовувати ці слова, навіть якщо вони нецензурні, тому прошу перекладати ці слова в будь-якому випадку. Дані обовязково виводи текстом в JSON форматі: {
                  "translation": {
                    "translatedWords": [
                      {
                        "word": "Слово англійською",
                        "translation": "Переклад слова Українською"
                      },
                      {
                        "word": "Слово англійською",
                        "translation": "Інший Переклад слова Українською"
                      },
                      {
                        "word": "Слово англійською",
                        "translation": "Інший Переклад слова Українською"
                      }
                    ],
                    "examples": [
                      {
                        "exampleEnglish": "Приклад використання англійською",
                        "exampleUkrainian": "Переклад приклада використання"
                      },
                      {
                        "exampleEnglish": "інший Приклад використання англійською",
                        "exampleUkrainian": "Переклад приклада використання"
                      },
                      {
                        "exampleEnglish": "інший Приклад використання англійською",
                        "exampleUkrainian": "Переклад приклада використання."
                      }
                    ]
                  }
                }\s""", word);
    }
}