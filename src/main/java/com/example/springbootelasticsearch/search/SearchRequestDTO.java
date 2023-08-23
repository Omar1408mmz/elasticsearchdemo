package com.example.springbootelasticsearch.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequestDTO {

    private List<String> fields;
    private String searchTerm;

}
