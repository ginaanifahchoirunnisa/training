package com.tujuhsembilan.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "most_frequent_skillset")
public class  MostFrequentSkillset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "most_frequent_skillset_id")
    private Integer mostFrequentSkillsetId;

    @ManyToOne
    @JoinColumn(name = "skillset_id")
    private Skillset skillset;

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
