package com.deposit.crs.eiconsumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	private ClientHttpConnector connector() {
		// HttpClient class to set timeout periods for connection timeout, read timeout
		// and write timeouts.

		HttpClient httpClient = HttpClient.create().tcpConfiguration(
				client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000).doOnConnected(conn -> conn
						.addHandlerLast(new ReadTimeoutHandler(10)).addHandlerLast(new WriteTimeoutHandler(10))));

		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

		return connector;
	}

	@Bean(name = "eiapp")
	@Description("ei web client")
	public WebClient eiClient(@Value("${ei.service.url}") String serviceUrl) {
	
		return WebClient.builder().baseUrl(serviceUrl).clientConnector(connector())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}
	
	
	
	@Bean(name = "fisapp")
	@Description("fis web client")
	public WebClient fisClient(@Value("${fis.service.url}") String serviceUrl) {
	
		return WebClient.builder().baseUrl(serviceUrl).clientConnector(connector())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}
	
	
	
	
	


}
