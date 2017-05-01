package com.ethanaa.deepcrawl.writer.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.ethanaa.deepcrawl.writer.entity.converter.LocalDateTimeConverter;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.time.LocalDateTime;

import static com.ethanaa.deepcrawl.writer.entity.PageData.TABLE_NAME;

@DynamoDBTable(tableName = TABLE_NAME)
public class PageData {

    public static final String TABLE_NAME = "PageData";

    @DynamoDBHashKey(attributeName = "UriAuthority")
    private String uriAuthority;

    @DynamoDBIndexRangeKey(attributeName = "NumHits")
    private Long numHits = 0L;

    @DynamoDBIndexRangeKey(attributeName = "CreatedAt")
    @DynamoDBMarshalling(marshallerClass = LocalDateTimeConverter.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @DynamoDBIndexRangeKey(attributeName = "UpdatedAt")
    @DynamoDBMarshalling(marshallerClass = LocalDateTimeConverter.class)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public PageData(String uriAuthority, Long numHits) {

        this.uriAuthority = uriAuthority;
        this.numHits = numHits;
    }

    public PageData() {
        //
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
        PageData pageData = (PageData) o;
        return Objects.equal(uriAuthority, pageData.uriAuthority);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uriAuthority);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("uriAuthority", uriAuthority)
                .add("numHits", numHits)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .toString();
    }
}
