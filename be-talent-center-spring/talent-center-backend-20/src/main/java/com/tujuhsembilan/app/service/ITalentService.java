package com.tujuhsembilan.app.service;

import com.tujuhsembilan.app.dto.ResponseTalent;
import com.tujuhsembilan.app.dto.request.*;
import com.tujuhsembilan.app.dto.response.*;
import com.tujuhsembilan.app.model.*;
import org.springframework.data.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ITalentService {

    Page<TalentsResponse> getAllTalents(Integer employeeStatusId, Integer talentStatusId, Integer talentLevelId,
                                        String sortField, String sortDirection, Integer page, Integer size);

    TalentsResponse getSingleTalentResponse(Talent talent);

    List<TalentsResponse> getListTalents(String sortBy);

    List<TalentWishListResponse> getTalentWishlist(Integer user_id);

    DetailTalentResponse getDataDetailTalent(Integer talentId);

    String generateRandomFileName(MultipartFile file);

    String uploadFileToMinio(MultipartFile file);

    TalentStatus findTalentStatusByTalentStatusId(Integer talentStatusId);

    TalentLevel findTalentLevelByTalentLevelId(Integer talentLevelId);

    Position findPositionByPositionId(Integer positionId);

    Skillset findTalentSkillsetByTalentSkillsetId(Integer talentSkillsetId);

    EmployeeStatus findEmployeeStatusByEmployeeStatusId(Integer employeeStatusId);

    void saveAllPositionsByPositionId(List<TalentPositionsRequest> talentPositionsRequestList, Talent talent);

    void saveAllSkillsetsBySkillsetId(List<TalentSkillsetRequest> talentSkillsetRequestsList, Talent talent);

    EditTalentResponse putDataTalent(Integer talentId, EditTalentRequest editTalentRequest);

    ResponseTalent saveDataTalent(AddTalentRequest requestTambahTalent);
}
