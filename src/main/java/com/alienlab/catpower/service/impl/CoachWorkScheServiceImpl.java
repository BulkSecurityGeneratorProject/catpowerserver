package com.alienlab.catpower.service.impl;
import com.alienlab.catpower.domain.Coach;
import com.alienlab.catpower.domain.CoachWorkSche;
import com.alienlab.catpower.repository.CoachWorkScheRespository;
import com.alienlab.catpower.service.CoachService;
import com.alienlab.catpower.service.CoachWorkScheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * user :小乐爷
 * time :2017/7/22
 */
@Service
@Transactional
public class CoachWorkScheServiceImpl implements CoachWorkScheService {
    private final Logger log = LoggerFactory.getLogger(CoachWorkScheService.class);

    private final CoachWorkScheRespository coachWorkScheRespository;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    CoachWorkScheService coachWorkScheService;
    @Autowired
    CoachService coachService;

    public CoachWorkScheServiceImpl(CoachWorkScheRespository coachWorkScheRespository) {
        this.coachWorkScheRespository = coachWorkScheRespository;
    }

    /**
     * Save a coach.
     *
     * @param coachWorkSche the entity to save
     * @return the persisted entity
     */
    @Override
    public CoachWorkSche save(CoachWorkSche coachWorkSche) {
        log.debug("Request to save Coach : {}", coachWorkSche);
        CoachWorkSche result = coachWorkScheRespository.save(coachWorkSche);
        return result;
    }

    /**
     *  Get all the coaches.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CoachWorkSche> findAll(Pageable pageable) {
        log.debug("Request to get all Coaches");
        Page<CoachWorkSche> result = coachWorkScheRespository.findAll(pageable);
        return result;
    }

    /**
     *  Get one coach by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CoachWorkSche findOne(Long id) {
        log.debug("Request to get Coach : {}", id);
        CoachWorkSche coach = coachWorkScheRespository.findOne(id);
        return coach;
    }

    /**
     *  Delete the  coach by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Coach : {}", id);
        coachWorkScheRespository.delete(id);
    }

    //根据教练的ID查询对应的排班时间
    @Override
    public List<CoachWorkSche> findCoachWorkScheByCoachId(Long coachId) throws Exception{
        if(coachId == null){
            throw new Exception("参数异常");
        }
        Coach coach = coachService.findOne(coachId);
        if (coach == null){
            throw  new Exception("没有找到对应的教练信息");
        }
        List<CoachWorkSche> lists = coachWorkScheRespository.findCoachWorkScheByCoach(coach);
        return lists;
    }
}