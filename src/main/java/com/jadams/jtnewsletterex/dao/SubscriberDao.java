package com.jadams.jtnewsletterex.dao;

import com.jadams.jtnewsletterex.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberDao extends JpaRepository<Subscriber, Long> {
}
