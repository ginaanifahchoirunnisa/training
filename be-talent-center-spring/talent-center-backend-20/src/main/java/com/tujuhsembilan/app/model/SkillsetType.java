package com.tujuhsembilan.app.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skillset_type")
public class SkillsetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skillset_type_id")
    private Integer skillsetTypeId;

    @OneToMany(mappedBy = "skillsetType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Skillset> skillsets;

    @Column(name = "skillset_type_name")
    private String skillseTypeName;

    @Column(name = "is_programming_skill")
    private Boolean isProgrammingSkill;

    @Column(name = "is_active")
    private  Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;
}
