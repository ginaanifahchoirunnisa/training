package com.tujuhsembilan.app.controller;

import com.tujuhsembilan.app.dto.request.EmailRequest;
import com.tujuhsembilan.app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody EmailRequest request) {
        try{
            emailService.sendEmail(request);
            return "Email sent successfully";
        }catch(Exception e){
            return e.getMessage();
        }

    }
}