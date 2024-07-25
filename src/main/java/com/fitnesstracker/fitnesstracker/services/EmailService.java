package com.fitnesstracker.fitnesstracker.services;

public interface EmailService {

    void sendHtmlMessage(String to, String subject, String html);
}
