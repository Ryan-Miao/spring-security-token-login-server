package com.example.serverapi.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 自动生成API文档配置.
 * @author Ryan Miao
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Value("${swagger.enable}")
    private boolean enableSwagger;


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("认证 API")
                .description("提供登录，token认证，鉴权，权限管理.")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .build();
    }

    /**
     * doc.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build()
                .globalOperationParameters(globalOperationParameters())
                .apiInfo(apiInfo());
    }

    private List<Parameter> globalOperationParameters() {
        final List<Parameter> parametersList = new ArrayList<>();

        final Parameter authorization = new ParameterBuilder()
                .name("Authorization")
                .description("Bearer tokenxxxxx 除了登陆接口外，都需要通过此header或者url参数token来传递token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        parametersList.add(authorization);

        return parametersList;
    }
}
