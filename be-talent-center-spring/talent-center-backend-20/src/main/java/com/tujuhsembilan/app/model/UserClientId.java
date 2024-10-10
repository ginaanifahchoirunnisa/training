package com.tujuhsembilan.app.model;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserClientId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "client_id")
    private Integer clientId;
}
