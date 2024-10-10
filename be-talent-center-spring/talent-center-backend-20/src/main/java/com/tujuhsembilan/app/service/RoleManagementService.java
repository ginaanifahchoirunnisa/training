package com.tujuhsembilan.app.service;


import com.tujuhsembilan.app.dto.request.RoleRequest;
import com.tujuhsembilan.app.dto.request.RoleRequestEdit;
import com.tujuhsembilan.app.dto.response.RoleResponse;
import com.tujuhsembilan.app.model.Role;
import com.tujuhsembilan.app.repository.RoleRepository;
import com.tujuhsembilan.app.util.ResponseMessages;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.tujuhsembilan.app.util.ResponseMessages.DATA_ROLE_NOT_FOUND;

@Service
public class RoleManagementService {

    @Autowired
    private RoleRepository roleRepository;


    public List<RoleResponse> getAllRoles(){
        List<Role> roleList = roleRepository.findAll();
        List<RoleResponse> roleResponseList = new ArrayList<>();

        for (Role role : roleList) {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setRoleId(role.getRoleId());
            roleResponse.setRoleName(role.getRoleName()==null?null:role.getRoleName());
            roleResponseList.add(roleResponse);
        }
        return roleResponseList;
    }

    public RoleResponse saveDataRole(RoleRequest roleRequest){
        Role newRole = new Role();
        RoleResponse roleResponse = new RoleResponse();
        newRole.setRoleName(roleRequest.getRoleName());
        newRole = roleRepository.save(newRole);
        roleResponse.setRoleId(newRole.getRoleId());
        roleResponse.setRoleName(newRole.getRoleName());
        return roleResponse;
    }

    public RoleResponse editDataTalent(RoleRequestEdit roleRequestEdit){
        RoleResponse roleResponse = new RoleResponse();
        Role currRole = roleRepository.findById(roleRequestEdit.getRoleId()).orElseThrow(()-> new EntityNotFoundException(String.format(ResponseMessages.DATA_TALENT_NOT_FOUND)));
        currRole.setRoleName(roleRequestEdit.getRoleName());
        currRole = roleRepository.save(currRole);
        roleResponse.setRoleId(currRole.getRoleId());
        roleResponse.setRoleName(currRole.getRoleName());
        return roleResponse;
    }

    public String deleteDataRole(Integer roleId){
        roleRepository.findById(roleId).orElseThrow(()-> new EntityNotFoundException(String.format(ResponseMessages.DATA_ROLE_NOT_FOUND)));
        roleRepository.deleteById(roleId);
        return "";
    }

}
