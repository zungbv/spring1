package vn.atv.spring.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@EnableWebMvc
@Configuration
@PropertySource("classpath:myDemoApp.properties")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Value("${demo.pathToUserImage}")
    private static String pathToUserImage;

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        if(pathToUserImage==null){
            pathToUserImage=env.getProperty("demo.pathToUserImage");
        }
        resourceHandlerRegistry.addResourceHandler("/userImage/**").addResourceLocations("file:"+pathToUserImage);

        resourceHandlerRegistry.addResourceHandler("/res/**").addResourceLocations("classpath:/static/public/");
    }
}
