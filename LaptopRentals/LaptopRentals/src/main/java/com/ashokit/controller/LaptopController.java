package com.ashokit.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.dtos.LaptopDTO;
import com.ashokit.model.Laptop;
import com.ashokit.service.LaptopService;

@RestController
@RequestMapping("/api/laptops")

public class LaptopController {

     @Autowired
    private  LaptopService laptopService;

    @PostMapping
    public ResponseEntity<Laptop> createLaptop(@ModelAttribute LaptopDTO laptopDTO) {
        return ResponseEntity.ok(laptopService.createLaptop(laptopDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Laptop> updateLaptop(@PathVariable Long id, @RequestBody LaptopDTO dto) {
        return ResponseEntity.ok(laptopService.updateLaptop(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laptop> getLaptop(@PathVariable Long id) {
        return ResponseEntity.ok(laptopService.getLaptopById(id));
    }

    @GetMapping
    public ResponseEntity<List<Laptop>> getAllLaptops() {
        return ResponseEntity.ok(laptopService.getAllLaptops());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaptop(@PathVariable Long id) {
        laptopService.deleteLaptop(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/bulk")
    public ResponseEntity<List<Laptop>> createLaptops(@RequestBody List<LaptopDTO> laptopDTOs) {
        List<Laptop> savedLaptops = laptopService.createLaptops(laptopDTOs);
        return new ResponseEntity<>(savedLaptops, HttpStatus.CREATED);
    }

}
