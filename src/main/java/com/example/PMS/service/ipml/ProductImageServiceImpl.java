package com.example.PMS.service.ipml;
import com.example.PMS.dto.request.ProductImageRequest;
import com.example.PMS.dto.response.ProductImageResponse;
import com.example.PMS.entity.ProductEntity;
import com.example.PMS.entity.ProductImageEntity;
import com.example.PMS.exception.ResourceNotFoundException;
import com.example.PMS.mapper.ProductImageMapper;
import com.example.PMS.repository.ProductImageRepository;
import com.example.PMS.repository.ProductRepository;
import com.example.PMS.service.CloudinaryService;
import com.example.PMS.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final ProductImageMapper productImageMapper;

    @Override
    public ProductImageResponse create(ProductImageRequest imageRequest) throws IOException {
        ProductEntity product = productRepository.findById(imageRequest.productID())
                .orElseThrow(()->new ResourceNotFoundException("Product not found."));

        Map<String, Object> uploadResult = cloudinaryService.uploadImage(imageRequest.image());
        String imageUrl = uploadResult.get("secure_url").toString();
        String publicId = uploadResult.get("public_id").toString();

        ProductImageEntity image = new ProductImageEntity();
        image.setImageUrl(imageUrl);
        image.setPublicId(publicId);
        image.setProduct(product);

        ProductImageEntity saved = productImageRepository.save(image);
        return productImageMapper.toResponse(saved);

    }

    @Override
    public List<ProductImageResponse> findAll() {

        return productImageRepository.findAll()
                .stream()
                .map(productImageMapper::toResponse)
                .toList();
    }

    @Override
    public ProductImageResponse findById(Integer id) {

        ProductImageEntity productImage = productImageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product image not found."));
        return productImageMapper.toResponse(productImage);
    }

    @Override
    public List<ProductImageResponse> findByProductId(Integer productId) {

        return productImageRepository
                .findByProductId(productId)
                .stream()
                .map(productImageMapper::toResponse)
                .toList();
    }

    @Override
    public ProductImageResponse update(Integer id, MultipartFile file) throws IOException {

        // find Product image
        ProductImageEntity image = productImageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product image not found."));

        String oldImage = image.getPublicId();

        Map<String,Object> uploadResult = cloudinaryService.uploadImage(file);
        image.setImageUrl(uploadResult.get("secure_url").toString());
        image.setPublicId(uploadResult.get("public_id").toString());

        ProductImageEntity updated = productImageRepository.save(image);

        if (oldImage != null && !oldImage.isBlank()){
            cloudinaryService.deleteImage(oldImage);
        }

        return productImageMapper.toResponse(updated);

    }


    @Override
    public void delete(Integer id) throws IOException {
        ProductImageEntity image = productImageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product image not found."));

        // delete image from Cloudinary
        cloudinaryService.deleteImage(image.getPublicId());

        // delete image from database
        productImageRepository.delete(image);
    }
}
