package com.shepherdjerred.easely.api.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shepherdjerred.easely.api.config.EaselyConfig;
import com.shepherdjerred.easely.api.model.CourseGrade;
import com.shepherdjerred.easely.api.model.User;
import com.shepherdjerred.easely.api.refresher.scraper.objects.*;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    public void saveUserId(User user, String userId) {

    }

    @Override
    public void saveUserCourses(User user, Collection<CourseCore> courses) {

    }

    @Override
    public void saveCourseDetails(CourseCore courseCore, CourseDetails courseDetails) {

    }

    @Override
    public void saveUserCourseGrade(User user, CourseCore courseCore, CourseGrade courseGrade) {

    }

    @Override
    public void saveCourseAssignments(CourseCore courseCore, Collection<AssignmentCore> assignments) {

    }

    @Override
    public void saveAssignmentDetails(AssignmentCore assignmentCore, AssignmentDetails assignmentDetails) {

    }

    @Override
    public void saveUserAssignmentGrade(User user, AssignmentCore assignmentCore, AssignmentGrade assignmentGrade) {

    }
}
