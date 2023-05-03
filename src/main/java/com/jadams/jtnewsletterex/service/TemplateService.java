package com.jadams.jtnewsletterex.service;

import com.jadams.jtnewsletterex.dao.TemplateDao;
import com.jadams.jtnewsletterex.domain.RefinedMessage;
import com.jadams.jtnewsletterex.domain.Template;
import com.jadams.jtnewsletterex.domain.model.ChatRequest;
import com.jadams.jtnewsletterex.domain.model.ChatResponse;
import com.jadams.jtnewsletterex.domain.Message;
//import com.jadams.jtnewsletterex.domain.Prompt;
//import com.jadams.jtnewsletterex.domain.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

//
@Service
public class TemplateService {
    private Logger logger = LoggerFactory.getLogger(TemplateService.class);

    private final String TODO_API_URL = "https://api.openai.com/v1/completions";

    private static RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.accesskey}")
    private String accessKey;

    @Autowired
    private TemplateDao templateDao;


    public HttpEntity<ChatRequest> buildHttpEntity(ChatRequest chatRequest){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE));
        headers.add("Authorization", "Bearer " + accessKey);
        return new HttpEntity<>(chatRequest, headers);
    }

    public ChatResponse getResponse(HttpEntity<ChatRequest> chatRequestHttpEntity){
        ResponseEntity<ChatResponse> responseEntity = restTemplate.postForEntity(TODO_API_URL, chatRequestHttpEntity, ChatResponse.class);
        return responseEntity.getBody();
    }

    @Transactional
    public ChatResponse createAnContentPrompt(Message message)  {

        ChatResponse chatResponse = this.getResponse(this.buildHttpEntity(new ChatRequest("text-davinci-003",
                message.getMessage(),
                0.5f,
                250,
                1.0f,
                0.0f,
                0.0f,
                5)));

            Template template = new Template();
            template.setPrompt(message.getMessage());
            template.setText(chatResponse.getChoices().get(0).getText());
            templateDao.save(template);
        return chatResponse;
    }

    @Transactional
    public ChatResponse createMoreSpecificPrompt(RefinedMessage refinedMessage)  {
        Message message = new Message(refinedMessage.getMarketingIntro() + refinedMessage.getTargetSubscriber() + refinedMessage.getTargetSubscriber());
        ChatResponse chatResponse = this.getResponse(this.buildHttpEntity(new ChatRequest("text-davinci-003",
                message.getMessage(),
                0.5f,
                250,
                1.0f,
                0.0f,
                0.0f,
                5)));

        Template template = new Template();
        template.setPrompt(message.getMessage());
        template.setText(chatResponse.getChoices().get(0).getText());
        templateDao.save(template);
        return chatResponse;
    }


    public List<Template> getTemplates(){
        return templateDao.findAll();
    }
}