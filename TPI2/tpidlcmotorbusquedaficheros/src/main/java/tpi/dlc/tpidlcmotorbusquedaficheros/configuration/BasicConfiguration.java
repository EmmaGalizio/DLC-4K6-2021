package tpi.dlc.tpidlcmotorbusquedaficheros.configuration;

import tpi.dlc.tpidlcmotorbusquedaficheros.indexation.structure.VocabularySlot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

@Configuration
public class BasicConfiguration implements WebMvcConfigurer {

    @Value("${tpi.dlc.documentsResourceDirectory}")
    private String resorucesPath;
    @Value("${tpi.dlc.documentsResourcePattern}")
    private String resoursePathPattern;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Map<String, VocabularySlot> getVocabulary(){
        return Collections.synchronizedMap(new Hashtable<>());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/documentos/**")
                .addResourceLocations("file:"+resorucesPath+ File.separator);
    }
}
