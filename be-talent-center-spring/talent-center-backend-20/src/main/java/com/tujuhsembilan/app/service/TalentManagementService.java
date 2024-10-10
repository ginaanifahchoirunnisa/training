package com.tujuhsembilan.app.service;


import com.tujuhsembilan.app.dto.GetTalentResponsDTO;
import com.tujuhsembilan.app.dto.ResponseTalent;
import com.tujuhsembilan.app.dto.request.*;
import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.model.*;
import com.tujuhsembilan.app.repository.*;
import com.tujuhsembilan.app.util.ResponseMessages;
import io.minio.messages.Upload;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lib.minio.MinioSrvc;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.tujuhsembilan.app.util.ResponseMessages.DATA_TALENT_NOT_FOUND;

@Service

public class TalentManagementService implements ITalentService {
    private static final Logger logger = Logger.getLogger(TalentManagementService.class.getName());

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
    @Lazy
    private ClientPositionRepository clientPositionRepository;

    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private UserClientRepository userClientRepository;

    @Autowired
    @Lazy
    private UserRoleRepository userRoleRepository;

    @Autowired
    @Lazy
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
    private TalentWishlistRepository talentWishlistRepository;

    @Autowired
    @Lazy
    private TalentRequestStatusRepository talentRequestStatusRepository;

    @Autowired
    @Lazy
    private PositionRepository positionRepository;

    @Autowired
    @Lazy
    private SkillsetRepository skillsetRepository;

    @Autowired
    @Lazy
    private TalentPositionRepository talentPositionRepository;

    @Autowired
    @Lazy
    private TalentSkillsetRepository talentSkillsetRepository;

    @Autowired
    @Lazy
    private MinioSrvc minioSrvc;

    private final String bucketName = "talent-center-app";


    public Page<TalentsResponse> getAllTalents(Integer employeeStatusId, Integer talentStatusId, Integer talentLevelId,
                                               String sortField, String sortDirection, Integer page, Integer size){

        EmployeeStatus employeeStatus ;

        Pageable pageable = PageRequest.of(page,size);
        TalentStatus talentStatus;
        TalentLevel talentLevel;
        Page<Talent> talentList;
        System.out.println(employeeStatusId+ " = "+ talentStatusId+ " = "+ talentLevelId);
        System.out.println(sortField + " ==  " + sortDirection +"/n"+page+" dan "+ size);

        if( employeeStatusId == null){
            employeeStatus = null;
        }else if(employeeStatusId == 0 ){
            employeeStatus = null;
        }else{
            employeeStatus = employeeStatusRepository.findById(employeeStatusId).orElseThrow(()->null);
        }

        if(talentStatusId == null){
            talentStatus = null;
        }else if(talentStatusId == 0 ){
            talentStatus = null;
        }else{
            talentStatus = talentStatusRepository.findById(talentStatusId).orElseThrow(()->null);
        }

        if(talentLevelId == null){
            talentLevel = null;
        }else if(talentLevelId == 0){
            talentLevel = null;
        }else{
            talentLevel = talentLevelRepository.findById(talentLevelId).orElseThrow(()->null);
        }

//        List<TalentsResponse> talentsResponseList = new ArrayList<>();
        if(sortField != null && sortField.equals("experience")){
            if(sortDirection!=null && sortDirection.equals("desc")){
                talentList = talentRepository.findTalentWithFilteringDescExperience(employeeStatus,talentStatus,talentLevel, pageable);
                System.out.println("ini desc, dan  experience");
            }else{
                System.out.println("kesini sih ");
                talentList = talentRepository.findTalentWithFilteringAscExperience(employeeStatus, talentStatus, talentLevel,pageable);
                System.out.println("ini asc, dan  experience");
            }
        }else{
            if(sortDirection != null && sortDirection.equals("desc")){
                talentList = talentRepository.findTalentWithFilteringDesc(employeeStatus,talentStatus,talentLevel,sortField,pageable);
                System.out.println("ini desc, dan bukan experience");
            }else{


                talentList = talentRepository.findTalentWithFilteringAsc(employeeStatus,talentStatus,talentLevel,sortField, pageable);
                System.out.println("ini asc, dan bukan experience");
            }

        }

        List<TalentsResponse> talentsResponses = talentList.getContent().stream().map(this::getSingleTalentResponse).collect(Collectors.toList());
        return new PageImpl<>(talentsResponses, pageable, talentList.getTotalElements());
    }

