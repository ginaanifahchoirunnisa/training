package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee_status")
public class EmployeeStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_status_id")
    private  Integer employeeStatusId;

    @OneToMany(mappedBy = "employeeStatus", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Talent> talents;

    @Column(name = "employee_status_name")
    private String employeeStatusName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;
}
