package com.alienlab.catpower.service.impl;

import com.alienlab.catpower.service.CourseSchedulingService;
import com.alienlab.catpower.domain.CourseScheduling;
import com.alienlab.catpower.repository.CourseSchedulingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Service Implementation for managing CourseScheduling.
 */
@Service
@Transactional
public class CourseSchedulingServiceImpl implements CourseSchedulingService{

    private final Logger log = LoggerFactory.getLogger(CourseSchedulingServiceImpl.class);

    private final CourseSchedulingRepository courseSchedulingRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;
    public CourseSchedulingServiceImpl(CourseSchedulingRepository courseSchedulingRepository) {
        this.courseSchedulingRepository = courseSchedulingRepository;
    }

    /**
     * Save a courseScheduling.
     *
     * @param courseScheduling the entity to save
     * @return the persisted entity
     */
    @Override
    public CourseScheduling save(CourseScheduling courseScheduling) {
        log.debug("Request to save CourseScheduling : {}", courseScheduling);
        CourseScheduling result = courseSchedulingRepository.save(courseScheduling);
        return result;
    }

    /**
     *  Get all the courseSchedulings.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CourseScheduling> findAll(Pageable pageable) {
        log.debug("Request to get all CourseSchedulings");
        Page<CourseScheduling> result = courseSchedulingRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one courseScheduling by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CourseScheduling findOne(Long id) {
        log.debug("Request to get CourseScheduling : {}", id);
        CourseScheduling courseScheduling = courseSchedulingRepository.findOne(id);
        return courseScheduling;
    }

    /**
     *  Delete the  courseScheduling by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseScheduling : {}", id);
        courseSchedulingRepository.delete(id);
    }

    @Override
    public List<CourseScheduling> getScheByDate(ZonedDateTime startDate, ZonedDateTime endDate) throws Exception {
        return courseSchedulingRepository.findCourseSchedulingsByStartTimeBetween(startDate,endDate);
    }

    @Override
    public Page<CourseScheduling> getScheByDate(ZonedDateTime startDate, ZonedDateTime endDate, int index, int size) throws Exception {
        return courseSchedulingRepository.findCourseSchedulingsByStartTimeBetween(startDate,endDate,new PageRequest(index,size));
    }

    /**
     *根据ID更新上课的状态
     *
     */
    @Override
    public int updateCourseScheduling(Long id,String status) throws Exception {
        int n = 0;
        String sql = "UPDATE `course_scheduling` SET STATUS=? WHERE id=?";
        Object[] args = {status,id};
        n = jdbcTemplate.update(sql,args);
        return n;
    }
}
