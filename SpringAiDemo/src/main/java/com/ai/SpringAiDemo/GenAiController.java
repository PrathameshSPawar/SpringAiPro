package com.ai.SpringAiDemo;

import com.ai.SpringAiDemo.Chat.ChatService;
import com.ai.SpringAiDemo.Image.ImageService;
import com.ai.SpringAiDemo.Recipe.RecipeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class GenAiController {
    private final ChatService chatService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    public GenAiController(ChatService chatService, ImageService imageService, RecipeService recipeService)
    {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("ask-ai")
    public String getResponse(@RequestParam String prompt)
    {

        return chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt)
    {
        return chatService.getResponseOption(prompt);
    }

//    @GetMapping("generate-image")
//    public void generateImage(HttpServletResponse response, @RequestParam String prompt) throws IOException
//    {
//        ImageResponse imageResponse = imageService.generateImage(prompt);
//        String imgURL = imageResponse.getResult().getOutput().getUrl();
//        response.sendRedirect(imgURL);
//    }

    @GetMapping("generate-image")
    public List<String> generateImage(HttpServletResponse response,
                                      @RequestParam String prompt,
                                      @RequestParam(defaultValue = "hd" ) String quality,
                                      @RequestParam(defaultValue = "1" ) int n,
                                      @RequestParam(defaultValue = "1024" ) int width,
                                      @RequestParam(defaultValue = "1024" ) int height ) throws IOException
    {
        ImageResponse imageResponse = imageService.generateImage(prompt,quality,n,width,height);

        List<String> imageURLs = imageResponse.getResults().stream()
                .map(result -> result.getOutput().getUrl())
                .toList();
                //.collect(Collectors.toList());
        return imageURLs;
    }

    @GetMapping("recipe-creator")
    public AssistantMessage recipeCreator(@RequestParam String ingrediants,
                                          @RequestParam(defaultValue = "any") String cuisine,
                                          @RequestParam(defaultValue = "") String dietorayRestriction)
    {
        return recipeService.createRecipe(ingrediants,cuisine,dietorayRestriction);
    }


}
