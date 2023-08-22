package com.example.springbootelasticsearch.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.example.springbootelasticsearch.repository")
public class PersonConfiguration {

    @Bean
    public ElasticsearchClient getElasticsearchClient() {

        RestClient client = RestClient.builder(new HttpHost("localhost", 9200)).build();
        JacksonJsonpMapper jsonMapper = new JacksonJsonpMapper();
        RestClientTransport restClientTransport = new RestClientTransport(client, jsonMapper);
        return new ElasticsearchClient(restClientTransport);
    }


}
