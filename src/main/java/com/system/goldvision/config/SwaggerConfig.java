package com.system.goldvision.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

	@Bean
	public Docket greetingApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.system.goldvision")).build()
				.securitySchemes(Lists.newArrayList(apiKey())).securityContexts(Lists.newArrayList(securityContext()))
				.apiInfo(metaData());

	}

	@Bean
	SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private ApiInfo metaData() {
		return new ApiInfoBuilder().title("GoldVision API").description("\"Documentação da API GoldVision\"")
				.version("1.0.0").license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"").build();
	}
	
	@Bean
    public WebMvcConfigurer webMvcConfigurer()
    {
        return new WebMvcConfigurer()
        {
            @Override
            public void addResourceHandlers( ResourceHandlerRegistry registry )
            {
                registry.addResourceHandler( "swagger-ui.html" ).addResourceLocations( "classpath:/META-INF/resources/" );
                registry.addResourceHandler( "/webjars/**" ).addResourceLocations( "classpath:/META-INF/resources/webjars/" );
            }

			@Override
			public void configurePathMatch(PathMatchConfigurer configurer) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addFormatters(FormatterRegistry registry) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void configureViewResolvers(ViewResolverRegistry registry) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Validator getValidator() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public MessageCodesResolver getMessageCodesResolver() {
				// TODO Auto-generated method stub
				return null;
			}
        };
    }
}
