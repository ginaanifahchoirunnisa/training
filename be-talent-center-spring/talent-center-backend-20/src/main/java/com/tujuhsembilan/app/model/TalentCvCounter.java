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
@Table(name = "talent_cv_counter")
public class TalentCvCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "talent_cv_counter_id")
    private Integer talentCvCounterId;

    @ManyToOne
    @JoinColumn(name = "talent_id")
    private Talent talent;

    @Column(name = "counter")
    private Integer counter;

    @Column(name = "created_by")
    private  String createdBy;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;

}
