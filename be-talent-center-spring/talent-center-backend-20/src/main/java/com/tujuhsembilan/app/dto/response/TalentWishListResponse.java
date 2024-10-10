package com.tujuhsembilan.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalentWishListResponse {
    private Integer wishListId;
    private Integer talentId;
    private String talentName;
    private String employeeStatus;

    private Short talentExperience;

    private String talentLevel;

    private List<PositionResponse> position;

    private List<SkillsetsResponse> skillset;





}
