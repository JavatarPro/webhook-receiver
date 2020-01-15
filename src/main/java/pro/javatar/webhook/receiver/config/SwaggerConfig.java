package pro.javatar.webhook.receiver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Borys Zora
 * @version 2020-01-15
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pro.javatar.webhook.receiver.resource"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("webhook-receiver")
                .contact(new Contact("Development Team", null, "borys.zora@javatar.pro"))
                .description("This project is about webhooks helper for javatar declarative pipeline")
                .termsOfServiceUrl("http://api.javatar.pro")
                .license("Private licence. All rights reserved by Javatar LLC")
                .licenseUrl("https://javatar.pro/LICENSE")
                .build();
    }
}
