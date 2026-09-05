package com.example.PMS.dto.response;
public record ProductImageResponse (
        Integer id,
        String imageUrl,
        String publicId,
        Integer productID
){
}
