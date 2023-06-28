package com.ncs.demo.config;

import com.ncs.demo.config.interceptor.LoginCheckInterceptor;
import com.ncs.demo.repository.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MemberRepository memberRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/login",
                        "logout",
                        "/signUp",
                        "/css/**",
                        "/img/**",
                        "/*.ico",
                        "/error",
                        "/lib/**",
                        "/js/**"

                );

    }
}
