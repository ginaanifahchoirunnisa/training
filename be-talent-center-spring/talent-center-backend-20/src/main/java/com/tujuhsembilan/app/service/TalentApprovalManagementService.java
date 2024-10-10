package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.request.UpdateTalentApprovalRequest;
import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.model.*;
import com.tujuhsembilan.app.repository.*;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TalentApprovalManagementService {


        @Autowired
        private TalentRepository talentRepository;

        @Autowired
        private TalentLevelRepository talentLevelRepository;

        @Autowired
        private TalentStatusRepository talentStatusRepository;

        @Autowired
        private TalentRequestRepository talentRequestRepository;

        @Autowired
        private EmployeeStatusRepository employeeStatusRepository;

        @Autowired
        private ClientRepository clientRepository;

        @Autowired
        private ClientPositionRepository clientPositionRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private UserClientRepository userClientRepository;

        @Autowired
        private UserRoleRepository userRoleRepository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private TalentWishlistRepository talentWishlistRepository;

        @Autowired
        private TalentRequestStatusRepository talentRequestStatusRepository;

        @Autowired
        private PositionRepository positionRepository;
        @Autowired
        private SkillsetRepository skillsetRepository;



        /*GET DISPLAY DAFTAR PERSETUJUAN TALENT*/
        public Page<TalentApprovalListResponse> getDataApprovalTalentList(String filterBy, String filterRequest, String sortBy, int page, int size) {
            filterRequest = (filterRequest != null)?filterRequest.toLowerCase():null;
            filterBy = (filterBy != null)?filterBy.toLowerCase():null;
            sortBy = (sortBy != null)?sortBy.toLowerCase():"desc";
            Pageable pageable ;
            Page <TalentRequest> talentRequests;
//
            List<TalentApprovalListResponse> talentApprovalListResponseList = new ArrayList<>();
            List<TalentRequest> talentRequestList;
            Sort sort;
            if (sortBy == "asc") {
                sort =  Sort.by(Sort.Direction.ASC, "requestDate");
            } else {
                sort = Sort.by(Sort.Direction.DESC, "requestDate");
            }
            if(filterBy == null || filterRequest == null){
                pageable = PageRequest.of(page,size);
          //      talentRequests = talentRequestRepository.findAll(sort);
                talentRequests = talentRequestRepository.findAllByOrderByTalentRequestIdAsc(pageable);
                System.out.println("disini");
                List<TalentApprovalListResponse> talentsApprovalResponses = talentRequests.getContent().stream().map(this::getSingleTalentRequestResponse).collect(Collectors.toList());
                return  new PageImpl<>(talentsApprovalResponses, pageable, talentRequests.getTotalElements());

            }else{
                return null;
            }
//            if (filterBy.equals("status")) {
//                TalentRequestStatus talentRequestStatus = talentRequestStatusRepository.findByTalentRequestStatusNameIgnoreCase(filterRequest);
//                if (talentRequestStatus == null) {
//                    return null;
//                }
//                talentRequestList = talentRequestRepository.findByTalentRequestStatusOrderByRequestDate(talentRequestStatus, sort);
//                talentRequestList.forEach(talentRequest -> {
//                    TalentApprovalListResponse talentApprovalListResponse = new TalentApprovalListResponse();
//                    talentApprovalListResponse.setTalentRequestId(talentRequest.getTalentRequestId());
//                    talentApprovalListResponse.setTalentName(talentRequest.getTalentWishList().getTalent().getTalentName());
//                    talentApprovalListResponse.setRequestDate(talentRequest.getRequestDate());
//                    talentApprovalListResponse.setApprovalStatus(talentRequest.getTalentRequestStatus()==null?" ":talentRequest.getTalentRequestStatus().getTalentRequestStatusName());
//                    talentApprovalListResponse.setAgencyName(talentRequest.getTalentWishList().getClient() == null? "-" : talentRequest.getTalentWishList().getClient().getAgencyName());
//                    talentApprovalListResponseList.add(talentApprovalListResponse);
//                });
//            }
//            else if (filterBy.equals("talent")){
//                List<Talent> talentList = talentRepository.findByTalentNameContainingIgnoreCase(filterRequest);
//                if(talentList == null){
//                    return null;
//                }
//                talentList.forEach(talent -> {
//                    List<TalentWishlist> talentWishlists = talentWishlistRepository.findAllByTalent(talent);
//                    talentWishlists.forEach(talentWishlist -> {
//                        List<TalentRequest> talentRequests = talentRequestRepository.findAllByTalentWishListOrderByRequestDate(talentWishlist, sort);
//                        talentRequests.forEach(talentRequest -> {
//                            TalentApprovalListResponse talentApprovalListResponse = new TalentApprovalListResponse();
//                            talentApprovalListResponse.setTalentRequestId(talentRequest.getTalentRequestId());
//                            talentApprovalListResponse.setTalentName(talentRequest.getTalentWishList().getTalent().getTalentName());
//                            talentApprovalListResponse.setRequestDate(talentRequest.getRequestDate());
//                            talentApprovalListResponse.setApprovalStatus(talentRequest.getTalentRequestStatus()==null?" ":talentRequest.getTalentRequestStatus().getTalentRequestStatusName());
//                            talentApprovalListResponse.setAgencyName(talentRequest.getTalentWishList().getClient() == null? "-" : talentRequest.getTalentWishList().getClient().getAgencyName());
//                            talentApprovalListResponseList.add(talentApprovalListResponse);
//
//                        });
//
//                    });
//                });
//            }else if(filterBy.equals("instansi")){
//                List<Client> clients = clientRepository.findAllByAgencyNameContainingIgnoreCase(filterRequest);
//                if(clients == null){
//                    return null;
//                }
//                clients.forEach(client -> {
//                    System.out.println(client.getClientName());
//                    List<TalentWishlist> talentWishlists = talentWishlistRepository.findAllByClient(client);
//                    talentWishlists.forEach(talentWishlist -> {
//                        List<TalentRequest> talentRequests = talentRequestRepository.findAllByTalentWishListOrderByRequestDate(talentWishlist,sort);
//                        talentRequests.forEach(talentRequest -> {
//                            TalentApprovalListResponse talentApprovalListResponse = new TalentApprovalListResponse();
//                            talentApprovalListResponse.setTalentRequestId(talentRequest.getTalentRequestId());
//                            talentApprovalListResponse.setTalentName(talentRequest.getTalentWishList().getTalent().getTalentName());
//                            talentApprovalListResponse.setRequestDate(talentRequest.getRequestDate());
//                            talentApprovalListResponse.setApprovalStatus(talentRequest.getTalentRequestStatus()==null?" ":talentRequest.getTalentRequestStatus().getTalentRequestStatusName());
//                            talentApprovalListResponse.setAgencyName(talentRequest.getTalentWishList().getClient() == null? "-" : talentRequest.getTalentWishList().getClient().getAgencyName());
//                            talentApprovalListResponseList.add(talentApprovalListResponse);
//                        });
//                    });
//                });
//            }
//            return talentApprovalListResponseList;

        }

        public TalentApprovalListResponse getSingleTalentRequestResponse (TalentRequest talentRequest) {
            TalentApprovalListResponse talentApprovalResponse = new TalentApprovalListResponse();
            talentApprovalResponse.setTalentRequestId(talentRequest.getTalentRequestId());
            talentApprovalResponse.setTalentName(talentRequest.getTalentWishList().getTalent().getTalentName());
            talentApprovalResponse.setRequestDate(talentRequest.getRequestDate());
            talentApprovalResponse.setApprovalStatus(talentRequest.getTalentRequestStatus()==null?" ":talentRequest.getTalentRequestStatus().getTalentRequestStatusName());
            talentApprovalResponse.setAgencyName(talentRequest.getTalentWishList().getClient() == null? "-" : talentRequest.getTalentWishList().getClient().getAgencyName());
            return talentApprovalResponse;
        }

        public UpdateTalentApprovalResponse putApprovePersetujuanTalent(Integer talentRequestId, UpdateTalentApprovalRequest updateTalentApprovalRequest){
            TalentRequest updatedTalentRequest = new TalentRequest();
            System.out.println(updateTalentApprovalRequest.getAction());
            TalentRequest talentRequest = talentRequestRepository.findById(updateTalentApprovalRequest.getTalentRequestId()).orElseThrow(() -> new RuntimeException("Not Found"));
            /* GET TALENT_REQUEST_STATUS DATA*/
            TalentRequestStatus talentRequestStatus = talentRequestStatusRepository.findByTalentRequestStatusNameIgnoreCase(updateTalentApprovalRequest.getAction());
            talentRequest.setRequestRejectReason(updateTalentApprovalRequest.getRejectReason() != null? updateTalentApprovalRequest.getRejectReason() : " ");
            talentRequest.setTalentRequestStatus(talentRequestStatus);
            updatedTalentRequest = talentRequestRepository.save(talentRequest);
            UpdateTalentApprovalResponse updateTalentApprovalResponse = new UpdateTalentApprovalResponse();
            updateTalentApprovalResponse.setRequestId(updatedTalentRequest.getTalentRequestId());
            updateTalentApprovalResponse.setStatus(updateTalentApprovalRequest.getAction());
            return updateTalentApprovalResponse;
        }



}

