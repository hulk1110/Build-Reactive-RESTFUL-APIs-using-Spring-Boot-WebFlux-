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
public class FisConsumerController {

	@Qualifier("fisapp")
	@Autowired
	WebClient client;

	@GetMapping("/books")
	public Flux<Book> getMeUserExchange() {

		return client.get().uri("/Books").
				retrieve().
				onStatus(HttpStatus::is5xxServerError, serverError -> {
					Mono<String> errorMono = serverError.bodyToMono(String.class);
					return errorMono.flatMap((errMsg) -> {
						
						throw new RuntimeException(errMsg);
					});
				       })

				.onStatus(HttpStatus::is4xxClientError, clientError -> {
					Mono<String> errorMono = clientError.bodyToMono(String.class);
					return errorMono.flatMap((errMsg) -> {
						// log.error("The error message is: " +errMsg);
						throw new RuntimeException(errMsg);
					});
				})

				.bodyToFlux(Book.class).log("Items in Fis");
	}

	@GetMapping("/Books/{id}")
	public Mono<Book> getMeUserBasedOnId(@PathVariable int id) {

		return client.get().uri("/Books/" + id).retrieve().bodyToMono(Book.class).log("Items in EI");
	}

	@PostMapping("/user-book")
	public Mono<Book> createBook(@RequestBody Book book) {
		return client.post().uri("/Books").body(Mono.just(book), Book.class).retrieve().bodyToMono(Book.class);
	}

	@PutMapping("Books/update/{id}")
	public Mono<Book> update(@PathVariable int id, @RequestBody Book book) {
		return client.put().uri("/Books/" + id).body(Mono.just(book), Book.class).retrieve().bodyToMono(Book.class);
	}

}
