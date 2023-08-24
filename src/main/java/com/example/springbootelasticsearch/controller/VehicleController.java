package com.example.springbootelasticsearch.controller;

import com.example.springbootelasticsearch.document.Person;
import com.example.springbootelasticsearch.document.Vehicle;
import com.example.springbootelasticsearch.search.SearchRequestDTO;
import com.example.springbootelasticsearch.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/save")
    public void index(@RequestBody final Vehicle vehicle) throws IOException {
         vehicleService.index(vehicle);
    }

    @GetMapping("/{id}")
    public Vehicle getPersonById(@PathVariable final String id) throws IOException {
        return vehicleService.getVehicleById(id);
    }

    @PostMapping("/search")
    public void index(@RequestBody final SearchRequestDTO dto) throws IOException {
        vehicleService.search(dto);
    }
}
