package com.alienlab.catpower.repository;

import com.alienlab.catpower.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the BuyCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyCourseRepository extends JpaRepository<BuyCourse,Long> {

    //根据时间查询授课情况
    Page<BuyCourse> findBuyCourseByBuyTimeBetweenOrderByBuyTimeDesc(ZonedDateTime butTime1,ZonedDateTime butTime2, Pageable page);

    @Query("select a from BuyCourse a where a.course=?2 and a.learner=?1 and a.status='正常'")
    BuyCourse findBuyCourseByLearnerAndCourse(Learner learner, Course course);


    @Query("select a from BuyCourse a where a.learner.id=?1 group by a.coach.id")
    List<BuyCourse> findCoachByLearner(Long learnerId);

    @Query("select a.course from BuyCourse a where a.coach=?1 and a.learner.id=?2")
    List<BuyCourse> findCourseByCoach(Coach coach, Long learnerId);

    @Query("select a from BuyCourse a where a.learner.id=?1")
    List<BuyCourse> findCourseByLearner(Long learnerId);

    //查询我的课程
    @Query("select a from BuyCourse a where a.learner=?1 order by a.buyTime")
    List<BuyCourse> findBuyCourseByLearner(Learner learner);


    //查询支付方式
    @Query("select a from BuyCourse a group by a.paymentWay")
    List<BuyCourse> findBuyCourse();

    //查询退课情况
    Page<BuyCourse> findBuyCoursesByStatus(String status, Pageable pageable);

    @Query("select a from BuyCourse a where a.coach.id=?1 group by a.learner.id")
    List<BuyCourse> getBuyCourseByCoachId(Long coachId);

    @Query("select a from BuyCourse a where a.coach=?1 ")
    List<BuyCourse> findBuyCourseByCoach(Coach coach);

    //模糊查询
    @Query("select a from BuyCourse a where (a.course.courseName like CONCAT('%',?1,'%')) or (a.learner.learneName like CONCAT('%',?1,'%')) or (a.coach.coachName like CONCAT('%',?1,'%')) ")
    Page<BuyCourse> findBuyCourseByCourseNameLike(String keyword,Pageable pageable);

    @Query("select a from BuyCourse a where a.coach=?2 and a.learner=?1 and a.remainClass>0 order by a.buyTime asc")
    List<BuyCourse> findByLearnerAndCoach(Learner learner,Coach coach);

    @Query("select a from BuyCourse a where a.coach=?1 and a.remainClass>0 and a.learner not in(select b.learner from CourseScheduling b where b.status in ('已预约','预约中') and b.appointDate>=?2)")
    List<BuyCourse> findHasCourseLearners(Coach coach,String date);



}
