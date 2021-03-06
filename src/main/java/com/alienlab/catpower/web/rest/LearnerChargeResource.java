package com.alienlab.catpower.web.rest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.alienlab.catpower.domain.Course;
import com.alienlab.catpower.service.CourseService;
import com.alienlab.catpower.web.rest.util.ExecResult;
import com.codahale.metrics.annotation.Timed;
import com.alienlab.catpower.domain.LearnerCharge;
import com.alienlab.catpower.service.LearnerChargeService;
import com.alienlab.catpower.web.rest.util.HeaderUtil;
import com.alienlab.catpower.web.rest.util.PaginationUtil;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing LearnerCharge.
 */
@RestController
@RequestMapping("/api")
public class LearnerChargeResource {

    private final Logger log = LoggerFactory.getLogger(LearnerChargeResource.class);

    private static final String ENTITY_NAME = "learnerCharge";

    private final LearnerChargeService learnerChargeService;

    @Autowired
    CourseService courseService;
    public LearnerChargeResource(LearnerChargeService learnerChargeService) {
        this.learnerChargeService = learnerChargeService;
    }

    /**
     * POST  /learner-charges : Create a new learnerCharge.
     *
     * @param learnerCharge the learnerCharge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new learnerCharge, or with status 400 (Bad Request) if the learnerCharge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/learner-charges")
    @Timed
    public ResponseEntity<LearnerCharge> createLearnerCharge(@RequestBody LearnerCharge learnerCharge) throws URISyntaxException {
        log.debug("REST request to save LearnerCharge : {}", learnerCharge);
        if (learnerCharge.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new learnerCharge cannot already have an ID")).body(null);
        }
        LearnerCharge result = learnerChargeService.save(learnerCharge);
        return ResponseEntity.created(new URI("/api/learner-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /learner-charges : Updates an existing learnerCharge.
     *
     * @param learnerCharge the learnerCharge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated learnerCharge,
     * or with status 400 (Bad Request) if the learnerCharge is not valid,
     * or with status 500 (Internal Server Error) if the learnerCharge couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/learner-charges")
    @Timed
    public ResponseEntity<LearnerCharge> updateLearnerCharge(@RequestBody LearnerCharge learnerCharge) throws URISyntaxException {
        log.debug("REST request to update LearnerCharge : {}", learnerCharge);
        if (learnerCharge.getId() == null) {
            return createLearnerCharge(learnerCharge);
        }
        LearnerCharge result = learnerChargeService.save(learnerCharge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, learnerCharge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /learner-charges : get all the learnerCharges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of learnerCharges in body
     */
    @GetMapping("/learner-charges")
    @Timed
    public ResponseEntity<List<LearnerCharge>> getAllLearnerCharges(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LearnerCharges");
        Page<LearnerCharge> page = learnerChargeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/learner-charges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /learner-charges/:id : get the "id" learnerCharge.
     *
     * @param id the id of the learnerCharge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the learnerCharge, or with status 404 (Not Found)
     */
    @GetMapping("/learner-charges/{id}")
    @Timed
    public ResponseEntity<LearnerCharge> getLearnerCharge(@PathVariable Long id) {
        log.debug("REST request to get LearnerCharge : {}", id);
        LearnerCharge learnerCharge = learnerChargeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(learnerCharge));
    }

    /**
     * DELETE  /learner-charges/:id : delete the "id" learnerCharge.
     *
     * @param id the id of the learnerCharge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/learner-charges/{id}")
    @Timed
    public ResponseEntity<Void> deleteLearnerCharge(@PathVariable Long id) {
        log.debug("REST request to delete LearnerCharge : {}", id);
        learnerChargeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @ApiOperation("根据排课获取已签到学员名单")
    @ApiImplicitParams({
        @ApiImplicitParam(name="scheId",value="排课编码",paramType = "path")
    })
    @GetMapping("/learner-charges/sche/{scheId}")
    public ResponseEntity getLearnersBySche(@PathVariable Long scheId){
        try {
            List<LearnerCharge> result=learnerChargeService.getLeanersBySche(scheId);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation("学员课程签到，课程核销")
    @PostMapping("/learner-charges/sche")
    public ResponseEntity signCourse(@RequestBody Map param){
        String openid= TypeUtils.castToString(param.get("openid"));
        Long scheId=TypeUtils.castToLong(param.get("scheId"));
        try {
            LearnerCharge charge=learnerChargeService.chargeCourse(openid,scheId);
            return ResponseEntity.ok(charge);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation("测试JSONString")
    @PostMapping("/learner-charges/json")
    public ResponseEntity signCourse(@RequestBody String param){
        JSONObject jo=JSONObject.parseObject(param);
        jo.getString("openid");
        jo.getJSONArray("arr");
        jo.getJSONObject("obj");
        return ResponseEntity.ok("success");
    }
    @ApiOperation("查询学员已经签到的课程")
    @GetMapping("/learner-charges/stusign/{learnerId}")
    public ResponseEntity getStuSignCourse(@PathVariable(value = "learnerId") Long learnerId){
        try {
            List<LearnerCharge> learnerCharges = learnerChargeService.findLeanerChargeByLearnerId(learnerId);
            return ResponseEntity.ok().body(learnerCharges);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }

    @ApiOperation("查询学员已经签到的签到记录")
    @PostMapping("/learner-charges/stusign/log")
    public ResponseEntity getStuSignLog(@RequestBody Map map){
        Long learnerId = TypeUtils.castToLong(map.get("learnerId"));
        Long courseId = TypeUtils.castToLong(map.get("courseId"));
        try {
            List<LearnerCharge> learnerCharges = learnerChargeService.findLearnerChargeByCourseIdAndLearnerId(courseId,learnerId);
            return ResponseEntity.ok().body(learnerCharges);
        } catch (Exception e) {
            e.printStackTrace();
            ExecResult er=new ExecResult(false,e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(er);
        }
    }



}
