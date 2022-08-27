package pl.futuresoft.judo.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionService {

	@Transactional
	public Boolean ifClubHasPaidSubscription(int clubId)  {
		throw new RuntimeException("Not implemented yet");
	}
}
