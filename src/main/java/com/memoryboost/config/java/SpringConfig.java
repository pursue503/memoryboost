package com.memoryboost.config.java;

import com.memoryboost.config.web.WebMvcConfig;
import com.memoryboost.util.interceptor.LoginSuccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class SpringConfig extends WebMvcConfig {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // /members/sns-signup - 입력창은 제외
        registry.addInterceptor(new LoginSuccessInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/members/sns-signup")
                .excludePathPatterns("/members/sns")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/img/**");
    }
}
