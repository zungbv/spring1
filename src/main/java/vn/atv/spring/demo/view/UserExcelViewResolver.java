package vn.atv.spring.demo.view;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Locale;
@Configuration
@EnableWebMvc
public class UserExcelViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        UserExcelView excelView=new UserExcelView();
        return excelView;
    }
}
