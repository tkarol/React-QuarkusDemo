package io.akoserwal;

import com.google.common.base.Predicates;
import org.springframework.*;

import com.beust.jcommander.internal.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.testng.annotations.IConfigurationAnnotation;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import javax.servlet.*;

import java.util.*;

@Configuration
@EnableWebSecurity
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public SecurityConfiguration securityConfiguration() {

        Map<String, Object> additionalQueryStringParams = new HashMap<>();
        additionalQueryStringParams.put("nonce", "123456");

        return SecurityConfigurationBuilder.builder()
                .clientId("quarkus-bff-tyler").realm("quarkus").appName("react-ff")
                .additionalQueryStringParams(additionalQueryStringParams)
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.akoserwal"))
                .paths(PathSelectors.any())
                .build().securitySchemes(buildSecurityScheme()).securityContexts(buildSecurityContext());
    }

    private List<SecurityContext> buildSecurityContext() {
        List<SecurityReference> securityReferences = new ArrayList<>();

        securityReferences.add(SecurityReference.builder().reference("oauth2").scopes(scopes().toArray(new AuthorizationScope[]{})).build());

        SecurityContext context = SecurityContext.builder().forPaths(Predicates.alwaysTrue()).securityReferences(securityReferences).build();

        List<SecurityContext> ret = new ArrayList<>();
        ret.add(context);
        return ret;
    }

    private List<? extends SecurityScheme> buildSecurityScheme() {
        List<SecurityScheme> lst = new ArrayList<>();
        // lst.add(new ApiKey("api_key", "X-API-KEY", "header"));

        LoginEndpoint login = new LoginEndpointBuilder().url("http://localhost:8888/auth/realms/quarkus/protocol/openid-connect/auth").build();

        List<GrantType> gTypes = new ArrayList<>();
        gTypes.add(new ImplicitGrant(login, "acces_token"));

        lst.add(new OAuth("oauth2", scopes(), gTypes));
        return lst;
    }

    private List<AuthorizationScope> scopes() {
        List<AuthorizationScope> scopes = new ArrayList<>();
        for (String scopeItem : new String[]{"openid=openid", "profile=profile"}) {
            String scope[] = scopeItem.split("=");
            if (scope.length == 2) {
                scopes.add(new AuthorizationScopeBuilder().scope(scope[0]).description(scope[1]).build());
            } //else {
              //  log.warn("Scope '{}' is not valid (format is scope=description)", scopeItem);
           // }
        }

        return scopes;
    }
}
