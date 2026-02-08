package pharmacy_webapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {

    private final Cloudinary cloudinary;

    private String extractPublicIdFromUrl(String imageUrl) {
        // Extract public_id from Cloudinary URL
        // Example: https://res.cloudinary.com/cloud-name/image/upload/v123456/pharmacy_products/image.jpg
        // public_id: pharmacy_products/image
        String[] parts = imageUrl.split("/upload/");
        if (parts.length > 1) {
            String path = parts[1];
            // Remove version number (v123456/)
            path = path.replaceFirst("v\\d+/", "");
            // Remove file extension
            return path.substring(0, path.lastIndexOf('.'));
        }
        return imageUrl;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        try{
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "pharmacy_products",
                            "resource_type", "auto"
                    )
            );
            return uploadResult.get("secure_url").toString();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    public List<String> uploadMultipleImages(List<MultipartFile> files) throws IOException {
        ArrayList<String> imageUrls = new ArrayList<>();
        for(MultipartFile file : files){
            if(file != null && !file.isEmpty()){
                String imageUrl = uploadImage(file);
                imageUrls.add(imageUrl);
            }
        }
        return imageUrls;
    }

    public void deleteImage(String imageUrl) throws IOException {
        try{
            String publicId = extractPublicIdFromUrl(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }catch (Exception e){
            log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    public void deleteMultipleImages(List<String> imageUrls) throws IOException {
        for(String imageUrl : imageUrls){
            if(imageUrl != null && !imageUrl.isEmpty()){
                deleteImage(imageUrl);
            }
        }
    }

}
