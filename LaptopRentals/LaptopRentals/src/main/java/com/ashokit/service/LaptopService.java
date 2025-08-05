package com.ashokit.service;



import java.util.List;

import com.ashokit.dtos.LaptopDTO;
import com.ashokit.model.Laptop;

public interface LaptopService {

    Laptop createLaptop(LaptopDTO laptopDTO);
    Laptop updateLaptop(Long id, LaptopDTO laptopDTO);
    Laptop getLaptopById(Long id);
    List<Laptop> getAllLaptops();
    void deleteLaptop(Long id);
    
    List<Laptop> createLaptops(List<LaptopDTO> laptopDTOList);

}
