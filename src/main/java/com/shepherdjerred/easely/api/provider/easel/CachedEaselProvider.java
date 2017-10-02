package com.shepherdjerred.easely.api.provider.easel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shepherdjerred.easely.api.object.Assignment;
import com.shepherdjerred.easely.api.object.Course;
import com.shepherdjerred.easely.api.object.User;
import com.shepherdjerred.easely.api.provider.Provider;
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

@Log4j2
public class CachedEaselProvider implements Provider {

    private EaselProvider easelProvider;
    private RedissonClient redisson;

    public CachedEaselProvider() {
        this.easelProvider = new EaselProvider();

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

            String password = redisUrl.substring(redisUrl.indexOf(':') + 1, redisUrl.indexOf('@') - 1);

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

    @Override
    public Collection<Course> getCourses(User user) {
        Collection<Course> courses;
        RBucket<Collection<Course>> coursesBucket = redisson.getBucket("courses:" + user.getUuid());

        if (coursesBucket.isExists()) {
            courses = coursesBucket.get();
        } else {
            courses = easelProvider.getCourses(user);
            coursesBucket.set(courses);
        }

        return courses;
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
        RBucket<Collection<Assignment>> assignmentsBucket = redisson.getBucket("assignments:" + user.getUuid() + ":course:" + course.getId());

        if (assignmentsBucket.isExists()) {
            assignments = assignmentsBucket.get();
        } else {
            assignments = easelProvider.getAssignments(user, course);
            assignmentsBucket.set(assignments);
        }

        return assignments;
    }
}
