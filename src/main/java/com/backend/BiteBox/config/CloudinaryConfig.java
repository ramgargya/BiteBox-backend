package com.backend.BiteBox.config;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }


    @Bean
    public Cloudinary getCloudinary() {
        Map config = new HashMap<>();
        config.put("cloud_name", "drwotnwuf");
        config.put("api_key", "616372856761312");
        config.put("api_secret", "_RA80hJ9fBxbBx4AimVXMM9xJb4");
        config.put("secure", true);

        return new Cloudinary(config);
    }
}
