package com.aurea.deadcode.detector.infra.exception.config;

import com.aurea.deadcode.detector.infra.exception.InvalidGithubUrlRepositoryException;
import com.aurea.deadcode.detector.infra.exception.RepositoryNotFoundException;
import com.aurea.deadcode.detector.infra.exception.UnsupportedLanguagueException;
import com.aurea.deadcode.detector.infra.exception.handler.GeneralExceptionHandler;
import cz.jirutka.spring.exhandler.RestHandlerExceptionResolver;
import cz.jirutka.spring.exhandler.handlers.ErrorMessageRestExceptionHandler;
import cz.jirutka.spring.exhandler.support.HttpMessageConverterUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Configuration
public class SpringExceptionHandlerConfig {

    @Bean
    public RestHandlerExceptionResolver restExceptionResolver() {
        return RestHandlerExceptionResolver.builder().messageSource(this.httpErrorMessageSource())
            .defaultContentType(MediaType.APPLICATION_JSON)
            .addHandler(new ErrorMessageRestExceptionHandler(NullPointerException.class, HttpStatus.INTERNAL_SERVER_ERROR))
            .addHandler(new ErrorMessageRestExceptionHandler(IllegalArgumentException.class, HttpStatus.INTERNAL_SERVER_ERROR))
            .addHandler(new GeneralExceptionHandler<>(RepositoryNotFoundException.class, HttpStatus.NOT_FOUND))
            .addHandler(new GeneralExceptionHandler<>(UnsupportedLanguagueException.class, HttpStatus.INTERNAL_SERVER_ERROR))
            .addHandler(new GeneralExceptionHandler<>(InvalidGithubUrlRepositoryException.class, HttpStatus.INTERNAL_SERVER_ERROR))
            .withDefaultHandlers(true).build();
    }

    @Bean
    public MessageSource httpErrorMessageSource() {
        ReloadableResourceBundleMessageSource m = new ReloadableResourceBundleMessageSource();
        m.setBasename("classpath:customMessages");
        m.setDefaultEncoding("UTF-8");
        return m;
    }

    @Bean
    public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(HttpMessageConverterUtils.getDefaultHttpMessageConverters());
        return resolver;
    }
}