    public TalentsResponse getSingleTalentResponse(Talent talent){
        TalentsResponse talentsResponse = new TalentsResponse();

        talentsResponse.setTalentId(talent.getTalentId());
        talentsResponse.setTalentName(talent.getTalentName());
        talentsResponse.setTalentExperience(talent.getExperience());
        talentsResponse.setTalentStatus(talent.getTalentStatus()==null?null:talent.getTalentStatus().getTalentStatusName());
        talentsResponse.setTalentLevel(talent.getTalentLevel()==null?null:talent.getTalentLevel().getTalentLevelName());
        talentsResponse.setEmployeeStatus(talent.getEmployeeStatus()==null?null:talent.getEmployeeStatus().getEmployeeStatusName());


        List<PositionResponse> talentPositionsResponseList = new ArrayList<>();
        talent.getTalentPositions().forEach(talentPosition -> {
            PositionResponse positionsResponse = new PositionResponse();
            positionsResponse.setPositionId(talentPosition.getPosition().getPositionId());
            positionsResponse.setPositionName(talentPosition.getPosition().getPositionName());
            talentPositionsResponseList.add(positionsResponse);
        });
        talentsResponse.setPosition(talentPositionsResponseList);


        List<SkillsetsResponse> talentSkillsetsResponseList = new ArrayList<>();
        talent.getTalentSkillsets().forEach(skill -> {
            SkillsetsResponse skillsetsResponse = new SkillsetsResponse();
            skillsetsResponse.setSkillsetId(skill.getSkillset().getSkillsetId());
            skillsetsResponse.setSkillsetName(skill.getSkillset().getSkillsetName());
            talentSkillsetsResponseList.add(skillsetsResponse);
        });
        talentsResponse.setSkillset(talentSkillsetsResponseList);


        return talentsResponse;
    }


    @Transactional
    public List<TalentsResponse> getListTalents(String sortBy) {
        EmployeeStatus employeeStatus = employeeStatusRepository.findById(1).orElseThrow(()->null);


        // System.out.println("ini nah ====> " + sortBy);
        //sortBy = "talentId";
        List<Talent> talentList;

        List<TalentsResponse> talentsResponseList = new ArrayList<>();
        Sort sort;

        if(sortBy != null) {
            sort = Sort.by(Sort.Direction.ASC, sortBy);

            talentList = talentRepository.findAll(sort);
        } else {
            sortBy = "talentName";
            sort = Sort.by(Sort.Direction.ASC, sortBy);
            talentList = talentRepository.findAll(sort);
        }

        talentList.forEach(talent -> {
            TalentsResponse talentsResponse = new TalentsResponse();

            talentsResponse.setTalentId(talent.getTalentId());
            talentsResponse.setTalentName(talent.getTalentName());
            talentsResponse.setTalentExperience(talent.getExperience());
            talentsResponse.setTalentStatus(talent.getTalentStatus()==null?null:talent.getTalentStatus().getTalentStatusName());
            talentsResponse.setTalentLevel(talent.getTalentLevel()==null?null:talent.getTalentLevel().getTalentLevelName());
//            talentsResponse.setWishListId(1);
            talentsResponse.setEmployeeStatus(talent.getEmployeeStatus()==null?null:talent.getEmployeeStatus().getEmployeeStatusName());

            List<PositionResponse> clientPositionsResponseList = new ArrayList<>();
            talent.getTalentPositions().forEach(talentPosition -> {
                PositionResponse positionsResponse = new PositionResponse();
                positionsResponse.setPositionId(talentPosition.getPosition().getPositionId());
                positionsResponse.setPositionName(talentPosition.getPosition().getPositionName());
                clientPositionsResponseList.add(positionsResponse);
            });
            talentsResponse.setPosition(clientPositionsResponseList);


            List<SkillsetsResponse> clientSkillsetsResponseList = new ArrayList<>();
            talent.getTalentSkillsets().forEach(skill -> {
                SkillsetsResponse skillsetsResponse = new SkillsetsResponse();
                skillsetsResponse.setSkillsetId(skill.getSkillset().getSkillsetId());
                skillsetsResponse.setSkillsetName(skill.getSkillset().getSkillsetName());
                clientSkillsetsResponseList.add(skillsetsResponse);
            });
            talentsResponse.setSkillset(clientSkillsetsResponseList);


            talentsResponseList.add(talentsResponse);
        });

        return talentsResponseList;

    }


