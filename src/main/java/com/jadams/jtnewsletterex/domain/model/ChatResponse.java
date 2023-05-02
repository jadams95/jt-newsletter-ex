package com.jadams.jtnewsletterex.domain.model;

import com.jadams.jtnewsletterex.domain.Choice;

import java.time.LocalDate;
import java.util.List;

public class ChatResponse {

    private String id;
    private String object;
    private LocalDate created;
    private String model;
    private List<Choice> choices;

    public ChatResponse() {
    }

    public ChatResponse(String id, String object, LocalDate created, String model, List<Choice> choices) {
        this.id = id;
        this.object = object;
        this.created = created;
        this.model = model;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
