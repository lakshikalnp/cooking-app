package com.example.cookingapp.config;


import com.openai.client.OpenAIClient;
import com.openai.client.OpenAIClientImpl;
import com.openai.client.okhttp.OkHttpClient;
import com.openai.core.ClientOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    @Value("${open.ai.api.key}")
    private String openAiApiKey;

    @Bean
    public OpenAIClient openAIClient() {
        // Create OpenAI OkHttpClient using the static builder
        OkHttpClient httpClient = OkHttpClient.builder().build();

        ClientOptions options = ClientOptions.builder()
                .apiKey(openAiApiKey)
                .httpClient(httpClient)   // required
                .build();

        return new OpenAIClientImpl(options);
    }
}
