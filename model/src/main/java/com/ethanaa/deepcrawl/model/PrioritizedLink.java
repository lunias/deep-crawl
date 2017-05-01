package com.ethanaa.deepcrawl.model;


import java.util.Objects;

public class PrioritizedLink implements Comparable<PrioritizedLink> {

    private String url;

    private int priority;

    public PrioritizedLink() {}

    public PrioritizedLink(String url, int priority) {

        this.url = url;
        this.priority = priority;
    }

    @Override
    public int compareTo(PrioritizedLink o) {
        return this.priority > o.getPriority() ? -1
                : this.getPriority() < o.getPriority() ? 1
                : 0;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrioritizedLink that = (PrioritizedLink) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "PrioritizedLink{" +
                "url=" + url +
                ", priority=" + priority +
                '}';
    }
}
