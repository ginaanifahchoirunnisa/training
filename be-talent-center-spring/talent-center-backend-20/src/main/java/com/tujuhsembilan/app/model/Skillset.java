package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skillset", schema = "public")
public class Skillset {
    @Id
    @Column(name = "skillset_id")
    private Integer skillsetId;

    @ManyToOne
    @JoinColumn(name = "skillset_type_id")
    private SkillsetType skillsetType;

    @OneToMany(mappedBy = "skillset", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MostFrequentSkillset> mostFrequentSkillsets;

    @OneToMany(mappedBy = "skillset", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TalentSkillset> talentSkillsets;

    @Column(name = "skillset_name")
    private String skillsetName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;


    @Column(name = "last_modified_by")
    private String lastModifiedBy;


    @Column(name = "last_modified_time")
    private Date lastModifiedTime;









}
