package com.ethanaa.deepcrawl.model;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Set;

public class SiteData {

    private String uri;
    private String content;
    private Set<String> links;

    public SiteData() {}

    public SiteData(String uri, String content, Set<String> links) {

        this.uri = uri;
        this.content = content;
        this.links = links;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getLinks() {
        return links;
    }

    public void setLinks(Set<String> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SiteData siteData = (SiteData) o;
        return Objects.equal(uri, siteData.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uri", uri)
                .add("content", content)
                .add("links", links)
                .toString();
    }
}
