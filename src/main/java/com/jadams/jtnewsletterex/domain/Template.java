package com.jadams.jtnewsletterex.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 3000)
    private String text;
    @Column(length = 3000)
    private String prompt;

    public Template() {
    }

//    public Template(Long id) {
//        this.id = id;
//    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
