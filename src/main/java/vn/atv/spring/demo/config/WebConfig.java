package vn.atv.spring.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import vn.atv.spring.demo.view.UserExcelViewResolver;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.TEXT_HTML)
                .favorPathExtension(true)
                .favorParameter(false)
                .ignoreAcceptHeader(true);
    }

    @Bean
    public ContentNegotiatingViewResolver viewResolver(ContentNegotiationManager cnManager){
        ContentNegotiatingViewResolver cnResolver =new ContentNegotiatingViewResolver();
        cnResolver.setContentNegotiationManager(cnManager);

        UserExcelViewResolver userExcelViewResolver=new UserExcelViewResolver();

        List<ViewResolver> resolverList=new ArrayList<>();
        resolverList.add(userExcelViewResolver);

        cnResolver.setViewResolvers(resolverList);
        cnResolver.setOrder(Ordered.LOWEST_PRECEDENCE);
        return cnResolver;
    }
}
