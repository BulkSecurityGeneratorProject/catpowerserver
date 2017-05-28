package com.alienlab.catpower.service.impl;

import com.alienlab.catpower.service.LearnerInfoService;
import com.alienlab.catpower.domain.LearnerInfo;
import com.alienlab.catpower.repository.LearnerInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing LearnerInfo.
 */
@Service
@Transactional
public class LearnerInfoServiceImpl implements LearnerInfoService{

    private final Logger log = LoggerFactory.getLogger(LearnerInfoServiceImpl.class);
    
    private final LearnerInfoRepository learnerInfoRepository;

    public LearnerInfoServiceImpl(LearnerInfoRepository learnerInfoRepository) {
        this.learnerInfoRepository = learnerInfoRepository;
    }

    /**
     * Save a learnerInfo.
     *
     * @param learnerInfo the entity to save
     * @return the persisted entity
     */
    @Override
    public LearnerInfo save(LearnerInfo learnerInfo) {
        log.debug("Request to save LearnerInfo : {}", learnerInfo);
        LearnerInfo result = learnerInfoRepository.save(learnerInfo);
        return result;
    }

    /**
     *  Get all the learnerInfos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LearnerInfo> findAll(Pageable pageable) {
        log.debug("Request to get all LearnerInfos");
        Page<LearnerInfo> result = learnerInfoRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one learnerInfo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LearnerInfo findOne(Long id) {
        log.debug("Request to get LearnerInfo : {}", id);
        LearnerInfo learnerInfo = learnerInfoRepository.findOne(id);
        return learnerInfo;
    }

    /**
     *  Delete the  learnerInfo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LearnerInfo : {}", id);
        learnerInfoRepository.delete(id);
    }
}
