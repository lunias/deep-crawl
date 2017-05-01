package com.ethanaa.deepcrawl.writer.entity.id;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.ethanaa.deepcrawl.writer.entity.BackLink;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class BackLinkId {

    @DynamoDBHashKey(attributeName = BackLink.HASH_NAME)
    private String fromUriAuthority;

    @DynamoDBRangeKey(attributeName = BackLink.RANGE_NAME)
    private String toUriAuthority;

    public BackLinkId(String fromUriAuthority, String toUriAuthority) {

        this.fromUriAuthority = fromUriAuthority;
        this.toUriAuthority = toUriAuthority;
    }

    public BackLinkId() {
        //
    }

    public String getFromUriAuthority() {
        return fromUriAuthority;
    }

    public void setFromUriAuthority(String fromUriAuthority) {
        this.fromUriAuthority = fromUriAuthority;
    }

    public String getToUriAuthority() {
        return toUriAuthority;
    }

    public void setToUriAuthority(String toUriAuthority) {
        this.toUriAuthority = toUriAuthority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackLinkId that = (BackLinkId) o;
        return Objects.equal(fromUriAuthority, that.fromUriAuthority) &&
                Objects.equal(toUriAuthority, that.toUriAuthority);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fromUriAuthority, toUriAuthority);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fromUriAuthority", fromUriAuthority)
                .add("toUriAuthority", toUriAuthority)
                .toString();
    }
}
