package com.d108.project.cache.redisEmail;

import com.d108.project.cache.redisEmail.dto.EmailAuthCheckDto;
import com.d108.project.cache.redisEmail.dto.MailSendRequestDto;
import jakarta.mail.MessagingException;

public interface RedisEmailService {
    Boolean checkAuthCode(EmailAuthCheckDto emailAuthCheckDto);

    void sendEmail(String email) throws MessagingException;
}