    /*CLIENT SIDE, GET WISHLIST TALENT*/
    public List<TalentWishListResponse> getTalentWishlist(Integer user_id) {
        List<TalentWishListResponse> talentsResponseList = new ArrayList<>();
        List<Talent> talentList = new ArrayList<>();
        Client client = new Client();
        List<TalentWishlist> talentWishlists;
        User user = userRepository.findByUserId(user_id);
        client = clientRepository.findByEmail(user.getEmail());


        talentWishlists = talentWishlistRepository.findAllByClient(client);
        talentWishlists.forEach(talentWish -> {
            TalentWishListResponse tempTalentWishListResponse = new TalentWishListResponse();
            tempTalentWishListResponse.setWishListId(talentWish.getTalentWishlistId());
            tempTalentWishListResponse.setTalentId(talentWish.getTalent().getTalentId());
            tempTalentWishListResponse.setTalentName(talentWish.getTalent().getTalentName());
            tempTalentWishListResponse.setEmployeeStatus(talentWish.getTalent().getEmployeeStatus().getEmployeeStatusName());
            tempTalentWishListResponse.setTalentLevel(talentWish.getTalent().getTalentLevel().getTalentLevelName());
            tempTalentWishListResponse.setTalentExperience(talentWish.getTalent().getExperience());

            List<PositionResponse> positionsResponsesList = new ArrayList<>();
            talentWish.getTalent().getTalentPositions().forEach(talentPosition -> {
                PositionResponse positionsResponse = new PositionResponse();
                positionsResponse.setPositionId(talentPosition.getPosition().getPositionId());
                positionsResponse.setPositionName(talentPosition.getPosition().getPositionName());
                positionsResponsesList.add(positionsResponse);
            });
            tempTalentWishListResponse.setPosition(positionsResponsesList);

            List<SkillsetsResponse> skillsetsResponseList = new ArrayList<>();
            talentWish.getTalent().getTalentSkillsets().forEach(talentSkillset -> {
                SkillsetsResponse skillsetsResponse = new SkillsetsResponse();
                skillsetsResponse.setSkillsetId(talentSkillset.getTalent().getTalentId());
                skillsetsResponse.setSkillsetName(talentSkillset.getTalent().getTalentName());
                skillsetsResponseList.add(skillsetsResponse);
            });
            tempTalentWishListResponse.setSkillset(skillsetsResponseList);

            talentsResponseList.add(tempTalentWishListResponse);
        });
        return talentsResponseList;
    }



