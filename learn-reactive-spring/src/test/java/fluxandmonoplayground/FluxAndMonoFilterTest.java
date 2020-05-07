package fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoFilterTest {

	
	List<String> names = Arrays.asList("nish", "sukhi", "baba","nishant");

	@Test
	public void filterTest() {

		Flux<String> namesFlux = Flux.fromIterable(names)
				.filter(p->p.startsWith("n"));

		StepVerifier.create(namesFlux)
		.expectNext("nish", "nishant")
		.verifyComplete();

	}
	
	@Test
	public void filterTestLength() {

		Flux<String> namesFlux = Flux.fromIterable(names)
				.filter(p->p.length()>5);

		StepVerifier.create(namesFlux)
		.expectNext("nishant")
		.verifyComplete();

	}
}
