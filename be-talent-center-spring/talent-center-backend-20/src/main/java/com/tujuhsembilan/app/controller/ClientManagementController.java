package com.tujuhsembilan.app.controller;


import com.tujuhsembilan.app.service.ClientManagementService;
import com.tujuhsembilan.app.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tujuhsembilan.app.util.ResponseMessages.GET_ALL_CLIENTS_IS_FAIL;
import static com.tujuhsembilan.app.util.ResponseMessages.GET_ALL_CLIENTS_IS_SUCCESS;

@Controller
@RestController
@RequestMapping("/client-management")
public class ClientManagementController {
    @Autowired
    private ClientManagementService clientManagementService;

    @GetMapping("/clients")
    public ResponseEntity<?> getAllClients(){
        try{
            return ResponseHandler.generateResponse(GET_ALL_CLIENTS_IS_SUCCESS, HttpStatus.OK, clientManagementService.getAllDataClients());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(GET_ALL_CLIENTS_IS_FAIL);
        }
    }
}
