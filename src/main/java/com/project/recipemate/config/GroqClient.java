package com.project.recipemate.config;

import com.project.recipemate.dtos.SubstitutionResponseDTO;
import com.project.recipemate.dtos.groq.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
public class GroqClient {
    private final RestClient restClient;
    private   SubstitutionResponseDTO substitutionResponseDTO;

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.api.base-url}")
    private String baseUrl;

    @Value("${groq.api.model}")
    private String model;

    public GroqClient(RestClient restClient  ) {
        this.restClient = restClient;

    }

    public AiRecipeResponseDTO generateRecipe(String dishName) {
        String systemPrompt= """
                            You are a recipe data generator. Given a dish name, respond ONLY with valid JSON
                                (no markdown, no explanation, no code fences) matching exactly this shape:
                                {
                                  "name": string,
                                  "servings": number,
                                  "instructions": string,
                                  "ingredients": [ { "name": string, "quantity": number, "unit": string } ],
                                  "nutrition": { "calories": number, "protein": number, "fat": number, "carbs": number, "sugar": number }
                                }
                                Use realistic quantities and approximate nutrition per full recipe (not per serving).
                """;

        GroqRequestDTO request = new GroqRequestDTO();
        request.setModel(model);
        request.setMessages(List.of(
            new GroqMessageDTO("system",systemPrompt),
                new GroqMessageDTO("user","Dish:"+dishName)
        ));

        GroqResponseDTO response=restClient.post()
                .uri(baseUrl)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(GroqResponseDTO.class);

        String jsonContent=response.getChoices().get(0).getMessage().getContent();

        jsonContent=jsonContent.replaceAll("```json", "").replaceAll("```", "").trim();

        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.readValue(jsonContent, AiRecipeResponseDTO.class);
        }catch(Exception e){
            throw  new RuntimeException("Failed to parse AI recipe response",e);
        }
    }

    // In GroqClient.java, add:
    public SubstitutionResponseDTO generateSubstitution(String ingredientName) {

        String systemPrompt = """
            You suggest a single best cooking substitute for an ingredient.
            Respond ONLY with valid JSON (no markdown), exactly this shape:
            { "substitute": string, "notes": string }
            Keep "notes" under 15 words, mention ratio or best use-case.
            """;

        GroqRequestDTO request = new GroqRequestDTO();
        request.setModel(model);
        request.setMessages(List.of(
                new GroqMessageDTO("system", systemPrompt),
                new GroqMessageDTO("user", "Ingredient: " + ingredientName)
        ));

        GroqResponseDTO response = restClient.post()
                .uri(baseUrl)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(GroqResponseDTO.class);

        String jsonContent = response.getChoices().get(0).getMessage().getContent()
                .replaceAll("```json", "").replaceAll("```", "").trim();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(jsonContent);
            SubstitutionResponseDTO dto = new SubstitutionResponseDTO();
            dto.setIngredient(ingredientName);
            dto.setSubstitute(node.get("substitute").asText());
            dto.setNotes(node.get("notes").asText());
            dto.setSource("AI");
            return dto;
        } catch (Exception   e) {
            throw new RuntimeException("Failed to parse AI substitution response", e);
        }
    }

}
