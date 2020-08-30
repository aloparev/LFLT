package home.lflt.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * collection of one pager controllers
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    path and view template
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/awake").setViewName("home");
//        registry.addViewController("/login");
        registry.addViewController("/game").setViewName("template");
    }
}
