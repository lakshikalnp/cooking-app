package com.example.cookingapp.ai;

import com.example.cookingapp.dto.RecipeDto;
import com.example.cookingapp.exception.RecipeGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputText;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.cookingapp.util.Constant.PROMPT_STRING;
import static com.example.cookingapp.util.Constant.PROMPT_STRING_WITH_CUSTOMIZED;

@Service
public class ChatGPTClient {


    private final OpenAIClient client;
    private final ObjectMapper objectMapper;

    public ChatGPTClient(OpenAIClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public RecipeDto getRecipeForPeople(int people, String promptSuggestion, Integer noOfGramsOnePersonEats, boolean isCustomized) throws Exception {
        String  prompt = "";
        if (isCustomized)
            prompt = PROMPT_STRING_WITH_CUSTOMIZED
                .formatted(promptSuggestion, people, noOfGramsOnePersonEats);
        else prompt = PROMPT_STRING
                .formatted(promptSuggestion, people);

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model("gpt-4.1-mini")
                .input(prompt)
                .maxOutputTokens(600)
                .build();

        Response response = client.responses().create(params);


        // Extract the JSON string safely
        // Extract JSON string from response safely
        String jsonContent = response.output().stream()
                .flatMap(item -> item.message().stream())                  // Optional -> Stream
                .flatMap(msg -> msg.content().stream())                    // get content list
                .map(content -> content.outputText().orElse(null))         // unwrap Optional<OutputText>
                .filter(Objects::nonNull)
                .map(ResponseOutputText::text)
                .map(text -> text.replace("```json", "").replace("```", "").trim())// get the actual text
                .findFirst()
                .orElseThrow(() -> new RecipeGenerationException("No JSON output from OpenAI"));

        // Map JSON string to RecipeDto
        return objectMapper.readValue(jsonContent, RecipeDto.class);
    }
}
