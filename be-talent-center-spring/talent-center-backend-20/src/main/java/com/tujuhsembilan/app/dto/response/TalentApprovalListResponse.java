package com.tujuhsembilan.app.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalentApprovalListResponse {

    private Integer talentRequestId;
    private String agencyName;
    private LocalDate requestDate;
    private String talentName;
    private String approvalStatus;

}
