package com.ethanaa.deepcrawl.writer.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.ethanaa.deepcrawl.model.Context;
import com.ethanaa.deepcrawl.writer.entity.converter.LocalDateTimeConverter;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ethanaa.deepcrawl.writer.entity.SearchResult.TABLE_NAME;

@DynamoDBTable(tableName = TABLE_NAME)
public class SearchResult {

    public static final String TABLE_NAME = "SearchResult";

    @DynamoDBHashKey(attributeName = "SearchTermUuid")
    private String searchTermUuid;

    @DynamoDBAttribute(attributeName = "ApiKey")
    private String apiKey = "";

    @DynamoDBAttribute(attributeName = "Uri")
    private String uri = "";

    @DynamoDBAttribute(attributeName = "UriAuthority")
    private String uriAuthority = "";

    @DynamoDBIndexRangeKey(attributeName = "NumHits")
    private Long numHits = 0L;

    @DynamoDBAttribute(attributeName = "ContextList")
    private List<Context> contextList = new ArrayList<>();

    @DynamoDBIndexRangeKey(attributeName = "CreatedAt")
    @DynamoDBMarshalling(marshallerClass = LocalDateTimeConverter.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @DynamoDBIndexRangeKey(attributeName = "UpdatedAt")
    @DynamoDBMarshalling(marshallerClass = LocalDateTimeConverter.class)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public SearchResult(SearchTerm searchTerm, URI uri, long numHits, List<Context> contextList) {

        this.searchTermUuid = searchTerm.getUuid();
        this.apiKey = searchTerm.getApiKey();
        this.uri = uri.toString();
        this.numHits = numHits;
        this.contextList = contextList;
    }

    public SearchResult() {
        //
    }

    public String getSearchTermUuid() {
        return searchTermUuid;
    }

    public void setSearchTermUuid(String searchTermUuid) {
        this.searchTermUuid = searchTermUuid;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUriAuthority() {
        return uriAuthority;
    }

    public void setUriAuthority(String uriAuthority) {
        this.uriAuthority = uriAuthority;
    }

    public Long getNumHits() {
        return numHits;
    }

    public void setNumHits(Long numHits) {
        this.numHits = numHits;
    }

    public List<Context> getContextList() {
        return contextList;
    }

    public void setContextList(List<Context> contextList) {
        this.contextList = contextList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return Objects.equal(searchTermUuid, that.searchTermUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(searchTermUuid);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("searchTermUuid", searchTermUuid)
                .add("apiKey", apiKey)
                .add("uri", uri)
                .add("uriAuthority", uriAuthority)
                .add("numHits", numHits)
                .add("contextList", contextList)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .toString();
    }
}
