package com.tujuhsembilan.app.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class CustomResponseAPIApproval {
    private String message;
    private List<TalentApprovalListResponse> data;

    private int statusCode;

    private Integer totalPage;
}
