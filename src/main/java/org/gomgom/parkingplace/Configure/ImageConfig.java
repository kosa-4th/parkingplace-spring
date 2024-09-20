package org.gomgom.parkingplace.Configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    @Value(value = "${parple.file.prefix}" + "${parple.upload.path}")
    private String realPath;
    @Value(value="/api" + "${parple.upload.virtual}")
    private String virtualPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(virtualPath)
                .addResourceLocations(realPath + File.separator);
    }
}