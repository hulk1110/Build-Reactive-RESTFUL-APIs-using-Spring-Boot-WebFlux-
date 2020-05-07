package fluxandmonoplayground;

import org.junit.Test;

import reactor.core.publisher.Flux;

public class FluxAndMonoTest {

	@Test
	public void fluxTest() {

		Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
				//.concatWith(Flux.error(new RuntimeException("Exception occured")))
				.concatWith(Flux.just("AFter Error"))
				.log();
		
		stringFlux.subscribe(System.out::println,
				(e)->System.err.println("Exception is" + e),
				()->System.out.println("Completed"));

	}

}