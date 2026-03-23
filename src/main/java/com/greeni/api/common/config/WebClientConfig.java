package com.greeni.api.common.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	@Value("${ai.url}")
	private String url;

	@Bean
	public WebClient aiWebClient() {

		HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
			.responseTimeout(Duration.ofSeconds(30));

		return WebClient.builder()
			.baseUrl("http://"+url+":8000")
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.defaultHeader("Content-Type", "application/json")
			.build();
	}
}
