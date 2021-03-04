package ru.aizen.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		String identityService = System.getenv("IDENTITY_SERVICE_URL");

		return builder.routes()
				.route("registration",
						route -> route.path("/registration")
								.filters(f -> f.rewritePath("/registration", "/api/v1/user/registration"))
								.uri(identityService))
				.route("login",
						route -> route.path("/login")
								.filters(f -> f.rewritePath("/login", "/api/v1/user/login"))
								.uri(identityService))
				.route("my_profile",
						route -> route
								.path("/profile/me")
								.filters(f -> f.rewritePath("/profile/me", "/api/v1/user/johndoe88"))
								.uri(identityService))
				.route("user_profile",
						route -> route
								.path("/profile/**")
								.filters(f -> f.rewritePath("/profile/(?<segment>.*)", "/api/v1/user/${segment}"))
								.uri(identityService))
				.route("user_profile_edit",
						route -> route
								.path("/profile/**")
								.filters(f -> f.rewritePath("/profile/(?<segment>.*)", "/api/v1/user/${segment}/edit"))
								.uri(identityService))
				.build();
	}
}
