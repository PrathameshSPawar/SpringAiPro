package com.ai.SpringAiDemo.Chat;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel)
    {
        this.chatModel=chatModel;
    }

    public String getResponse(String prompt)
    {
        return chatModel.call(prompt);
    }

    public String getResponseOption(String prompt)
    {
        ChatResponse response= chatModel.call(
                new Prompt(
                        prompt,
                        OpenAiChatOptions.builder()
                                .model("gpt-4o")
                                .temperature(0.4)
                                //high temperature results in random responces and vice versa
                                //lower temperature means closer to zero, more focus responces
                                .build()
                )
        );
        return response.getResult().getOutput().toString();
    }


}
