package com.tujuhsembilan.app.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_position", schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class ClientPosition {
    @JsonIgnore
    private static final Long serialVersionUID = 1L;

    @Id
    @Column(name = "client_position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientPositionId;

    @Column(name = "client_position_name", length = 100)
    private String clientPositionName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @Column(name = "created_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedNy;

    @Column(name = "last_modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedTime;


}
