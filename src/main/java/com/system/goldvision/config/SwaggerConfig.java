package com.system.goldvision.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    @Bean
    public Docket api() {

        List<ResponseMessage> list = new java.util.ArrayList<>();

        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.system.goldvision"))
                .paths(PathSelectors.any()).build().securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
                .useDefaultResponseMessages(false).apiInfo(apiInfo()).globalResponseMessage(RequestMethod.GET, list)
                .globalResponseMessage(RequestMethod.POST, list);
    }

    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope("read", "read all"));
        authorizationScopeList.add(new AuthorizationScope("write", "access all"));

        List<GrantType> grantTypes = new ArrayList();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant("http://localhost:8080" +"/oauth/token");

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }


    @Bean
    SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
        authorizationScopes[0] = new AuthorizationScope("read", "read all");
        authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
        authorizationScopes[2] = new AuthorizationScope("write", "write all");
        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration("angular", "@ngul@r0", "", "", "", ApiKeyVehicle.HEADER, "", " ");
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("GoldVision API").description("\"Documentação da API GoldVision\"")
                .contact(new Contact("Pedro Lucas", "https://github.com/PedroLucasOM", "pedro99lucasom@gmail.com"))
                .license("Open Source").version("1.0.0").build();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
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
