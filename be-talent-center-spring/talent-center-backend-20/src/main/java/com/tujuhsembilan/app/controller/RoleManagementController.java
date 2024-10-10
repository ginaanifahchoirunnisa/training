package com.tujuhsembilan.app.controller;

import com.tujuhsembilan.app.dto.request.RoleRequest;
import com.tujuhsembilan.app.dto.request.RoleRequestEdit;
import com.tujuhsembilan.app.service.RoleManagementService;
import com.tujuhsembilan.app.util.ResponseHandler;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.tujuhsembilan.app.util.ResponseMessages.*;

@Controller
@RestController
@RequestMapping("/role-management")
public class RoleManagementController {
    @Autowired
    private  RoleManagementService roleManagementService;

    @GetMapping("/roles")
    public ResponseEntity<?> GetAllDataRoles(){
        try{
            return ResponseHandler.generateResponse(GET_DATA_ROLES_IS_SUCCESS, HttpStatus.OK, roleManagementService.getAllRoles());
        }catch(Exception e){
            return ResponseEntity.badRequest().body(GET_DATA_ROLES_IS_FAIL);
        }
    }


    @PostMapping("/roles")
    public ResponseEntity<?> saveNewDataRole(@RequestBody RoleRequest roleRequest){
        try{
            return ResponseHandler.generateResponse(SAVE_DATA_ROLE_IS_SUCCESS, HttpStatus.OK, roleManagementService.saveDataRole(roleRequest));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(SAVE_DATA_ROLE_IS_FAILED);
        }
    }

    @PutMapping("/roles")
    public ResponseEntity<?> saveEditDataRole(@RequestBody RoleRequestEdit roleRequestEdit){
        try{
            return ResponseHandler.generateResponse(EDIT_ROLE_IS_SUCCESS, HttpStatus.OK, roleManagementService.editDataTalent(roleRequestEdit));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(EDIT_ROLE_IS_FAIL);
        }
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<?> deleteDataRole(@PathVariable Integer roleId){
        try{
            return ResponseHandler.generateResponse(DELETE_ROLE_IS_SUCCESS, HttpStatus.OK, roleManagementService.deleteDataRole(roleId));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(DELETE_ROLE_IS_FAIL);
        }
    }
}
