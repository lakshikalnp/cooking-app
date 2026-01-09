package com.example.cookingapp.util;

public class Constant {

    private Constant() {
        // prevent instantiation
    }
    public static final String PROMPT_STRING = """
            Generate a recipe for %s for %d people.
            Output ONLY valid JSON in this format:
            {
              "recipeName": "",
              "serves": number,
              "noOfGramsOnePersonEats":number,
              "ingredients": [
                { "name": "", "amountGrams": number }
              ],
              "instructions": [""]
            }
            
             Rules:
             1. Scale the ingredients so that the main ingredient portion matches the per-person grams × number of people.
             2. Include all major ingredients with amounts in grams.
             3. Include a clear step-by-step instruction list.
             4. Output **only JSON**, no extra text.
            """;

    public static final String PROMPT_STRING_WITH_CUSTOMIZED = """
            Generate a recipe for "%s" for %d people, assuming **each person eats %d grams** of the main ingredient portion.\s
            
             Output **ONLY valid JSON** in this exact format:
            
             {
               "recipeName": "",
               "serves": number,
               "noOfGramsOnePersonEats":number,
               "ingredients": [
                 { "name": "", "amountGrams": number }
               ],
               "instructions": [""]
             }
            
             Rules:
             1. Scale the ingredients so that the main ingredient portion matches the per-person grams × number of people.
             2. Include all major ingredients with amounts in grams.
             3. Include a clear step-by-step instruction list.
             4. Output **only JSON**, no extra text.
            
            """;
}
