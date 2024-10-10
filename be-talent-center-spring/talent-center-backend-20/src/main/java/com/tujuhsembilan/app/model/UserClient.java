package com.tujuhsembilan.app.model;

import java.util.Date;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_client", schema = "public")
public class UserClient {
    @EmbeddedId
    private UserClientId userClientId;

    @ManyToOne
    @MapsId("client_id")
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

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

}
