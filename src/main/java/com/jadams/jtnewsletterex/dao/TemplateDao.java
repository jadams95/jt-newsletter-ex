package com.jadams.jtnewsletterex.dao;

import com.jadams.jtnewsletterex.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateDao extends JpaRepository<Template, Long> {
}
