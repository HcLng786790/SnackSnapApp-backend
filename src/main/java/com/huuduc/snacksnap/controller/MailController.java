package com.huuduc.snacksnap.controller;

import com.huuduc.snacksnap.data.dto.MailDTPRequest;
import com.huuduc.snacksnap.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Mail Controller
 *
 * @author huuduc
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;


    @PostMapping("/send")
    public  String send(@RequestBody MailDTPRequest mailDTPRequest){

        return this.mailService.send(mailDTPRequest);
    }
}
