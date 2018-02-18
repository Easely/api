package com.shepherdjerred.easely.api.easel.scraper.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shepherdjerred.easely.api.config.EaselyConfig;
import com.shepherdjerred.easely.api.CacheException;
import com.shepherdjerred.easely.api.easel.scraper.model.*;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.model.UserCourseGrade;
import lombok.AllArgsConstructor;
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

@Log4j2
@AllArgsConstructor
public class RedisScraperCache implements ScraperCache {
    private RedissonClient redisson;

    public RedisScraperCache(EaselyConfig easelyConfig) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        org.redisson.config.Config config = easelyConfig.getRedissonConfig();
        config.setCodec(new JsonJacksonCodec(mapper));
        redisson = Redisson.create(config);
    }

    @Override
    public void setUserEaselCookies(User user, Map<String, String> cookies) {
        RBucket<Map<String, String>> cookiesBucket = redisson.getBucket("user:" + user.getUuid() + ":cookies");
        cookiesBucket.set(cookies);
        cookiesBucket.expire(1, TimeUnit.HOURS);
    }

    @Override
    public void setUserEaselId(User user, String userEaselId) {
        RBucket<String> easelUserIdBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":id");
        easelUserIdBucket.set(userEaselId);
    }

    @Override
    public void setUserCourseCores(User user, Collection<CourseCore> courses) {
        RBucket<Collection<CourseCore>> userCoursesBucket = redisson.getBucket("user:" + user.getUuid() + ":courses");
        userCoursesBucket.set(courses);
        userCoursesBucket.expire(24, TimeUnit.DAYS);
    }

    @Override
    public void setCourseDetails(CourseCore courseCore, CourseDetails courseDetails) {
        RBucket<CourseDetails> courseDetailsBucket = redisson.getBucket("course:details:" + courseCore.getId());
        courseDetailsBucket.set(courseDetails);
        courseDetailsBucket.expire(7, TimeUnit.DAYS);
    }

    @Override
    public void setUserCourseGrade(User user, CourseCore courseCore, UserCourseGrade courseGrade) {
        RBucket<UserCourseGrade> courseGradeBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":course:" + courseCore.getId() + ":grade");
        courseGradeBucket.set(courseGrade);
        courseGradeBucket.expire(1, TimeUnit.DAYS);
    }

    @Override
    public void setCourseAssignmentCores(CourseCore courseCore, Collection<AssignmentCore> assignments) {
        RBucket<Collection<AssignmentCore>> courseAssignmentsBucket = redisson.getBucket("course:" + courseCore.getId() + ":assignments");
        courseAssignmentsBucket.set(assignments);
        courseAssignmentsBucket.expire(1, TimeUnit.DAYS);
    }

    @Override
    public void setAssignmentDetails(AssignmentCore assignmentCore, AssignmentDetails assignmentDetails) {
        RBucket<AssignmentDetails> assignmentDetailsBucket = redisson.getBucket("assignment:details:" + assignmentCore.getId());
        assignmentDetailsBucket.set(assignmentDetails);
        if (assignmentCore.getDate().isAfter(LocalDate.now())) {
            assignmentDetailsBucket.expire(1, TimeUnit.DAYS);
        }
    }

    @Override
    public void setUserAssignmentGrade(User user, AssignmentCore assignmentCore, UserAssignmentGrade assignmentGrade) {
        RBucket<UserAssignmentGrade> assignmentGradeBucket = redisson.getBucket("user:" + user.getUuid() + ":assignment:" + assignmentCore.getId() + ":grade");
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
    public boolean hasUserEaselId(User user) {
        RBucket<String> easelUserIdBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":id");
        return easelUserIdBucket.isExists();
    }

    @Override
    public boolean hasUserCourseCores(User user) {
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
        RBucket<UserCourseGrade> courseGradeBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":course:" + courseCore.getId() + ":grade");
        return courseGradeBucket.isExists();
    }

    @Override
    public boolean hasCourseAssignmentCores(CourseCore courseCore) {
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
        RBucket<UserAssignmentGrade> assignmentGradeBucket = redisson.getBucket("user:" + user.getUuid() + ":assignment:" + assignmentCore.getId() + ":grade");
        return assignmentGradeBucket.isExists();
    }

    @Override
    public Map<String, String> getUserEaselCookies(User user) throws CacheException {
        RBucket<Map<String, String>> cookiesBucket = redisson.getBucket("user:" + user.getUuid() + ":cookies");
        if (!cookiesBucket.isExists()) {
            throw new CacheException();
        }
        log.trace(cookiesBucket.get());
        return cookiesBucket.get();
    }

    @Override
    public String getUserEaselId(User user) throws CacheException {
        RBucket<String> easelUserIdBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":id");
        if (!easelUserIdBucket.isExists()) {
            throw new CacheException();
        }
        log.trace(easelUserIdBucket.get());
        return easelUserIdBucket.get();
    }

    @Override
    public Collection<CourseCore> getUserCourseCores(User user) throws CacheException {
        RBucket<Collection<CourseCore>> userCoursesBucket = redisson.getBucket("user:" + user.getUuid() + ":courses");
        if (!userCoursesBucket.isExists()) {
            throw new CacheException();
        }
        log.trace(userCoursesBucket.get());
        return userCoursesBucket.get();
    }

    @Override
    public CourseDetails getCourseDetails(CourseCore courseCore) throws CacheException {
        RBucket<CourseDetails> courseDetailsBucket = redisson.getBucket("course:details:" + courseCore.getId());
        if (!courseDetailsBucket.isExists()) {
            throw new CacheException();
        }
        log.trace(courseDetailsBucket.get());
        return courseDetailsBucket.get();
    }

    @Override
    public UserCourseGrade getUserCourseGrade(User user, CourseCore courseCore) throws CacheException {
        RBucket<UserCourseGrade> courseGradeBucket = redisson.getBucket("user:uuid:" + user.getUuid() + ":course:" + courseCore.getId() + ":grade");
        if (!courseGradeBucket.isExists()) {
            throw new CacheException();
        }
        log.trace(courseGradeBucket.get());
        return courseGradeBucket.get();
    }

    @Override
    public Collection<AssignmentCore> getCourseAssignmentCores(CourseCore courseCore) throws CacheException {
        RBucket<Collection<AssignmentCore>> courseAssignmentsBucket = redisson.getBucket("course:" + courseCore.getId() + ":assignments");
        if (!courseAssignmentsBucket.isExists()) {
            throw new CacheException();
        }
        log.trace(courseAssignmentsBucket.get());
        return courseAssignmentsBucket.get();
    }

    @Override
    public AssignmentDetails getAssignmentDetails(AssignmentCore assignmentCore) throws CacheException {
        RBucket<AssignmentDetails> assignmentDetailsBucket = redisson.getBucket("assignment:details:" + assignmentCore.getId());
        if (!assignmentDetailsBucket.isExists()) {
            throw new CacheException();
        }
        log.trace(assignmentDetailsBucket.get());
        return assignmentDetailsBucket.get();
    }

    @Override
    public UserAssignmentGrade getUserAssignmentGrade(User user, AssignmentCore assignmentCore) throws CacheException {
        RBucket<UserAssignmentGrade> assignmentGradeBucket = redisson.getBucket("user:" + user.getUuid() + ":assignment:" + assignmentCore.getId() + ":grade");
        if (!assignmentGradeBucket.isExists()) {
            throw new CacheException();
        }
        log.trace(assignmentGradeBucket.get());
        return assignmentGradeBucket.get();
    }
}
