package fluxandmonoplayground;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoCombileTest {
	
	@Test
	public void combineUsingMerge() {
		Flux<String> flux1=Flux.just("Nishant","Sukhi","Baba");
		Flux<String> flux2=Flux.just("MOnty","Kinshuk","MIntu");
		
		Flux<String> merged=Flux.merge(flux1,flux2);
		
		StepVerifier.create(merged.log())
		.expectNext("Nishant","Sukhi","Baba","MOnty","Kinshuk","MIntu")
		.verifyComplete();
		
		
	}
	
	
	@Test
	public void combineUsingMerge_withDelay_no_order() {
		Flux<String> flux1=Flux.just("Nishant","Sukhi","Baba").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2=Flux.just("MOnty","Kinshuk","MIntu").delayElements(Duration.ofSeconds(1));
		
		Flux<String> merged=Flux.merge(flux1,flux2);
		
		StepVerifier.create(merged.log())
		.expectNextCount(6)
		.verifyComplete();
		
		
	}
	
	@Test
	public void combineUsingMerge_withDelay_no_orderMaintiained() {
		Flux<String> flux1=Flux.just("Nishant","Sukhi","Baba").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2=Flux.just("MOnty","Kinshuk","MIntu").delayElements(Duration.ofSeconds(1));
		
		Flux<String> merged=Flux.concat(flux1,flux2);
		
		StepVerifier.create(merged.log())
		.expectNext("Nishant","Sukhi","Baba","MOnty","Kinshuk","MIntu")
		.verifyComplete();
		
		
	}
	
	@Test
	public void combineUsingMerge_withDelay_zip() {
		Flux<String> flux1=Flux.just("Nishant","Sukhi","Baba").delayElements(Duration.ofSeconds(1));
		Flux<String> flux2=Flux.just("MOnty","Kinshuk","MIntu").delayElements(Duration.ofSeconds(1));
		
		Flux<String> merged=Flux.zip(flux1, flux2,(t1,t2)->{ return t1.concat(t2);});
		
		StepVerifier.create(merged.log())
		.expectNext("NishantMOnty","SukhiKinshuk","BabaMIntu")
		.verifyComplete();
		
		
	}

}
