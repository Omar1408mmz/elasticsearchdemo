package com.example.springbootelasticsearch.search.utils;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.util.ObjectBuilder;
import com.example.springbootelasticsearch.search.SearchRequestDTO;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

@NoArgsConstructor
public class SearchUtil {
    public static Query getQueryBuilder(SearchRequestDTO dto){
        if(dto == null){
            return null;
        }
        final List<String> fields = dto.getFields();
        if(CollectionUtils.isEmpty(fields)){
            return null;
        }
        if(fields.size() > 1){

           MultiMatchQuery queryBuilder = QueryBuilders.multiMatch(builder -> builder.query(dto.getSearchTerm())
                    .type(TextQueryType.CrossFields).operator(Operator.And).fields(fields)).multiMatch();
            return queryBuilder._toQuery();

        }
        return fields.stream()
                .findFirst()
                .map(
                        field -> QueryBuilders.match(
                                builder -> (ObjectBuilder<MatchQuery>) builder.query(dto.getSearchTerm())
                                        .field(field).operator(Operator.And).build()
                        )
                ).orElse(null);
    }

    public static SearchRequest buildSearchRequest(final String indexName,final SearchRequestDTO dto){
        return   SearchRequest.of(b->b.index(indexName).postFilter(getQueryBuilder(dto)));
    }


}
