package com.tujuhsembilan.app.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
@EntityListeners(AuditingEntityListener.class)
public class Client {
    @JsonIgnore
    private static final Long serialVersionUID = 1L;

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "sex")
    private Character sex;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "email", length = 100)
    @Size(min = 8, max = 100)
    private String email;

    @Column(name = "agency_name", length = 100)
    private String agencyName;

    @Column(name = "agency_address", length = 100)
    private String agencyAddress;

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
    private String lastModifiedBy;

    @Column(name = "last_modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedTime;

    @ManyToOne
    @MapsId("client_position_id")
    @JoinColumn(name = "client_position_id")
    private ClientPosition clientPosition;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<UserClient> userClients;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TalentWishlist> talentWishlists;

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
