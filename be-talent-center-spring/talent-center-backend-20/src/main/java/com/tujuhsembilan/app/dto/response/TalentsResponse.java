package com.tujuhsembilan.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalentsResponse {

    private Integer talentId;

    private String talentName;
    private String employeeStatus;

    private Short talentExperience;

    private String talentLevel;

    private String talentStatus;

    private List<PositionResponse> position;

    private List<SkillsetsResponse> skillset;









}
