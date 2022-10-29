//package cn.me.ppx.infrastructure.web.config;
//
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.oas.annotations.EnableOpenApi;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
///**
// * @author ym
// * @date 2022/10/12 9:42
// */
//@Configuration
//@EnableOpenApi
//@EnableKnife4j
//public class SwaggerConfig {
//    @Value("application.name")
//    @Autowired
//    private ApplicationContext applicationContext;
//    private String applicationName;
//    @Value("application.version")
//    private String applicationVersion;
//
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any())
//                .build()
//                .enable(true);
//    }
//
//    /**
//     * Api页面信息配置
//     *
//     * @return
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                //描述字段支持Markdown语法
//                .description("# Knife4j RESTful APIs")
//                .termsOfServiceUrl("https://doc.xiaominfo.com/")
//                .contact(new Contact("ppx","","xiaoymin@foxmail.com"))
//                .version("1.01")
//                .build();
//    }
//
//}
