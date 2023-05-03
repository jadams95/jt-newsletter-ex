package com.jadams.jtnewsletterex.domain;

import java.util.List;

public class RefinedMessage {
    private String marketingIntro;

    private List<Product> productList;

    private String targetSubscriber;
    private String productDescription;

    private List<Product> subscriberProductTriedList;

    public RefinedMessage(String marketingIntro, String targetSubscriber, String productDescription) {
        this.marketingIntro = marketingIntro;
        this.targetSubscriber = targetSubscriber;
        this.productDescription = productDescription;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getMarketingIntro() {
        return marketingIntro;
    }

    public void setMarketingIntro(String marketingIntro) {
        this.marketingIntro = marketingIntro;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public List<Product> getSubscriberProductTriedList() {
        return subscriberProductTriedList;
    }

    public void setSubscriberProductTriedList(List<Product> subscriberProductTriedList) {
        this.subscriberProductTriedList = subscriberProductTriedList;
    }

    public String getTargetSubscriber() {
        return targetSubscriber;
    }

    public void setTargetSubscriber(String targetSubscriber) {
        this.targetSubscriber = targetSubscriber;
    }
}
