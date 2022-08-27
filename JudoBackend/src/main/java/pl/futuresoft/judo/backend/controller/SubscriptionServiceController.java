package pl.futuresoft.judo.backend.controller;

import pl.futuresoft.judo.backend.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class SubscriptionServiceController {

	@Autowired
    SubscriptionService subscriptionService;

	@PostMapping("/subscription")
    public ResponseEntity<Void> addSubscription(@PathVariable int clubId) {
		throw new RuntimeException("Not implemented yet");
	}
}
