package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.dto.response.TalentsResponse;
import com.tujuhsembilan.app.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalentRepository extends JpaRepository<Talent, Integer> {
    List<Talent> findByTalentNameContainingIgnoreCase(String talentName);

    Page<Talent> findByTalentSkillsetsSkillsetSkillsetNameIn(List<String> skillsetNames, Pageable page);

//    Page<Talent> findTalentByTalentLevel(TalentLevel talentLevel, Pageable pageable, Sort sort);
//

    @Query("SELECT t FROM Talent t " +
            "WHERE (:employeeStatusId IS NULL OR t.employeeStatus = :employeeStatusId) " +
            "AND (:talentStatusId IS NULL OR t.talentStatus = :talentStatusId) " +
            "AND (:talentLevelId IS NULL OR t.talentLevel = :talentLevelId) " +
            "ORDER BY " +
            "CASE " +
            "  WHEN :sortField IS NULL THEN t.talentName " +
            "  WHEN :sortField = 'talentlevel' THEN t.talentLevel.talentLevelName " +
            "  WHEN :sortField = 'talenttatus' THEN  t.talentStatus.talentStatusName "+
            "  WHEN :sortField = 'employeestatus' THEN t.employeeStatus.employeeStatusName "+
            "END DESC")
    Page<Talent> findTalentWithFilteringDesc(@Param("employeeStatusId") EmployeeStatus employeeStatusId,
                                            @Param("talentStatusId")TalentStatus talentStatusId,
                                            @Param("talentLevelId")TalentLevel talentLevelId,
                                            @Param("sortField") String sortField,
                                             Pageable pageable

    );

    @Query("SELECT t FROM Talent t " +
            "WHERE (:employeeStatusId IS NULL OR t.employeeStatus = :employeeStatusId) " +
            "AND (:talentStatusId IS NULL OR t.talentStatus = :talentStatusId) " +
            "AND (:talentLevelId IS NULL OR t.talentLevel = :talentLevelId) " +
            "ORDER BY " +
            "CASE " +
            "  WHEN :sortField IS NULL THEN t.talentName " +
            "  WHEN :sortField = 'talentlevel' THEN t.talentLevel.talentLevelName " +
            "  WHEN :sortField = 'talenttatus' THEN  t.talentStatus.talentStatusName "+
            "  WHEN :sortField = 'employeestatus' THEN t.employeeStatus.employeeStatusName "+
            "END ASC")
    Page<Talent> findTalentWithFilteringAsc(@Param("employeeStatusId") EmployeeStatus employeeStatusId,
                                            @Param("talentStatusId")TalentStatus talentStatusId,
                                            @Param("talentLevelId")TalentLevel talentLevelId,
                                            @Param("sortField")String sortField,
                                            Pageable pageable
    );

    @Query("SELECT t FROM Talent t " +
            "WHERE (:employeeStatusId IS NULL OR t.employeeStatus = :employeeStatusId) " +
            "AND (:talentStatusId IS NULL OR t.talentStatus = :talentStatusId) " +
            "AND (:talentLevelId IS NULL OR t.talentLevel = :talentLevelId) " +
            "ORDER BY " +
            " t.experience " +
            " ASC")
    Page<Talent> findTalentWithFilteringAscExperience(@Param("employeeStatusId") EmployeeStatus employeeStatusId,
                                            @Param("talentStatusId")TalentStatus talentStatusId,
                                            @Param("talentLevelId")TalentLevel talentLevelId,
                                                      Pageable pageable

    );

    @Query("SELECT t FROM Talent t " +
            "WHERE (:employeeStatusId IS NULL OR t.employeeStatus = :employeeStatusId) " +
            "AND (:talentStatusId IS NULL OR t.talentStatus = :talentStatusId) " +
            "AND (:talentLevelId IS NULL OR t.talentLevel = :talentLevelId) " +
            "ORDER BY " +
            " t.experience " +
            " DESC ")
    Page<Talent> findTalentWithFilteringDescExperience(@Param("employeeStatusId") EmployeeStatus employeeStatusId,
                                                      @Param("talentStatusId")TalentStatus talentStatusId,
                                                      @Param("talentLevelId")TalentLevel talentLevelId,
                                                       Pageable pageable

    );




    List<Talent> findAll(Sort sort);

    Talent findByTalentWishlists(TalentWishlist talentWishlist);



   // List<Talent> findAll();

}
