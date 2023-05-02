package com.jadams.jtnewsletterex.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatRequest {
    private String model;
    private String prompt;
    private Float temperature;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    @JsonProperty("top_p")
    private Float topP;
    @JsonProperty("frequency_penalty")
    private Float frequencyPenalty;
    @JsonProperty("presence_penalty")
    private Float presence_penalty;
    @JsonProperty("best_of")
    private Integer bestOf;

    public ChatRequest() {
    }

    public ChatRequest(String model, String prompt, Float temperature, Integer maxTokens, Float topP, Float frequencyPenalty, Float presence_penalty, Integer bestOf) {
        this.model = model;
        this.prompt = prompt;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
        this.topP = topP;
        this.frequencyPenalty = frequencyPenalty;
        this.presence_penalty = presence_penalty;
        this.bestOf = bestOf;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Float getTopP() {
        return topP;
    }

    public void setTopP(Float topP) {
        this.topP = topP;
    }

    public Float getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(Float frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public Float getPresence_penalty() {
        return presence_penalty;
    }

    public void setPresence_penalty(Float presence_penalty) {
        this.presence_penalty = presence_penalty;
    }

    public Integer getBestOf() {
        return bestOf;
    }

    public void setBestOf(Integer bestOf) {
        this.bestOf = bestOf;
    }
}
