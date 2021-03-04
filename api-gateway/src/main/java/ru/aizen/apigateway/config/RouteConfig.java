package ru.aizen.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.aizen.apigateway.filter.MyProfileRewriteFilter;
import ru.aizen.apigateway.filter.ProfileRewriteFilter;
import ru.aizen.apigateway.filter.jwt.JwtFilter;

@Configuration
public class RouteConfig {

	@Value("${identityServiceUri}")
	private String identityService;

	@Bean
	public RouteLocator noSecureRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("registration",
						route -> route.path("/registration")
								.filters(f -> f.rewritePath("/registration", "/api/v1/user/registration"))
								.uri(identityService))
				.route("login",
						route -> route.path("/login")
								.filters(f -> f.rewritePath("/login", "/api/v1/user/login"))
								.uri(identityService))
				.build();
	}

	@Bean
	public RouteLocator secureMyProfileRouteLocator(RouteLocatorBuilder builder, JwtFilter jwtFilter, MyProfileRewriteFilter myProfileRewriteFilter) {
		return builder.routes()
				.route("my_profile",
						route -> route
								.path("/profile/me")
								.filters(f -> f.filter(jwtFilter)
										//	.rewritePath("/profile/me", "/api/v1/user")
										.filter(myProfileRewriteFilter))
								.uri(identityService))
				.build();
	}

	@Bean
	public RouteLocator secureProfileRouteLocator(RouteLocatorBuilder builder, JwtFilter jwtFilter, ProfileRewriteFilter profileRewriteFilter) {
		return builder.routes()
				.route("user_profile",
						route -> route
								.path("/profile/{username}")
								.filters(f -> f.filter(jwtFilter)
										.rewritePath("/profile/(?<segment>.*)", "/api/v1/user/${segment}")
										.filter(profileRewriteFilter))
								.uri(identityService))
				.route("user_profile_edit",
						route -> route
								.path("/profile/{username}/edit")
								.filters(f -> f.filter(jwtFilter)
										.rewritePath("/profile/(?<segment>.*)/edit", "/api/v1/user/${segment}/edit")
										.filter(profileRewriteFilter))
								.uri(identityService))
				.build();


	}
}
