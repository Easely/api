package com.shepherdjerred.easely.api.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shepherdjerred.easely.api.config.EaselyConfig;
import com.shepherdjerred.easely.api.model.CourseGrade;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.refresher.scraper.objects.*;
import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// TODO eliminate duplication of bucket definitions

@Log4j2
public class RedisCache implements Cache {

    private RedissonClient redisson;

    public RedisCache(EaselyConfig easelyConfig) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        org.redisson.config.Config config = easelyConfig.getRedissonConfig();
        config.setCodec(new JsonJacksonCodec(mapper));
        redisson = Redisson.create(config);
    }

    @Override
    public void saveUserEaselCookies(User user, Map<String, String> cookies) {
        RBucket<Map<String, String>> cookiesBucket = redisson.getBucket("user:" + user.getUuid() + ":cookies");
        cookiesBucket.set(cookies);
        cookiesBucket.expire(1, TimeUnit.HOURS);
    }

    @Override
    public void saveEaselUserId(User user, String easelUserId) {
        RBucket<String> easelUserIdBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":id");
        easelUserIdBucket.set(easelUserId);
    }

    @Override
    public void saveUserCourses(User user, Collection<CourseCore> courses) {
        RBucket<Collection<CourseCore>> userCoursesBucket = redisson.getBucket("user:" + user.getUuid() + ":courses");
        userCoursesBucket.set(courses);
        userCoursesBucket.expire(24, TimeUnit.DAYS);
    }

    @Override
    public void saveCourseDetails(CourseCore courseCore, CourseDetails courseDetails) {
        RBucket<CourseDetails> courseDetailsBucket = redisson.getBucket("course:details:" + courseCore.getId());
        courseDetailsBucket.set(courseDetails);
        courseDetailsBucket.expire(7, TimeUnit.DAYS);
    }

    @Override
    public void saveUserCourseGrade(User user, CourseCore courseCore, CourseGrade courseGrade) {
        RBucket<CourseGrade> courseGradeBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":course:" + courseCore.getId() + ":grade");
        courseGradeBucket.set(courseGrade);
        courseGradeBucket.expire(1, TimeUnit.DAYS);
    }

    @Override
    public void saveCourseAssignments(CourseCore courseCore, Collection<AssignmentCore> assignments) {
        RBucket<Collection<AssignmentCore>> courseAssignmentsBucket = redisson.getBucket("course:" + courseCore.getId() + ":assignments");
        courseAssignmentsBucket.set(assignments);
        courseAssignmentsBucket.expire(1, TimeUnit.DAYS);
    }

    @Override
    public void saveAssignmentDetails(AssignmentCore assignmentCore, AssignmentDetails assignmentDetails) {
        RBucket<AssignmentDetails> assignmentDetailsBucket = redisson.getBucket("assignment:details:" + assignmentCore.getId());
        assignmentDetailsBucket.set(assignmentDetails);
        if (assignmentCore.getDate().isAfter(LocalDate.now())) {
            assignmentDetailsBucket.expire(1, TimeUnit.DAYS);
        }
    }

    @Override
    public void saveUserAssignmentGrade(User user, AssignmentCore assignmentCore, AssignmentGrade assignmentGrade) {
        RBucket<AssignmentGrade> assignmentGradeBucket = redisson.getBucket("user:" + user.getUuid() + ":assignment:" + assignmentCore.getId() + ":grade");
        assignmentGradeBucket.set(assignmentGrade);
        if (assignmentCore.getDate().isAfter(LocalDate.now())) {
            assignmentGradeBucket.expireAt(Date.valueOf(assignmentCore.getDate()));
        } else {
            if (assignmentGrade.isGraded()) {
                assignmentGradeBucket.expire(3, TimeUnit.DAYS);
            }
        }
    }

    @Override
    public boolean hasUserEaselCookies(User user) {
        RBucket<Map<String, String>> cookiesBucket = redisson.getBucket("user:" + user.getUuid() + ":cookies");
        return cookiesBucket.isExists();
    }

    @Override
    public boolean hasEaselUserId(User user) {
        RBucket<String> easelUserIdBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":id");
        return easelUserIdBucket.isExists();
    }

    @Override
    public boolean hasUserCourses(User user) {
        RBucket<Collection<CourseCore>> userCoursesBucket = redisson.getBucket("user:" + user.getUuid() + ":courses");
        return userCoursesBucket.isExists();
    }

    @Override
    public boolean hasCourseDetails(CourseCore courseCore) {
        RBucket<CourseDetails> courseDetailsBucket = redisson.getBucket("course:details:" + courseCore.getId());
        return courseDetailsBucket.isExists();
    }

    @Override
    public boolean hasUserCourseGrade(User user, CourseCore courseCore) {
        RBucket<CourseGrade> courseGradeBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":course:" + courseCore.getId() + ":grade");
        return courseGradeBucket.isExists();
    }

    @Override
    public boolean hasCourseAssignments(CourseCore courseCore) {
        RBucket<Collection<AssignmentCore>> courseAssignmentsBucket = redisson.getBucket("course:" + courseCore.getId() + ":assignments");
        return courseAssignmentsBucket.isExists();
    }

    @Override
    public boolean hasAssignmentDetails(AssignmentCore assignmentCore) {
        RBucket<AssignmentDetails> assignmentDetailsBucket = redisson.getBucket("assignment:details:" + assignmentCore.getId());
        return assignmentDetailsBucket.isExists();
    }

    @Override
    public boolean hasUserAssignmentGrade(User user, AssignmentCore assignmentCore) {
        RBucket<AssignmentGrade> assignmentGradeBucket = redisson.getBucket("user:" + user.getUuid() + ":assignment:" + assignmentCore.getId() + ":grade");
        return assignmentGradeBucket.isExists();
    }

    @Override
    public Map<String, String> loadUserEaselCookies(User user) {
        RBucket<Map<String, String>> cookiesBucket = redisson.getBucket("user:" + user.getUuid() + ":cookies");
        return cookiesBucket.get();
    }

    @Override
    public String loadEaselUserId(User user) {
        RBucket<String> easelUserIdBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":id");
        return easelUserIdBucket.get();
    }

    @Override
    public Collection<CourseCore> loadUserCourses(User user) {
        RBucket<Collection<CourseCore>> userCoursesBucket = redisson.getBucket("user:" + user.getUuid() + ":courses");
        return userCoursesBucket.get();
    }

    @Override
    public CourseDetails loadCourseDetails(CourseCore courseCore) {
        RBucket<CourseDetails> courseDetailsBucket = redisson.getBucket("course:details:" + courseCore.getId());
        return courseDetailsBucket.get();
    }

    @Override
    public CourseGrade loadCourseGrade(User user, CourseCore courseCore) {
        RBucket<CourseGrade> courseGradeBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":course:" + courseCore.getId() + ":grade");
        return courseGradeBucket.get();
    }

    @Override
    public Collection<AssignmentCore> loadCourseAssignments(CourseCore courseCore) {
        RBucket<Collection<AssignmentCore>> courseAssignmentsBucket = redisson.getBucket("course:" + courseCore.getId() + ":assignments");
        return courseAssignmentsBucket.get();
    }

    @Override
    public AssignmentDetails loadAssignmentDetails(AssignmentCore assignmentCore) {
        RBucket<AssignmentDetails> assignmentDetailsBucket = redisson.getBucket("assignment:details:" + assignmentCore.getId());
        return assignmentDetailsBucket.get();
    }

    @Override
    public AssignmentGrade loadAssignmentGrade(User user, AssignmentCore assignmentCore) {
        RBucket<AssignmentGrade> assignmentGradeBucket = redisson.getBucket("user:" + user.getUuid() + ":assignment:" + assignmentCore.getId() + ":grade");
        return assignmentGradeBucket.get();
    }
}
