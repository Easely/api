package com.shepherdjerred.easely.api.provider.easel.scraper;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class LoginScraper {

    private static final String BASE_URL = "https://cs.harding.edu/easel";
    private static final String LOGIN_URL = BASE_URL + "/cgi-bin/proc_login";

    private Map<String, String> cookies;

    public LoginScraper() {
        cookies = new HashMap<>();
    }

    public void login() {
        try {
            Connection.Response loginResponse = Jsoup.connect(LOGIN_URL)
                    .data("user", "jshepherd")
                    .data("passwd", "password")
                    .method(Connection.Method.POST)
                    .execute();
            cookies = loginResponse.cookies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getCookies() {
        return cookies;
    }
}
