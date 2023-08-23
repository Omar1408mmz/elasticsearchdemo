package com.example.springbootelasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import com.example.springbootelasticsearch.document.Vehicle;
import com.example.springbootelasticsearch.helper.Indices;
import com.example.springbootelasticsearch.search.SearchRequestDTO;
import com.example.springbootelasticsearch.search.utils.SearchUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    final ElasticsearchClient elasticsearchClient;

    public boolean index(Vehicle vehicle) throws IOException {

        final  IndexResponse indexResponse = elasticsearchClient.index(IndexRequest.of(req -> req.index(Indices.VEHICLE_INDEX)
                .id(vehicle.getId()).document(vehicle)));
        return  Result.Created.equals(indexResponse.result());
    }

    public Vehicle getVehicleById(String vehicleId) throws IOException {
        GetResponse<Vehicle> vehicleGetResponse = elasticsearchClient.get(request -> request.index(Indices.VEHICLE_INDEX).id(vehicleId), Vehicle.class);
        if (!vehicleGetResponse.found()){
            return null;
        }
        return vehicleGetResponse.source();
    }

    public List<Vehicle> search(SearchRequestDTO dto)  {
        final SearchRequest request = SearchUtil.buildSearchRequest(Indices.VEHICLE_INDEX,dto);
        if (request == null){
            log.error("Failed to build search request");
            return Collections.emptyList();
        }
        try {
            SearchResponse<Vehicle> searchResponse = elasticsearchClient.search(request, Vehicle.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
