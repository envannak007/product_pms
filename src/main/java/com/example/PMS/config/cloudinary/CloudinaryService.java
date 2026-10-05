package com.example.PMS.config.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public Map<String, Object> uploadImage(MultipartFile file)
            throws IOException {

        return cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.emptyMap()
        );
    }
    public Map<String, Object> deleteImage(String publicId) throws IOException {
        return cloudinary.uploader().destroy(
                publicId, ObjectUtils.emptyMap()
        );}
}
