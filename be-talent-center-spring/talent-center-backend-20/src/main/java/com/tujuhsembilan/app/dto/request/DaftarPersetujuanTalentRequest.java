package com.tujuhsembilan.app.dto.request;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaftarPersetujuanTalentRequest {
    private String filterColumn;

    private String filterContent;


}
