package com.ethanaa.deepcrawl.model;


public class ScrapeRequest {

    private String uri;

    public ScrapeRequest() {

    }

    public ScrapeRequest(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "ScrapeRequest{" +
                "uri='" + uri + '\'' +
                '}';
    }
}
