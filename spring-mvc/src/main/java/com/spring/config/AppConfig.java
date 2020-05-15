package com.spring.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ComponentScan("com")
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/page/",".html");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            System.out.println("converter: "+ converter);
        }
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converters.add(converter);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }

    @Override
    public Validator getValidator() {
        return null;
    }

    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }
}
