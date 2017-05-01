package com.ethanaa.deepcrawl.model;


public class ContentSearchResult {

    private String pattern;
    private long numMatches;

    public ContentSearchResult() {}

    public ContentSearchResult(String pattern, long numMatches) {

        this.pattern = pattern;
        this.numMatches = numMatches;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public long getNumMatches() {
        return numMatches;
    }

    public void setNumMatches(long numMatches) {
        this.numMatches = numMatches;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContentSearchResult{");
        sb.append("pattern='").append(pattern).append('\'');
        sb.append(", numMatches=").append(numMatches);
        sb.append('}');
        return sb.toString();
    }
}
