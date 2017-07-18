package com.alienlab.catpower.repository;

import com.alienlab.catpower.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    //根据课程ID查询课程

    //根据课程名称获取课程总课时
    List<Course> findTotalClassHourByCourseName(String courseName) throws Exception;

}