    public DetailTalentResponse getDataDetailTalent(Integer talentId){
        Talent talent = talentRepository.findById(talentId).orElseThrow(() -> new RuntimeException(DATA_TALENT_NOT_FOUND));
        Integer totRequest ;
        DetailTalentResponse detailTalentResponse = new DetailTalentResponse();
        detailTalentResponse.setTalentId(talent.getTalentId());
        detailTalentResponse.setTalentPhoto(talent.getTalentPhotoUrl());
        detailTalentResponse.setTalentName(talent.getTalentName());
        detailTalentResponse.setTalentStatus(talent.getTalentStatus()==null?null:talent.getTalentStatus().getTalentStatusName());
        detailTalentResponse.setTalentStatusId(talent.getTalentStatus()==null?null:talent.getTalentStatus().getTalentStatusId());
//        detailTalentResponse.setNip(" ");
        detailTalentResponse.setSex(talent.getSex());
        detailTalentResponse.setDob(talent.getBirthDate());
        detailTalentResponse.setTalentDescription(talent.getTalentDescription());
        detailTalentResponse.setCv(talent.getTalentCvUrl());
        detailTalentResponse.setTalentExperience(talent.getExperience());
        detailTalentResponse.setTalentLevel(talent.getTalentLevel().getTalentLevelName());
        detailTalentResponse.setTalentLevelId(talent.getTalentLevel().getTalentLevelId());
        detailTalentResponse.setProjectCompleted(talent.getTotalProjectCompleted());

        /*GET TALENT - TOTAL REQUESTED*/
        List<TalentWishlist> talentWishlistList = talentWishlistRepository.findAllByTalent(talent);
        if(talentWishlistList != null){
            Long countTotalRequested =  talent.getTalentWishlists().stream().flatMap(talentWishlist -> talentWishlist.getTalentRequests().stream()).count();
            detailTalentResponse.setTotalRequested(countTotalRequested);
        }else{
            detailTalentResponse.setProjectCompleted(null);
        }

        if(talent.getTalentPositions() != null){
            List<PositionResponse> positionResponseList = new ArrayList<>();
            talent.getTalentPositions().forEach(talentPosition -> {
                PositionResponse positionResponse = new PositionResponse();
                positionResponse.setPositionId(talentPosition.getPosition().getPositionId());
                positionResponse.setPositionName(talentPosition.getPosition().getPositionName());
                positionResponseList.add(positionResponse);
            });
            detailTalentResponse.setPosition(positionResponseList);

        }else{
            detailTalentResponse.setPosition(null);
        }

        if(talent.getTalentSkillsets() != null){
            List<SkillsetsResponse> skillsetsResponseList = new ArrayList<>();
            talent.getTalentSkillsets().forEach(talentSkillset -> {
                SkillsetsResponse skillsetsResponse = new SkillsetsResponse();
                skillsetsResponse.setSkillsetId(talentSkillset.getSkillset().getSkillsetId());
                skillsetsResponse.setSkillsetName(talentSkillset.getSkillset().getSkillsetName());
                skillsetsResponseList.add(skillsetsResponse);
            });
            detailTalentResponse.setSkillSet(skillsetsResponseList);
        }else{
            detailTalentResponse.setSkillSet(null);
        }

        detailTalentResponse.setEmail(talent.getEmail());
        detailTalentResponse.setCellphone(talent.getCellphone());
        detailTalentResponse.setEmployeeStatus(talent.getEmployeeStatus()==null?null:talent.getEmployeeStatus().getEmployeeStatusName());
        detailTalentResponse.setEmployeeStatusId(talent.getEmployeeStatus()==null?null:talent.getEmployeeStatus().getEmployeeStatusId());
//        detailTalentResponse.setTalentAvailibility(talent.getIsActive() ==true?"active":"not active");
        detailTalentResponse.setVideoUrl(talent.getBiographyVideoUrl());

        return detailTalentResponse;
    }


    public String generateRandomFileName(MultipartFile file){
        String originalFileName = file.getOriginalFilename();
        if(originalFileName != null){
            return System.currentTimeMillis() + "_-_ " + originalFileName.replace(" ", "_" );
        }else{
            throw new IllegalStateException(ResponseMessages.ORIGINAL_FILE_NAME_IS_NULL);
        }
    }


    public String uploadFileToMinio(MultipartFile file){
        if(!file.isEmpty()){
            String randomName = generateRandomFileName(file);
            minioSrvc.upload(file, bucketName, uploadedfile -> MinioSrvc.UploadOption.builder().filename(randomName).build());
            return randomName;
        }else{
            throw  new RuntimeException(ResponseMessages.FILE_UPLOADED_IS_EMPTY);
        }
    }

