package com.ethanaa.deepcrawl.model;


public class PageRelevanceResult {

    private int relevanceScore;

    public PageRelevanceResult() {}

    public PageRelevanceResult(int relevanceScore) {

        this.relevanceScore = relevanceScore;
    }

    public int getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(int relevanceScore) {
        this.relevanceScore = relevanceScore;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageRelevanceResult{");
        sb.append("relevanceScore=").append(relevanceScore);
        sb.append('}');
        return sb.toString();
    }
}
