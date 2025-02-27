package com.example.spring.essentials.config;

import com.example.spring.essentials.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 설정하기 위한 어노테이션, @Component 포함
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> filterRegistrationBean() {
        FilterRegistrationBean<LoggingFilter> filterRegistration = new FilterRegistrationBean<>();

        filterRegistration.setFilter(new LoggingFilter());
        filterRegistration.addUrlPatterns("/api/*"); // 패턴 지정
        filterRegistration.setOrder(1); // 순서 지정

        return filterRegistration;
    }
}
