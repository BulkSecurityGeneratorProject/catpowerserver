package com.alienlab.catpower.repository;

import com.alienlab.catpower.domain.Coach;
import com.alienlab.catpower.domain.CourseScheduling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the CourseScheduling entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseSchedulingRepository extends JpaRepository<CourseScheduling,Long> {

    List<CourseScheduling> findCourseSchedulingsByStartTimeBetween(ZonedDateTime startDate, ZonedDateTime endDate);
    Page<CourseScheduling> findCourseSchedulingsByStartTimeBetween(ZonedDateTime startDate, ZonedDateTime endDate, Pageable page);
    //根据教练获取排课记录
    List<CourseScheduling> findCourseSchedulingsByCoach(Coach coach);
    //根据具体日期和时间查询当天排课情况
   /* @Query("select a from  CourseScheduling a where a.coach=?1 and a.startTime=?2")
    List<CourseScheduling> findCourseSchedulingsByCoachAndStartTime(Coach coach,ZonedDateTime startDate);
*/
}
