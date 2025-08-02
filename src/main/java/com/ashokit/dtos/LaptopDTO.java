package com.ashokit.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Data
public class LaptopDTO {
    private String brand;
    private String model;
    private double pricePerMonth;
    private String processor;
    private int ramSize;
    private int storageSize;
   
    public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
    private MultipartFile image;
    
    
    private String imageUrl; 
}
