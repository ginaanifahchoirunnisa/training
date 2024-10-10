package com.tujuhsembilan.app.dto.response;


import com.tujuhsembilan.app.model.TalentCvCounter;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class DetailTalentResponse {
    private Integer talentId;
    private String talentPhoto;
    private String talentName;

    private String talentStatus;
    private Integer talentStatusId;
    private String nip;
    private char sex;
    private LocalDate dob;
    private String talentDescription;
    private String cv;
    private Short talentExperience;
    private String talentLevel;
    private Integer talentLevelId;
    private Short projectCompleted;
    private Long totalRequested;
    private List<PositionResponse> position;
    private List<SkillsetsResponse> skillSet;
    private String email;
    private String cellphone;
    private String employeeStatus;
    private Integer employeeStatusId;
    private String videoUrl;
}
