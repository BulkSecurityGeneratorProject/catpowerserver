package com.alienlab.catpower.web.wechat.service.impl;

import com.alienlab.catpower.web.wechat.bean.entity.WechatMaterial;
import com.alienlab.catpower.web.wechat.repository.WechatMaterialRepository;
import com.alienlab.catpower.web.wechat.service.WechatMaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing WechatMaterial.
 */
@Service
public class WechatMaterialServiceImpl implements WechatMaterialService {

    private final Logger log = LoggerFactory.getLogger(WechatMaterialServiceImpl.class);

    @Autowired
    private WechatMaterialRepository wechatMaterialRepository;

    /**
     * Save a wechatMaterial.
     *
     * @param wechatMaterial the entity to save
     * @return the persisted entity
     */
    public WechatMaterial save(WechatMaterial wechatMaterial) {
        log.debug("Request to save WechatMaterial : {}", wechatMaterial);
        WechatMaterial result = wechatMaterialRepository.save(wechatMaterial);
        return result;
    }

    /**
     *  Get all the wechatMaterials.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<WechatMaterial> findAll(Pageable pageable) {
        log.debug("Request to get all WechatMaterials");
        Page<WechatMaterial> result = wechatMaterialRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one wechatMaterial by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public WechatMaterial findOne(Long id) {
        log.debug("Request to get WechatMaterial : {}", id);
        WechatMaterial wechatMaterial = wechatMaterialRepository.findOne(id);
        return wechatMaterial;
    }

    /**
     *  Delete the  wechatMaterial by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete WechatMaterial : {}", id);
        wechatMaterialRepository.delete(id);
    }

    @Override
    public List<WechatMaterial> findMaterialByBtnId(String btnId) {
        return null;
    }
}
