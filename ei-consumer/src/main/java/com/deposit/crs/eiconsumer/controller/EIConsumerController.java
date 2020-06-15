package com.deposit.crs.eiconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.deposit.crs.eiconsumer.dto.Book;
import com.deposit.crs.eiconsumer.dto.User;
import com.deposit.crs.eiconsumer.exception.MyCustomServerException;
import com.deposit.crs.eiconsumer.exeption.MyCustomClientException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class EIConsumerController {

	@Qualifier("eiapp")
	@Autowired
	WebClient client;

	@GetMapping("/users")
	public Flux<User> getMeUser() {

		return client.get().uri("/posts")
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse ->
                Mono.error(new MyCustomClientException())
            )
            .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                Mono.error(new MyCustomServerException())
            )
				.bodyToFlux(User.class)
				.log("Items in EI");
	}
	
	
	@GetMapping("/users/exchange")
	public Flux<User> getMeUserExchange() {

		return client.get().uri("/posts")
				.exchange()
				.flatMapMany((clientResponse -> {

													if (clientResponse.statusCode().is5xxServerError()) {
										
														return clientResponse.bodyToMono(String.class)
										
																.flatMap(errMsg -> {
																	throw new RuntimeException(errMsg);
																});
														} else {
															return clientResponse.bodyToFlux(User.class);
														}
												}))
			
				.log("Items in EI");
	}
	
	
	@GetMapping("/users/{userId}")
	public Mono<User> getMeUserBasedOnId(@PathVariable int userId) {
		
		

		return client.get().uri("/posts/" + userId)
				.retrieve()
				.bodyToMono(User.class)
				.log("Items in EI");
	}
	
	
	@PostMapping("/user-create")
	public Mono<User> createUser(@RequestBody User user) {
		return client.post()
				.uri("/posts")
				.body(Mono.just(user),User.class)
				.retrieve()
				.bodyToMono(User.class);
	}

	
	@PutMapping("users/update")
	public Mono<User> update(@PathVariable int id,@RequestBody User e) 
	{
	    return client.put()
	        .uri("/posts/" + e.getId())
	        .body(Mono.just(e), User.class)
	        .retrieve()
	        .bodyToMono(User.class);
	}
	
}
