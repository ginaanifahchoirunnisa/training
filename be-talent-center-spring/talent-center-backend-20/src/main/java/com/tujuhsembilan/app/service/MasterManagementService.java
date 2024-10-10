package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.model.*;
import com.tujuhsembilan.app.repository.*;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MasterManagementService {


    @Autowired
    private TalentRepository talentRepository;

    @Autowired
    private TalentLevelRepository talentLevelRepository;

    @Autowired
    private TalentStatusRepository talentStatusRepository;

    @Autowired
    private TalentRequestRepository talentRequestRepository;

    @Autowired
    private EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    @Lazy
    private ClientPositionRepository clientPositionRepository;

    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private UserClientRepository userClientRepository;

    @Autowired
    @Lazy
    private UserRoleRepository userRoleRepository;

    @Autowired
    @Lazy
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
    private TalentWishlistRepository talentWishlistRepository;

    @Autowired
    @Lazy
    private TalentRequestStatusRepository talentRequestStatusRepository;

    @Autowired
    @Lazy
    private PositionRepository positionRepository;
    @Autowired
    @Lazy
    private SkillsetRepository skillsetRepository;

    @Autowired
    @Lazy
    private TalentPositionRepository talentPositionRepository;

    @Autowired
    @Lazy
    private TalentSkillsetRepository talentSkillsetRepository;


    public List<TalentLevelResponse> getDataMasterLevelTalent() {
        List<TalentLevel> talentLevelList = talentLevelRepository.findAll();
        List<TalentLevelResponse> talentLevelResponseList = new ArrayList<>();
        talentLevelList.forEach(talentLevel -> {
            TalentLevelResponse talentLevelResponse = new TalentLevelResponse();
            talentLevelResponse.setTalentLevelId(talentLevel.getTalentLevelId());
            talentLevelResponse.setTalentLevelName(talentLevel.getTalentLevelName());
            talentLevelResponseList.add(talentLevelResponse);
        });

        return talentLevelResponseList;

    }


    public List<EmployeeStatusResponse> getDataMasterEmployeeStatus() {
        List<EmployeeStatusResponse> employeeStatusResponseList = new ArrayList<>();
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findAll();

        employeeStatusList.forEach(employeeStatus -> {
            EmployeeStatusResponse employeeStatusResponse = new EmployeeStatusResponse();
            employeeStatusResponse.setEmployeeStatusId(employeeStatus.getEmployeeStatusId());
            employeeStatusResponse.setEmployeeStatusName(employeeStatus.getEmployeeStatusName());
            employeeStatusResponseList.add(employeeStatusResponse);
        });

        return employeeStatusResponseList;

    }


    public List<PositionResponse> getDataMasterPositions() {
        List<PositionResponse> positionResponseList = new ArrayList<>();
        List<Position> positionList = positionRepository.findAll();

        positionList.forEach(position -> {
            PositionResponse positionResponse = new PositionResponse();
            positionResponse.setPositionId(position.getPositionId());
            positionResponse.setPositionName(position.getPositionName());
            positionResponseList.add(positionResponse);
        });

        return positionResponseList;

    }


    public List<SkillsetsResponse> getDataMasterSkillsets() {
        List<SkillsetsResponse> skillsetsResponseList = new ArrayList<>();
        List<Skillset> skillsetList = skillsetRepository.findAll();

        skillsetList.forEach(skillset -> {
            SkillsetsResponse skillsetsResponse = new SkillsetsResponse();
            skillsetsResponse.setSkillsetId(skillset.getSkillsetId());
            skillsetsResponse.setSkillsetName(skillset.getSkillsetName());
            skillsetsResponseList.add(skillsetsResponse);
        });

        return skillsetsResponseList;
    }

    public List<TalentStatusResponse> getDataTalentStatus(){
        List<TalentStatusResponse> talentStatusResponseList = new ArrayList<>();
        List<TalentStatus> talentStatusList= talentStatusRepository.findAll();

        talentStatusList.forEach(talentStatus -> {
            TalentStatusResponse talentStatusResponse = new TalentStatusResponse();
            talentStatusResponse.setTalentStatusId(talentStatus.getTalentStatusId());
            talentStatusResponse.setTalentStatusName(talentStatus.getTalentStatusName());
            talentStatusResponseList.add(talentStatusResponse);
        });

        return talentStatusResponseList;

    }


}