    public TalentStatus findTalentStatusByTalentStatusId(Integer talentStatusId){
        return talentStatusRepository.findById(talentStatusId).orElseThrow(()-> new EntityNotFoundException(String.format(ResponseMessages.TALENT_STATUS_NOT_FOUND)));
    }

    public TalentLevel findTalentLevelByTalentLevelId(Integer talentLevelId){
        return talentLevelRepository.findById(talentLevelId).orElseThrow(() -> new EntityNotFoundException(String.format(ResponseMessages.TALENT_LEVEL_NOT_FOUND)));
    }

    public Position findPositionByPositionId(Integer positionId){
        return positionRepository.findById(positionId).orElseThrow(() -> new EntityNotFoundException(String.format(ResponseMessages.TALENT_POSITION_NOT_FOUND)));
    }

    public Skillset findTalentSkillsetByTalentSkillsetId(Integer talentSkillsetId){
        return skillsetRepository.findById(talentSkillsetId).orElseThrow(() -> new EntityNotFoundException(String.format(ResponseMessages.TALENT_SKILLSET_NOT_FOUND)));
    }

    public EmployeeStatus findEmployeeStatusByEmployeeStatusId(Integer employeeStatusId){
        return employeeStatusRepository.findById(employeeStatusId).orElseThrow(() -> new EntityNotFoundException(String.format(ResponseMessages.EMPLOYEE_STATUS_NOT_FOUND)));
    }

    public void saveAllPositionsByPositionId(List<TalentPositionsRequest> talentPositionsRequestList, Talent talent){
        for (TalentPositionsRequest talentPositionsRequest : talentPositionsRequestList){
            TalentPosition talentPosition = new TalentPosition();
            talentPosition.setCreatedTime(LocalDateTime.now());
            talentPosition.setTalent(talent);
            talentPosition.setPosition(findPositionByPositionId(talentPositionsRequest.getTalentPositionId()));
            talentPositionRepository.save(talentPosition);
        }
    }

    public void saveAllSkillsetsBySkillsetId(List<TalentSkillsetRequest> talentSkillsetRequestsList, Talent talent){
        for(TalentSkillsetRequest talentSkillsetRequest : talentSkillsetRequestsList){
            TalentSkillset talentSkillset = new TalentSkillset();
            talentSkillset.setCreatedTime(LocalDateTime.now());
            talentSkillset.setTalent(talent);
            talentSkillset.setLastModifiedTime(LocalDateTime.now());
            talentSkillset.setSkillset(findTalentSkillsetByTalentSkillsetId(talentSkillsetRequest.getSkillSetId()));
            talentSkillsetRepository.save(talentSkillset);
        }
    }


//    public void editSkillsetsBySkillsetId(List<TalentSkillsetRequest> talentSkillsetRequestList, Talent talent){
//        List<TalentSkillsetRequest> talentSkillsetRequestsToAdded;
//        List<TalentSkillset> talentSkillsets = talentSkillsetRepository.findAllByTalent(talent);
//        for (TalentSkillsetRequest talentSkillsetRequest : talentSkillsetRequestList){
//          for(TalentSkillset talentSkillset : talentSkillsets){
//              if(talentSkillset.getSkillset().getSkillsetId() == talentSkillsetRequestList.get)
//          }
//        }
//    }

