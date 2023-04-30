package com.jadams.jtnewsletterex.dao;

import com.jadams.jtnewsletterex.domain.Email;

public interface EmailManager {
    void sendEmail(Email email);
}
