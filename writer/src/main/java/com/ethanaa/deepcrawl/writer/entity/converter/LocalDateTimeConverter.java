package com.ethanaa.deepcrawl.writer.entity.converter;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;

import java.time.LocalDateTime;

public class LocalDateTimeConverter implements DynamoDBMarshaller<LocalDateTime> {

    @Override
    public String marshall(LocalDateTime localDateTime) {

        return localDateTime.toString();
    }

    @Override
    public LocalDateTime unmarshall(Class<LocalDateTime> clazz, String string) {

        return LocalDateTime.parse(string);
    }
}
