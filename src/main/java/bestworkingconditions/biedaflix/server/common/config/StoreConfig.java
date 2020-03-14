package bestworkingconditions.biedaflix.server.common.config;

import bestworkingconditions.biedaflix.server.common.model.FileResource;
import bestworkingconditions.biedaflix.server.common.properties.AppProperties;
import bestworkingconditions.biedaflix.server.common.properties.StoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.config.FilesystemStoreConfigurer;
import org.springframework.content.fs.config.FilesystemStoreConverter;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.content.rest.config.ContentRestConfigurer;
import org.springframework.content.rest.config.RestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;

import java.io.File;
import java.nio.file.Paths;

@Configuration
@EnableFilesystemStores
public class StoreConfig {

    private final StoreProperties storeProperties;
    private final AppProperties appProperties;

    @Autowired
    public StoreConfig(StoreProperties storeProperties, AppProperties appProperties) {this.storeProperties = storeProperties;
        this.appProperties = appProperties;
    }


    @Bean
    File fileSystemRoot(StoreProperties properties) {
        File root = Paths.get(properties.getPath()).toAbsolutePath().normalize().toFile();

        if(!root.exists())
            root.mkdir();

        return root;
    }

    @Bean
    FileSystemResourceLoader fileSystemResourceLoader() {
        return new FileSystemResourceLoader(fileSystemRoot(storeProperties).getAbsolutePath());
    }

    @SuppressWarnings("Convert2Lambda")
    @Bean
    public ContentRestConfigurer contentRestConfigurer(){
        return new ContentRestConfigurer() {
            @Override
            public void configure(RestConfiguration config) {
                config.getCorsRegistry().addMapping(storeProperties.getPath()+"**")
                      .allowedMethods("GET")
                      .allowedOrigins(appProperties.getApiDomain())
                      .maxAge(3600);
            }
        };
    }

    public Converter<FileResource,String> fileResourceStringConverter(){
        return new FilesystemStoreConverter<FileResource, String>() {
            @Override
            public String convert(FileResource source) {
                return source.getFilePath();
            }
        };
    }

    @Bean
    public FilesystemStoreConfigurer configurer(){
        return new FilesystemStoreConfigurer() {
            @Override
            public void configureFilesystemStoreConverters(ConverterRegistry registry) {
                registry.addConverter(fileResourceStringConverter());
            }
        };
    }


}
