package com.tujuhsembilan.app.dto;

import java.util.List;

import com.tujuhsembilan.app.dto.response.PositionResponse;
import com.tujuhsembilan.app.dto.response.SkillsetsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTalentResponsDTO {
    private Integer talentId;
    private String talentName;
    private String employeeStatus;
    private Boolean talentAvailability;
    private Short talentExperience;
    private String talentLevel;
    private Boolean isAddToListEnable;
    private List<PositionResponse> position;
    private List<SkillsetsResponse> skillset;
}
