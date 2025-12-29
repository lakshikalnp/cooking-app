package com.example.cookingapp.util;

public class Constant {
    public static final String PROMPT_STRING = """
            %s for %d people.
            Output ONLY valid JSON in this format:
            {
              "recipeName": "",
              "serves": number,
              "ingredients": [
                { "name": "", "amountGrams": number }
              ],
              "instructions": [""]
            }
            """;
}
