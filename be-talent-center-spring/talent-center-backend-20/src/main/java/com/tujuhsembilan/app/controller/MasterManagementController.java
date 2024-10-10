package com.tujuhsembilan.app.controller;


import com.tujuhsembilan.app.service.MasterManagementService;
import com.tujuhsembilan.app.service.TalentManagementService;
import com.tujuhsembilan.app.util.ResponseHandler;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/master-management")
public class MasterManagementController {

    @Autowired
    @Lazy
    private MasterManagementService masterManagementService;

    @GetMapping("/talent-level-option-lists")
    public ResponseEntity<Object> getDataMasterLevelTalent(){
        try{
            Object getLevelTalentResult = masterManagementService.getDataMasterLevelTalent();
            return ResponseHandler.generateResponse("Get data level talent is success", HttpStatus.OK, getLevelTalentResult);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employee-status-option-lists")
    public ResponseEntity<Object> getDataEmployeeStatus(){
        try{
            Object getDataEmployeeStatus = masterManagementService.getDataMasterEmployeeStatus();
            return ResponseHandler.generateResponse("Get data employee status is success", HttpStatus.OK, getDataEmployeeStatus);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/talent-position-option-lists")
    public ResponseEntity<Object> getDataPosition(){
        try{
            Object getDataPositions = masterManagementService.getDataMasterPositions();
            return ResponseHandler.generateResponse("Get data positions success", HttpStatus.OK, getDataPositions);
        }catch(Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/skill-set-option-lists")
    public ResponseEntity<Object> getDataSkillsets(){
        try{
            Object getDataSkillsets = masterManagementService.getDataMasterSkillsets();
            return ResponseHandler.generateResponse("Get data skillset is success", HttpStatus.OK, getDataSkillsets);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/talent-status-option-lists")
    public ResponseEntity<Object> getDataTalentStatus(){
        try{
            Object getDataTalentStatus = masterManagementService.getDataTalentStatus();
            return ResponseHandler.generateResponse("Get data talent status is success", HttpStatus.OK, getDataTalentStatus);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