    /*EDIT*/
    public EditTalentResponse putDataTalent(Integer talentId, EditTalentRequest editTalentRequest){
       try{
           Talent talentEdit = talentRepository.findById(talentId).orElseThrow(() -> new EntityNotFoundException(String.format(ResponseMessages.TALENT_SKILLSET_NOT_FOUND)));
           String imageUrl;

//
           if( editTalentRequest.getTalentPhoto() != null || !editTalentRequest.getTalentPhoto().isEmpty()){
               imageUrl = minioSrvc.upload(editTalentRequest.getTalentPhoto(), bucketName);
               System.out.println("IMG NYA ADA NIh" + editTalentRequest.getTalentPhoto());
               talentEdit.setTalentPhotoUrl(imageUrl);
           }else{
             //  imageUrl = null;
               System.out.println("GAMBARNYA GAADA NIH");
           }

           String cvUrl ;
           if( editTalentRequest.getCv() == null ){
               cvUrl= null;
               System.out.println("CV GA ADA");
           }else{
               cvUrl = minioSrvc.upload(editTalentRequest.getCv(), bucketName);
               talentEdit.setTalentCvUrl(cvUrl);
               System.out.println("CV ADA");
           }

           talentEdit.setTalentName(editTalentRequest.getTalentName());
           talentEdit.setTalentStatus(findTalentStatusByTalentStatusId(editTalentRequest.getTalentStatusId()));
           talentEdit.setSex(editTalentRequest.getSex());
           talentEdit.setBirthDate(editTalentRequest.getDob());
           talentEdit.setTalentDescription(editTalentRequest.getTalentDescription());
         //  talentEdit.setTalentCvUrl(cvUrl);
           talentEdit.setExperience(editTalentRequest.getTalentExperience());
           talentEdit.setTalentLevel(findTalentLevelByTalentLevelId(editTalentRequest.getTalentLevelId()));
           talentEdit.setTotalProjectCompleted(editTalentRequest.getProjectCompleted());
           talentEdit.setEmail(editTalentRequest.getEmail());
           talentEdit.setCellphone(editTalentRequest.getCellphone());
           talentEdit.setEmployeeStatus(findEmployeeStatusByEmployeeStatusId(editTalentRequest.getEmployeeStatusId()));
     //      talentEdit.setIsActive(editTalentRequest.getTalentAvailibility());
           talentEdit.setBiographyVideoUrl(editTalentRequest.getVideoUrl());
//           talentEdit.setTalentSkillsets(null);
           talentEdit = talentRepository.save(talentEdit);

          // saveAllSkillsetsBySkillsetId(editTalentRequest.getSkillSet(), talentEdit);

           if(editTalentRequest.getSkillSet() != null){
               List<TalentSkillsetRequest> talentSkillsetRequestList = new ArrayList<>();
               editTalentRequest.getSkillSet().forEach(talentSkillsetRequest -> {
                   Optional<Skillset> getSkillSetByReq = skillsetRepository.findById(talentSkillsetRequest.getSkillSetId());
                   Optional<Talent> currTalent = talentRepository.findById(talentId);
                   if(currTalent.isPresent()){
                       System.out.println("ada talentnya");
                      if(getSkillSetByReq.isPresent()){
                          System.out.println("ada skillset nya");
                          Optional<TalentSkillset> areThisSkillSetIsExistInCurrTalent = talentSkillsetRepository.findByTalentAndSkillset(currTalent.get(),getSkillSetByReq.get());
                          if(areThisSkillSetIsExistInCurrTalent.isPresent()){
                              System.out.println("ada yang sama nih skillset nya");
                          }else{
                              System.out.println("gaada yang sama nih skillset nya"+ talentSkillsetRequest.getSkillSetId());
                              talentSkillsetRequestList.add(talentSkillsetRequest);
                          }
                      }else{
//                          System.out.println("gaada skillset nya");
                      }
                   }else{
//                       System.out.println("gaada talentnya");
                   }
               });

               if(talentSkillsetRequestList.size()!=0){
                   System.out.println("ADA DATA TALENT SKILLSET YANG DITAMBAHKAN");
                   saveAllSkillsetsBySkillsetId(talentSkillsetRequestList, talentEdit);
               }else{
                   System.out.println("GAADA DATA SKILLSET TALENT YANG DITAMBAHKAN");

               }
           }

           if(editTalentRequest.getPosition() != null){
               List<TalentPositionsRequest> talentPositionsRequestList = new ArrayList<>();
               editTalentRequest.getPosition().forEach(talentPositionsRequest -> {
                   Position getPositionByReq = positionRepository.findById(talentPositionsRequest.getTalentPositionId()).orElseThrow(()->null);
                   Talent currTalent = talentRepository.findById(talentId).orElseThrow(()->null);
                   if(currTalent != null){
                       TalentPosition areThisPositionIsExistInCurrTalent = talentPositionRepository.findByTalentAndPosition(currTalent, getPositionByReq).orElseThrow(()->null);
                       if(areThisPositionIsExistInCurrTalent != null){
                           System.out.println("ada yang sama nih");
                       }else{
                           talentPositionsRequestList.add(talentPositionsRequest);
                           System.out.println("gaada yang sama nih");
                       }
                   }

               });

               if(talentPositionsRequestList != null && talentPositionsRequestList.size() != 0){
                   saveAllPositionsByPositionId(talentPositionsRequestList, talentEdit);
                   System.out.println("ada data yang ditambahkan ");
               }else{
                   System.out.println("Tidak ada data position yang ditambahkan ke talent");
               }
           }
          // talentEdit = talentRepository.save(talentEdit);
           EditTalentResponse editTalentResponse = new EditTalentResponse();
           editTalentResponse.setIdTalent(talentEdit.getTalentId());
           return editTalentResponse;
       }catch (Exception e){
           throw new  RuntimeException(e.getMessage());
       }
    }
    
