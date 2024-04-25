package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.MailDTPRequest;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    String send(MailDTPRequest mailDTPRequest);

    void sendOtpEmail(String email,String  otp);

}
