package fluxandmonoplayground;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Test;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FLuxAndMonoFactoryMethodTest {

	List<String> names = Arrays.asList("nish", "sukhi", "baba");

	@Test
	public void fluxUsingIterable() {

		Flux<String> namesFlux = Flux.fromIterable(names);

		StepVerifier.create(namesFlux)
		.expectNext("nish", "sukhi", "baba")
		.verifyComplete();

	}

	@Test
	public void fluxUsingArray() {

		String[] names = new String[] { "nish", "sukhi", "baba" };

		StepVerifier.create(Flux.fromArray(names))
		.expectNext("nish", "sukhi", "baba")
		.verifyComplete();

	}

	@Test
	public void fluxUsingStream() {

		Flux<String> namesFlux = Flux.fromStream(names.stream());
		String[] names = new String[] { "nish", "sukhi", "baba" };

		StepVerifier.create(Flux.fromArray(names)).
		expectNext("nish", "sukhi", "baba")
		.verifyComplete();

	}
	
	@Test
	public void fluxUsingRange() {

		Flux<Integer> integerFlux = Flux.range(1, 5).log();
		

		StepVerifier.create(integerFlux).
		expectNextCount(5)
		.verifyComplete();

	}
	
	@Test
	public void monoUsingJustOrEmpty() {

		Mono<String> mono = Mono.justOrEmpty(null);
		

		StepVerifier.create(mono.log())
		.verifyComplete();

	}
	
	
	@Test
	public void monoUsingSupplier() {

		Supplier<String> supplier= ()->"Nishant";
		Mono<String> mono = Mono.fromSupplier(supplier);
		StepVerifier.create(mono.log())
		.expectNext("Nishant")
		.verifyComplete();

	}
}
