package com.ai.SpringAiDemo.Recipe;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class RecipeService {
    private final ChatModel chatModel;

    public RecipeService(ChatModel chatModel)
    {
        this.chatModel=chatModel;
    }

    public AssistantMessage createRecipe(String ingredients,
                                         String cuisine,
                                         String dietaryRestrictions)
    {
        var template = """
                I want to create a recipe using the following requirements: {ingredients}.
                The cuisine type I prefer is: {cuisine}.
                Please consider the following dietory restriction: {dietaryRestrictions}.
                Please provide me with a detailed recipe including title, list of ingredients, and cooking instructions
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String, Object> params =  Map.of(
                "ingredients",ingredients,
                "cuisine",cuisine,
                "dietaryRestrictions",dietaryRestrictions);
        Prompt prompt = promptTemplate.create(params);
        return chatModel.call(prompt).getResult().getOutput();
    }
}
