package com.example.cookingapp.ai;

import com.example.cookingapp.dto.RecipeDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientImpl;
import com.openai.models.chat.completions.ChatCompletionAssistantMessageParam;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseOutputText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.example.cookingapp.util.Constant.PROMPT_STRING;

@Service
public class ChatGPTClient {


    private final OpenAIClient client;
    private final ObjectMapper objectMapper;

    public ChatGPTClient(OpenAIClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public RecipeDto getRecipeForPeople(int people, String promptSuggestion) throws Exception {

        String prompt = PROMPT_STRING
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
                .orElseThrow(() -> new RuntimeException("No JSON output from OpenAI"));

        // Map JSON string to RecipeDto
        return objectMapper.readValue(jsonContent, RecipeDto.class);
    }
}
