/**
 * 
 */
package net.jin.config;

import java.util.*;

import org.springframework.context.annotation.*;

import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

/**
 * @author njh
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("Jimmy API Delta").version("1.1.1.0").description("Jimmy's API Delta List")
				.build();
	}

	// Bean 등록
	@Bean
	public Docket commonApi() {
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes()).groupName("API with Swagger").apiInfo(this.apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("net.jin.controller")).paths(PathSelectors.ant("/**"))
				.build();
	}

	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}
}
