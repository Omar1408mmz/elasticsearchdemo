package com.example.springbootelasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.CreateRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.util.ObjectBuilder;
import com.example.springbootelasticsearch.helper.Indices;
import com.example.springbootelasticsearch.helper.Utility;
import jakarta.annotation.PostConstruct;


import jakarta.json.stream.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.elasticsearch.client.RequestOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IndexService {
    private final List<String> INDICES_TO_CREATE = List.of(Indices.VEHICLE_INDEX);
    private final ElasticsearchClient elasticsearchClient;

    @PostConstruct
    public void tryToCreateIndices(){
        recreateIndices(false);
        }

        public void recreateIndices(final boolean deleteExisting){
            final String settings = Utility.loadAsString("static/es-settings.json");

            for (final String indexName : INDICES_TO_CREATE) {
                try {
                    boolean indexExists = elasticsearchClient.indices()
                            .exists(builder -> builder.index(indexName)).value();
                    if(indexExists){
                        if(!deleteExisting){
                            continue;
                        }
                        elasticsearchClient.delete(d-> d.index(indexName));
                    }
                    final String  mappings = Utility.loadAsString("static/mappings/" + indexName + ".json");
                    if(settings == null || mappings == null){
                        log.error("Failed to create index with the name '{}'",indexName);
                        continue;
                    }

                    JsonpMapper mapper = elasticsearchClient._transport().jsonpMapper();
                    try (JsonParser parser = mapper.jsonProvider().createParser(new StringReader(mappings))){

                        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(request -> request.index(indexName)
                                .mappings(TypeMapping._DESERIALIZER.deserialize(parser, mapper)));
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }

}
