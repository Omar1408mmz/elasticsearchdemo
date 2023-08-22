package com.example.springbootelasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.CreateRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.util.ObjectBuilder;
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
    private final List<String> INDICES_TO_CREATE = List.of("vehicle");
    private final ElasticsearchClient elasticsearchClient;

    @PostConstruct
    public void tryToCreateIndices(){

        final String settings = Utility.loadAsString("static/es-settings.json");

        for (final String indexName : INDICES_TO_CREATE) {
            try {
                boolean indexExists = elasticsearchClient.indices()
                        .exists(ExistsRequest.of(builder -> builder.index(indexName))).value();
                if(indexExists){
                    continue;
                }
                final String  mappings = Utility.loadAsString("static/mappings/" + indexName + ".json");
                if(settings == null || mappings == null){
                    log.error("Failed to create index with the name '{}'",indexName);
                    continue;
                }
                JsonpMapper mapper = elasticsearchClient._transport().jsonpMapper();
                JsonParser parser = mapper.jsonProvider().createParser(new StringReader(mappings));

                elasticsearchClient.indices().create(request->request.index(indexName)
                                .mappings(TypeMapping._DESERIALIZER.deserialize(parser,mapper)));
/*                        .mappings(m->m.indexField(builder -> {
                            try {
                               return   builder.withJson(new FileInputStream(new ClassPathResource("static/mappings/" + indexName + ".json").getFile()));
                            } catch (IOException e) {
                                log.error(e.getMessage(),e);
                                return null;
                            }
                        })).settings(s-> {
                            try {
                                return s.withJson(new FileInputStream(new ClassPathResource("static/es-settings.json").getFile()));
                            } catch (IOException e) {
                                log.error(e.getMessage(),e);
                                return null;
                            }
                        }));*/

            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }
        }

}
