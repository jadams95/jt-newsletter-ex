package com.jadams.jtnewsletterex.dao;

import com.jadams.jtnewsletterex.domain.Product;
import com.jadams.jtnewsletterex.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Long> {
}
