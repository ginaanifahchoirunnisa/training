package com.tujuhsembilan.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTalent {
    private String talentName;

    private Integer newTalentId;
    private String message;
}
