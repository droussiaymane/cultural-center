package com.ju.islamicculturalcenter.service.iservice;

import javax.mail.MessagingException;

public interface MailService {

    void sendPasswordEmail(String firstName, String emailTo, String password);

    void sendPasswordEmailWithHtml(String firstName, String emailTo, String password);
}
