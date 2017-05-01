package com.ethanaa.deepcrawl.writer.config;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.ethanaa.deepcrawl.model.SpringProfile;
import org.socialsignin.spring.data.dynamodb.core.DynamoDBOperations;
import org.socialsignin.spring.data.dynamodb.core.DynamoDBTemplate;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.ethanaa.deepcrawl.writer.repository", dynamoDBOperationsRef = "dynamoDBOperations")
public class DynamoDBConfig {

    @Autowired
    private Environment environment;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() throws Exception {

        if(Arrays.stream(environment.getActiveProfiles()).anyMatch(
                prof -> (prof.equals(SpringProfile.TEST)))) {

            return DynamoDBEmbedded.create().amazonDynamoDB();

        } else if (Arrays.stream(environment.getActiveProfiles()).noneMatch(
                prof -> (prof.equals(SpringProfile.PROD)))) {

            final String[] localArgs = { "-inMemory" };
            DynamoDBProxyServer server = ServerRunner.createServerFromCommandLineArgs(localArgs);
            server.start();

            AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient();
            dynamoDB.setRegion(Region.getRegion(Regions.US_EAST_1));
            dynamoDB.setEndpoint("http://localhost:8000");

            return dynamoDB;
        }

        // TODO prod config
        return null;
    }

    @Bean
    public DynamoDBOperations dynamoDBOperations(AmazonDynamoDB amazonDynamoDB) {

        return new DynamoDBTemplate(amazonDynamoDB);
    }
}
