package com.ethanaa.deepcrawl.writer.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.ethanaa.deepcrawl.writer.entity.converter.LocalDateTimeConverter;
import com.ethanaa.deepcrawl.writer.entity.id.BackLinkId;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

import static com.ethanaa.deepcrawl.writer.entity.BackLink.TABLE_NAME;

@DynamoDBTable(tableName = TABLE_NAME)
public class BackLink {

    public static final String HASH_NAME = "FromUriAuthority";
    public static final String RANGE_NAME = "ToUriAuthority";
    public static final String TABLE_NAME = "BackLink";

    @Id
    private BackLinkId backLinkId;

    @DynamoDBIndexRangeKey(attributeName = "Weight", localSecondaryIndexName = "WeightIndex")
    private Integer weight = 0;

    @DynamoDBIndexRangeKey(attributeName = "CreatedAt", localSecondaryIndexName = "CreatedAtIndex")
    @DynamoDBMarshalling(marshallerClass = LocalDateTimeConverter.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @DynamoDBIndexRangeKey(attributeName = "UpdatedAt", localSecondaryIndexName = "UpdatedAtIndex")
    @DynamoDBMarshalling(marshallerClass = LocalDateTimeConverter.class)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public BackLink(String fromUriAuthority, String toUriAuthority, int weight) {

        this.backLinkId = new BackLinkId(fromUriAuthority, toUriAuthority);
        this.weight = weight;
    }

    public BackLink() {
        //
    }

    @DynamoDBHashKey(attributeName = HASH_NAME)
    public String getFromUriAuthority() {

        return backLinkId != null ? backLinkId.getFromUriAuthority() : null;
    }

    public void setFromUriAuthority(String fromUriAuthority) {

        if (backLinkId == null) {
            backLinkId = new BackLinkId();
        }

        this.backLinkId.setFromUriAuthority(fromUriAuthority);
    }

    @DynamoDBRangeKey(attributeName = RANGE_NAME)
    public String getToUriAuthority() {

        return backLinkId != null ? backLinkId.getToUriAuthority() : null;
    }

    public void setToUriAuthority(String toUriAuthority) {

        if (backLinkId == null) {
            backLinkId = new BackLinkId();
        }

        this.backLinkId.setToUriAuthority(toUriAuthority);
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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
        BackLink backLink = (BackLink) o;
        return Objects.equal(backLinkId, backLink.backLinkId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(backLinkId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("backLinkId", backLinkId)
                .add("weight", weight)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .toString();
    }
}
