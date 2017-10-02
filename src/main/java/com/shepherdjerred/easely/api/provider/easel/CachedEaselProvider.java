package com.shepherdjerred.easely.api.provider.easel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.provider.Provider;
import com.shepherdjerred.easely.api.provider.easel.scraper.AssignmentScraper;
import com.shepherdjerred.easely.api.provider.easel.scraper.CourseScraper;
import com.shepherdjerred.easely.api.provider.easel.scraper.LoginScraper;
import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Log4j2
public class CachedEaselProvider implements Provider {

    private RedissonClient redisson;

    public CachedEaselProvider() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Config config = getRedissonConfig();
        config.setCodec(new JsonJacksonCodec(mapper));
        redisson = Redisson.create(config);
    }

    private Config getRedissonConfig() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Config config;
        if (processBuilder.environment().get("REDIS_URL") != null) {
            String redisUrl = processBuilder.environment().get("REDIS_URL");

            String password = redisUrl.substring(redisUrl.indexOf(':', redisUrl.indexOf(':') + 1) + 1, redisUrl.indexOf('@'));

            log.debug(redisUrl);
            log.debug(password);

            config = new Config();
            config.useSingleServer().setAddress(redisUrl).setPassword(password);
        } else {
            try {
                config = Config.fromJSON(new File("redissonConfig.json"));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return config;
    }

    private Map<String, String> login(User user) {
        Map<String, String> cookies;
        RBucket<Map<String, String>> cookiesBucket = redisson.getBucket("user:" + user.getUuid() + ":cookies");

        if (cookiesBucket.isExists()) {
            cookies = cookiesBucket.get();
        } else {
            LoginScraper loginScraper = new LoginScraper();
            loginScraper.login(user.getEaselUsername(), user.getEaselPassword());
            cookies = loginScraper.getCookies();
            cookiesBucket.set(cookies);
        }

        return cookies;
    }

    @Override
    public Collection<Course> getCourses(User user) {
        Collection<Course> courses;
        Collection<Course> userCourses;
        RBucket<Collection<Course>> coursesBucket = redisson.getBucket("courses");
        RBucket<Collection<Course>> userCoursesBucket = redisson.getBucket("user:" + user.getUuid() + ":courses");

        if (coursesBucket.isExists()) {
            courses = coursesBucket.get();
        } else {
            Map<String, String> cookies = login(user);
            courses = new CourseScraper().getCourses(cookies);
            coursesBucket.set(courses);
        }

        if (userCoursesBucket.isExists()) {
            userCourses = userCoursesBucket.get();
        } else {
            Map<String, String> cookies = login(user);
            userCourses = new CourseScraper().getCourses(cookies);
            coursesBucket.set(userCourses);
        }

        return userCourses;
    }

    @Override
    public Collection<Assignment> getAssignments(User user) {
        Collection<Assignment> assignments = new ArrayList<>();
        getCourses(user).forEach(course -> {
            getAssignments(user, course).forEach(assignment -> {
                assignments.add(assignment);
            });
        });
        return assignments;
    }

    @Override
    public Collection<Assignment> getAssignments(User user, Course course) {
        Collection<Assignment> assignments;
        RBucket<Collection<Assignment>> assignmentsBucket = redisson.getBucket("course:" + course.getId() + ":assignments");

        if (assignmentsBucket.isExists()) {
            assignments = assignmentsBucket.get();
        } else {
            Map<String, String> cookies = login(user);

            assignments = new AssignmentScraper().getAssignmentsForCourse(cookies, course);
            assignmentsBucket.set(assignments);
        }

        return assignments;
    }
}
