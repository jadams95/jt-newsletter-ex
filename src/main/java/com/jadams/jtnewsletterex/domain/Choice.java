package com.jadams.jtnewsletterex.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;


public class Choice  {
    private Integer index;
    private String text;

    @JsonProperty("finish_reason")
    private String finishReason;



    // constructors, getters and setters


    public Choice() {
    }

    public Choice(Integer index, String text, String finishReason) {
        this.index = index;
        this.text = text;
        this.finishReason = finishReason;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}
