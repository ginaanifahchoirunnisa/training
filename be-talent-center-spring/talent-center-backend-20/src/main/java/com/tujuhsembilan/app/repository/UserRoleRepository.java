package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.UserRole;
import com.tujuhsembilan.app.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
