package com.tujuhsembilan.app.controller;

import com.tujuhsembilan.app.dto.GetTalentResponsDTO;
import com.tujuhsembilan.app.dto.request.AddTalentRequest;
import com.tujuhsembilan.app.dto.request.EditTalentRequest;
import com.tujuhsembilan.app.dto.request.UpdateTalentApprovalRequest;
import com.tujuhsembilan.app.dto.response.CustomResponseAPIApproval;
import com.tujuhsembilan.app.dto.response.TalentsResponse;
import com.tujuhsembilan.app.service.TalentApprovalManagementService;
import com.tujuhsembilan.app.service.TalentManagementService;
import com.tujuhsembilan.app.util.ResponseHandler;
import com.tujuhsembilan.app.util.ResponseMessages;
import okhttp3.Response;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

import java.util.List;

import static com.tujuhsembilan.app.util.ResponseMessages.*;

@RestController
@RequestMapping("/talent-management")

public class TalentManagementController {

    @Autowired
    private TalentManagementService talentManagementService;

    @Autowired
    private TalentApprovalManagementService talentApprovalManagementService;

//    @GetMapping("/talents")
//    public ResponseEntity<Object> getTalents(@RequestParam(name = "sortBy", required = false) String sortBy){
//        try{
//            return ResponseHandler.generateResponse("Get data Talents success", HttpStatus.OK, talentManagementService.getListTalents(sortBy));
//        }catch(Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }



    @GetMapping("/talents")
    public ResponseEntity<?> getDataTalentWithFiltering(@RequestParam(name = "employeeStatusId", required = false) Integer employeeStatusId,
                                        @RequestParam(name = "talentStatusId", required = false)Integer talentStatusId,
                                        @RequestParam(name = "talentLevelId", required = false)Integer talentLevelId,
                                        @RequestParam(name = "sortField", required = false)String sortField,
                                        @RequestParam(name = "sortDirection", required = false)String sortDirection,
                                                        @RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size){
        Page<TalentsResponse> listTalents =talentManagementService.getAllTalents(employeeStatusId, talentStatusId, talentLevelId, sortField, sortDirection,page,size);
        return ResponseHandler.generateResponse("Data berhasil", HttpStatus.OK, listTalents);
    }

//    @GetMapping("/talents")
//    public ResponseEntity<Page<GetTalentResponsDTO>> getAllTalent(@RequestParam(name = "tags", required = false) List<String> tags,
//                                                                  @RequestParam(name = "sortBy", required = false) String sortBy,
//                                                                  @RequestParam(defaultValue = "0") int page,
//                                                                  @RequestParam(defaultValue = "10") int size){
//        Page<GetTalentResponsDTO> listTalents = talentManagementService.getAllDataTalentPagination(tags, sortBy, page, size);
//        return new ResponseEntity<>(listTalents, HttpStatus.OK);
//    }

    @GetMapping("/talent-approvals")
    public ResponseEntity<?> getAllApproval(@RequestParam(name = "filterBy", required = false) String filterBy,
                                                 @RequestParam(name = "filterRequest", required = false) String filterRequest,
                                                 @RequestParam(name = "sortBy", required = false) String sortBy,
                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "10") int size
                                            ){
        try{
            return ResponseHandler.generateResponse(GET_TALENT_REQUEST_IS_SUCCESS, HttpStatus.OK, talentApprovalManagementService.getDataApprovalTalentList(filterBy, filterRequest, sortBy, page,size));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("talent-approvals/{talentRequestId}")
    public ResponseEntity<?> putApprovalsTalent(@PathVariable Integer talentRequestId, @RequestBody UpdateTalentApprovalRequest updateTalentApprovalRequest){
        try{

            return ResponseHandler.generateResponse(UPDATE_APPROVAL_REQUEST_TALENT_IS_SUCCESS,HttpStatus.OK, talentApprovalManagementService.putApprovePersetujuanTalent(talentRequestId, updateTalentApprovalRequest));
        }catch (Exception e){
            return ResponseHandler.generateResponse(UPDATE_APPROVAL_REQUEST_TALENT_IS_FAILED,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/talents/{talentId}")
    public ResponseEntity<?> getDetailTalent(@PathVariable Integer talentId){
        try{
            return ResponseHandler.generateResponse(GET_DETAIL_TALENT_SUCCESS, HttpStatus.OK, talentManagementService.getDataDetailTalent(talentId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(GET_DETAIL_TALENT_FAILED);
        }
    }

    @PostMapping(value = "/talents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> postDataTalent(@ModelAttribute AddTalentRequest addTalentRequest){
        System.out.println("MASUK INPUT NYA");
        try{

           return ResponseHandler.generateResponse(ADD_TALENT_IS_SUCESS, HttpStatus.OK, talentManagementService.saveDataTalent(addTalentRequest));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PutMapping( "/talents/{talentId}")
    public  ResponseEntity<?> editDataTalent( @PathVariable Integer talentId, @ModelAttribute(name = "editTalentRequest") EditTalentRequest editTalentRequest){
        System.out.println("MASUK NIH EDIT NYA NYA");

        if(editTalentRequest.getTalentPhoto() == null){
            System.out.println("foto nya null");
        }
        try{
            return ResponseHandler.generateResponse(EDIT_TALENT_IS_SUCCESS, HttpStatus.OK, talentManagementService.putDataTalent(talentId,editTalentRequest));
        }catch (Exception e){
//            return ResponseEntity.badRequest().body(EDIT_TALENT_IS_FAIL);
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }


}
