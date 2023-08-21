package com.example.springbootelasticsearch.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.erhlc.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.example.springbootelasticsearch.repository")
public class PersonConfiguration {
/*    @
    @Override
    public org.elasticsearch.client.RestHighLevelClient elasticsearchClient() {
        return null;
    }*/

/*    @Bean
    public ElasticsearchClient getElasticsearchClient(){
        return new ElasticsearchClient();
    }*/
}
