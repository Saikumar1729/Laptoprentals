package com.ashokit.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ashokit.cloudinarySignatureUtil.CopyUtils;
import com.ashokit.dtos.LaptopDTO;
import com.ashokit.laptopException.ResourceNotFoundException;
import com.ashokit.model.Laptop;
import com.ashokit.repository.LaptopRepository;
import com.ashokit.service.CloudinaryUploader;
import com.ashokit.service.LaptopService;

@Service
public class LaptopServiceImpl implements LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    @Autowired
    private CloudinaryUploader cloudinaryUploader;

    @Override
    public Laptop createLaptop(LaptopDTO dto) {
        Laptop laptop = new Laptop();
        BeanUtils.copyProperties(dto, laptop);

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = uploadToCloudinary(dto.getImage());
            laptop.setImageUrl(imageUrl);
        }

        return laptopRepository.save(laptop);
    }

    @Override
    public Laptop updateLaptop(Long id, LaptopDTO dto) {
        Laptop existing = laptopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laptop not found with id: " + id));

        CopyUtils.copyNonNullProperties(dto, existing); // ✅ Use safe copy

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = uploadToCloudinary(dto.getImage());
            existing.setImageUrl(imageUrl);
        }

        return laptopRepository.save(existing);
    }

    



    @Override
    public Laptop getLaptopById(Long id) {
        return laptopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laptop not found with id: " + id));
    }

    @Override
    public List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }

    @Override
    public void deleteLaptop(Long id) {
        Laptop laptop = getLaptopById(id);
        laptopRepository.delete(laptop);
    }

    @Override
    public List<Laptop> createLaptops(List<LaptopDTO> laptopDTOList) {
        List<Laptop> laptops = new ArrayList<>();

        for (LaptopDTO dto : laptopDTOList) {
            Laptop laptop = new Laptop();
            BeanUtils.copyProperties(dto, laptop);

            if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                String imageUrl = uploadToCloudinary(dto.getImage());
                laptop.setImageUrl(imageUrl);
            }

            laptops.add(laptop);
        }

        return laptopRepository.saveAll(laptops);
    }

    private String uploadToCloudinary(MultipartFile file) {
        try {
            return cloudinaryUploader.uploadFile(file);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload image to Cloudinary", e);
        }
    }
}
