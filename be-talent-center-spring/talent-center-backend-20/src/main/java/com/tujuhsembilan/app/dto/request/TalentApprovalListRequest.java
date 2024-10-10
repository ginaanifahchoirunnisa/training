package com.tujuhsembilan.app.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TalentApprovalListRequest {
    private String filter;
    private String sortBy;

}
