package com.tujuhsembilan.app.dto.request;

import lombok.Data;

@Data
public class UpdateTalentApprovalRequest {
    private Integer talentRequestId;
    private String action;
    private String rejectReason;
}
