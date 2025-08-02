package com.ashokit.model;



import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private double pricePerMonth;
    private String processor;
    private int ramSize;
    private int storageSize;
   
    private String imageUrl; 
}
