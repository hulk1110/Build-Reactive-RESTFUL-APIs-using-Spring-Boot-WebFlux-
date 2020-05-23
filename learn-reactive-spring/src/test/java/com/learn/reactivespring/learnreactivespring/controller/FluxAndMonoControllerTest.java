package com.learn.reactivespring.learnreactivespring.controller;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@Test
	public void testReturnFlux() {
		Flux<Integer> fluxTest = webTestClient.get().uri("/flux").accept(MediaType.APPLICATION_JSON_UTF8).exchange()
				.expectStatus().isOk().returnResult(Integer.class).getResponseBody();

		StepVerifier.create(fluxTest).expectSubscription()

				.expectNext(1, 2, 3, 4).verifyComplete();
	}
//failing
	@Test
	public void testReturnFluxBetterWay() {
		List<Integer> expectedList = Arrays.asList(1,2,3,4);

		EntityExchangeResult<List<Integer>> eniEntityExchangeResult = webTestClient.get().uri("/flux")
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectBodyList(Integer.class)
				.returnResult();

		assertEquals(expectedList, eniEntityExchangeResult);

	}
//failing
	@Test
	public void testFluxStream() {
		webTestClient.get().uri("/fluxStream").accept(MediaType.APPLICATION_STREAM_JSON).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_STREAM_JSON_VALUE).expectBodyList(Integer.class).hasSize(4);
	}

	//failing
	@Test
	public void testFluxStreamBetterWay() {
		List<Integer> expectedList = Arrays.asList(1,2,3,4);

		EntityExchangeResult<List<Integer>> eniEntityExchangeResult = webTestClient.get().uri("/flux")
				.accept(MediaType.APPLICATION_STREAM_JSON).exchange().expectStatus().isOk().
				expectBodyList(Integer.class)
				.consumeWith((response)->{
					assertEquals(expectedList, response.getResponseBody());
				});
	}
	
	
	//passing
	@Test
	public void testInfiniteFlux() {
		Flux<Integer> fluxTest = webTestClient.get().uri("/fluxInfiniteStream").accept(MediaType.APPLICATION_STREAM_JSON).exchange()
				.expectStatus().isOk().returnResult(Integer.class).getResponseBody();

		StepVerifier.create(fluxTest).expectSubscription()

				.expectNext(0, 1, 2, 3).thenCancel().verify();
	}
	
	
	@Test
	public void testMonoy() {
	Integer expectedValue = new Integer(1);

	webTestClient.get().uri("/mono").
	accept(MediaType.APPLICATION_JSON_UTF8)
	.exchange()
	.expectStatus().isOk()
	.expectBody(Integer.class)
	.consumeWith((response)->
	assertEquals(expectedValue,response.getResponseBody()));

	}
	
}
