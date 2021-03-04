package ru.aizen.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class MyProfileRewriteFilter implements GatewayFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String username = usernameFromHeader(exchange.getRequest().getHeaders());

		if (username != null) {
			ServerHttpRequest mutatedRequest = exchange.getRequest()
					.mutate()
					.path("/api/v1/user/" + username)
					.build();
			return chain.filter(
					exchange.mutate()
							.request(mutatedRequest)
							.build());
		} else {
			exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
			return exchange.getResponse().setComplete();
		}
	}

	private String usernameFromHeader(HttpHeaders headers) {
		return headers.entrySet()
				.stream()
				.filter(entry -> entry.getKey().equalsIgnoreCase(("-X-Username")))
				.findFirst()
				.map(entry -> entry.getValue().get(0))
				.orElse(null);
	}

}
