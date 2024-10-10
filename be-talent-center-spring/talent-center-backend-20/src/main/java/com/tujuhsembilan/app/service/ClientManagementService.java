package com.tujuhsembilan.app.service;


import com.tujuhsembilan.app.dto.response.GetClientResponse;
import com.tujuhsembilan.app.model.Client;
import com.tujuhsembilan.app.repository.ClientPositionRepository;
import com.tujuhsembilan.app.repository.ClientRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClientManagementService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientPositionRepository clientPositionRepository;

    @Autowired
    private PositionRepository positionRepository;


    @Transactional
    public List<GetClientResponse> getAllDataClients(){
        List<Client> clientList = clientRepository.findAll();
        List<GetClientResponse> getClientResponseList = new ArrayList<>();
        clientList.forEach(client -> {
            GetClientResponse getClientResponse = new GetClientResponse();
            getClientResponse.setClientId(client.getClientId());
            getClientResponse.setClientName(client.getClientName());
            getClientResponse.setJoinYear(1);
            getClientResponse.setAgencyName(client.getAgencyName());
            getClientResponse.setPosition(client.getClientPosition()==null?"-":client.getClientPosition().getClientPositionName());
            getClientResponseList.add(getClientResponse);
        });
    return getClientResponseList;
    }
}
