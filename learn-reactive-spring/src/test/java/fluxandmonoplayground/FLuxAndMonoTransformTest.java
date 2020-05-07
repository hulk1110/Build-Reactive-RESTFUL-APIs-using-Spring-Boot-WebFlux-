package fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FLuxAndMonoTransformTest {

	
	List<String> names = Arrays.asList("nish", "sukhi", "baba","nishant");

	@Test
	public void transformUsingMap() {

		Flux<String> namesFlux = Flux.fromIterable(names)
				.map(f->f.toUpperCase());

		StepVerifier.create(namesFlux)
		.expectNext("NISH", "SUKHI","BABA","NISHANT")
		.verifyComplete();

	}
	
	
	
	@Test
	public void transformUsingMap_Length() {

		Flux<Integer> namesFlux = Flux.fromIterable(names)
				.map(f->f.length())
				.log();

		StepVerifier.create(namesFlux)
		.expectNext(4,5,4,7)
		.verifyComplete();

	}
	
	@Test
	public void transformUsingMap_Length_repeat() {

		Flux<Integer> namesFlux = Flux.fromIterable(names)
				.map(f->f.length())
				.repeat(1)
				.log();

		StepVerifier.create(namesFlux)
		.expectNext(4,5,4,7,4,5,4,7)
		.verifyComplete();

	}

	@Test
	public void transformUsingMap_Filter() {

		Flux<String> namesFlux = Flux.fromIterable(names)
				.filter(p->p.length()>5)
				.map(f->f.toUpperCase());

		StepVerifier.create(namesFlux)
		.expectNext("NISHANT")
		.verifyComplete();

	}

}
