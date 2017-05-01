package com.ethanaa.deepcrawl.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.ethanaa.deepcrawl.writer.entity.BackLink;
import com.ethanaa.deepcrawl.writer.repository.BackLinkRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BackLinkRepositoryTests {

    private static final Long READ_CAPACITY_UNITS = 5L;
    private static final Long WRITE_CAPACITY_UNITS = 5L;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private BackLinkRepository repository;

    @Before
    public void init() throws Exception {

        ListTablesResult listTablesResult = amazonDynamoDB.listTables();

        listTablesResult.getTableNames().stream()
                .filter(tableName -> tableName.equals(BackLink.TABLE_NAME))
                .forEach(tableName -> {
                    amazonDynamoDB.deleteTable(tableName);
                });

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.addAll(Arrays.asList(
                new AttributeDefinition().withAttributeName(BackLink.HASH_NAME).withAttributeType("S"),
                new AttributeDefinition().withAttributeName(BackLink.RANGE_NAME).withAttributeType("S")));

        List<KeySchemaElement> keySchemaElements = new ArrayList<>();
        keySchemaElements.addAll(Arrays.asList(
                new KeySchemaElement().withAttributeName(BackLink.HASH_NAME).withKeyType(KeyType.HASH),
                new KeySchemaElement().withAttributeName(BackLink.RANGE_NAME).withKeyType(KeyType.RANGE)));

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(BackLink.TABLE_NAME)
                .withKeySchema(keySchemaElements)
                .withAttributeDefinitions(attributeDefinitions)
                .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(READ_CAPACITY_UNITS)
                        .withWriteCapacityUnits(WRITE_CAPACITY_UNITS));

        CreateTableResult createTableResult = amazonDynamoDB.createTable(request);

        assertThat(createTableResult.getTableDescription().getTableName()).isEqualTo(BackLink.TABLE_NAME);
    }

    @Test
    public void saveTest() {

        BackLink backLink = new BackLink("http://www.google.com", "http://www.ethanaa.com", 0);

        backLink.setWeight(1);

        backLink = repository.save(backLink);

        assertThat(backLink).isNotNull();
        assertThat(backLink.getCreatedAt()).isNotNull();
        assertThat(backLink.getUpdatedAt()).isNotNull();
        assertThat(backLink.getWeight()).isEqualTo(1);

        BackLink backLink2 = new BackLink("http://www.google2.com", "http://www.ethanaa2.com", 1);

        Iterable<BackLink> backLinks = repository.save(Arrays.asList(backLink, backLink2));

        assertThat(backLinks).hasSize(2);

        backLinks.forEach(bl -> {
            assertThat(bl).isNotNull();
            assertThat(bl.getCreatedAt()).isNotNull();
            assertThat(bl.getUpdatedAt()).isNotNull();
            assertThat(bl.getWeight()).isEqualTo(1);
        });
    }

    @Test
    public void findTest() {

        BackLink backLink = new BackLink("http://www.google.com", "http://www.ethanaa.com", 1);
        BackLink backLink2 = new BackLink("http://www.google.com", "http://www.ethanaa2.com", 2);
        BackLink backLink3 = new BackLink("http://www.google2.com", "http://www.ethanaa2.com", 3);

        repository.save(Arrays.asList(backLink, backLink2, backLink3));

        Page<BackLink> page = repository.findByFromUriAuthority("http://www.google.com", new PageRequest(0, 20));

        assertThat(page).isNotNull();
        assertThat(page).hasSize(2);

        Page<BackLink> page2 = repository.findByFromUriAuthority("http://www.google2.com", new PageRequest(0, 20));

        assertThat(page2).isNotNull();
        assertThat(page2).hasSize(1);
    }
}
