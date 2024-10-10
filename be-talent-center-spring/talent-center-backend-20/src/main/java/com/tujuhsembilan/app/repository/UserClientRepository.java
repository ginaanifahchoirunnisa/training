package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.UserClient;
import com.tujuhsembilan.app.model.UserClientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClientRepository extends JpaRepository<UserClient, Integer> {

}
