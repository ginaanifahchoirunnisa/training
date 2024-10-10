package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "talent_skillset")
public class TalentSkillset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talent_skillset_id")
    private Integer talentSkillsetId;

    @ManyToOne
    @JoinColumn(name = "skillset_id")
    private Skillset skillset;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;

}
