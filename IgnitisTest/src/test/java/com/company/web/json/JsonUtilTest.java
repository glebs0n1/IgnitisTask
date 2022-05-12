package com.company.web.json;

import com.company.PostTestData;
import com.company.model.Post;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JsonUtilTest {
    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(PostTestData.adminPost1);
        System.out.println(json);
        Post post = JsonUtil.readValue(json, Post.class);
        PostTestData.POST_MATCHER.assertMatch(post, PostTestData.adminPost1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(PostTestData.adminPosts);
        System.out.println(json);
        List<Post> posts = JsonUtil.readValues(json, Post.class);
        PostTestData.POST_MATCHER.assertMatch(posts, PostTestData.adminPosts);
    }
}