    public ResponseTalent saveDataTalent(AddTalentRequest requestTambahTalent){
        try {
            long timestampInMillis = System.currentTimeMillis(); // Replace this with your actual long timestamp
            Date currentTime = new Date(timestampInMillis);
            Talent talent = new Talent();

            String imageUrl;

            if(requestTambahTalent.getTalentPhoto().isEmpty()){
                imageUrl = null;
            }else{
                imageUrl = minioSrvc.upload(requestTambahTalent.getTalentPhoto(), bucketName);
            }

            //String imageUrl = uploadFileToMinio(requestTambahTalent.getTalentPhoto());
            String cvUrl ;
            if(requestTambahTalent.getCv().isEmpty()){
                cvUrl= null;
            }else{
                cvUrl = minioSrvc.upload(requestTambahTalent.getCv(), bucketName);
            }

            talent.setTalentPhotoUrl(imageUrl);
            talent.setTalentName(requestTambahTalent.getTalentName());
            talent.setEmployeeNumber(requestTambahTalent.getNip());
            talent.setSex(requestTambahTalent.getSex());
            talent.setBirthDate(requestTambahTalent.getDob());
            talent.setTalentDescription(requestTambahTalent.getTalentDescription());
            talent.setTalentCvUrl(cvUrl);
            talent.setTalentStatus(talentStatusRepository.findById(requestTambahTalent.getTalentStatusId()).orElse(null));
            talent.setExperience(requestTambahTalent.getTalentExperience());
            talent.setTotalProjectCompleted(requestTambahTalent.getProjectCompleted());
            talent.setTalentLevel(findTalentLevelByTalentLevelId(requestTambahTalent.getTalentLevelId()));
            talent.setEmail(requestTambahTalent.getEmail());
            talent.setCellphone(requestTambahTalent.getCellphone());
            talent.setEmployeeStatus(findEmployeeStatusByEmployeeStatusId(requestTambahTalent.getEmployeeStatusId()));
            talent.setBiographyVideoUrl(requestTambahTalent.getVideoUrl());
            talent.setIsActive(true);
            Talent savedTalent = talentRepository.save(talent);
            saveAllSkillsetsBySkillsetId(requestTambahTalent.getSkillSet(), talent);
            saveAllPositionsByPositionId(requestTambahTalent.getPosition(),talent);
            ResponseTalent responseTambahTalent = new ResponseTalent();
            responseTambahTalent.setTalentName(savedTalent.getTalentName());
            responseTambahTalent.setNewTalentId(savedTalent.getTalentId());
            responseTambahTalent.setMessage("Data Talent Berhasil Dibuat");

            return responseTambahTalent;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }







}