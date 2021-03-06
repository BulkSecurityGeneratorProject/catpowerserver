package com.alienlab.catpower.service;

import com.alienlab.catpower.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Interface for managing LearnerCharge.
 */
public interface LearnerChargeService {

    /**
     * Save a learnerCharge.
     *  保存一条学员核销信息
     * @param learnerCharge the entity to save
     * @return the persisted entity
     */
    LearnerCharge save(LearnerCharge learnerCharge);

    /**
     *  Get all the learnerCharges.
     *  得到所有的学员核销信息
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LearnerCharge> findAll(Pageable pageable);

    /**
     *  Get the "id" learnerCharge.
     *  得到指定id的学员核销信息
     *  @param id the id of the entity
     *  @return the entity
     */
    LearnerCharge findOne(Long id);

    /**
     *  Delete the "id" learnerCharge.
     *  删除指定id的学员核销信息
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<LearnerCharge> getLeanersBySche(long scheId) throws Exception;

    List<LearnerCharge> getLeanersBySche(CourseScheduling sche) throws Exception;

    LearnerCharge chargeCourse(String openid,Long scheId)throws Exception;

    LearnerCharge chargeCourse(Learner learner, CourseScheduling sche) throws Exception;

    /**
     * 添加核销记录
     */
    public boolean addLearnerCharge(Long id, ZonedDateTime chargeTime,String buyCourseId,String chargePeople,Long remainNumber,Long learner,Course course,Coach coach);

    //查询学员的签到课程
    List<LearnerCharge> findLeanerChargeByLearnerId(Long learnerId) throws Exception;

    //查询学员对应的签到记录
    List<LearnerCharge> findLearnerChargeByCourseIdAndLearnerId(Long courseId,Long learnerId);

    //学员小程序签到上课
    LearnerCharge learnSign(Learner learner,CourseScheduling scheduling) throws Exception;


}
