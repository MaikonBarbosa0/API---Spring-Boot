package com.user.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceConsole implements EmailService {
    private static final Logger log =
            LoggerFactory.getLogger(EmailServiceConsole.class);

    @Override
    public void enviarLinkReset(String email, String link) {
        log.info("Reset de senha para {}: {}", email, link);
    }

}
