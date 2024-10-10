package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByEmail(String email);

    List<Client> findAllByAgencyNameContainingIgnoreCase(String agencyName);

    Client findByClientId(Integer clientId);

    //aList<Client> findAllByAgencyNameContainingIgnoreCase(String agencyName);

}
