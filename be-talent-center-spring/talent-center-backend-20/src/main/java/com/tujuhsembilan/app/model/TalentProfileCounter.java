package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "talent_profile_counter")
public class TalentProfileCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talent_profile_counter_id")
    private Integer talentProfileCounterId;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @Column(name = "counter")
    private Integer counter;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;
}
