package com.user.api.service;

public interface EmailService {
    void enviarLinkReset(String email, String link);
}
