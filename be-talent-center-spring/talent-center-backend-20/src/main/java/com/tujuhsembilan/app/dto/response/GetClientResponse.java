package com.tujuhsembilan.app.dto.response;

import com.tujuhsembilan.app.model.Position;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class GetClientResponse {
    private Integer clientId;
    private String clientName;
    private String agencyName;
    private String position;

    private Integer joinYear;
}
