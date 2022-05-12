package com.company.web.post;

import com.company.PostTestData;
import com.company.TestUtil;
import com.company.UserTestData;
import com.company.model.Post;
import com.company.service.PostService;
import com.company.web.AbstractControllerTest;
import com.company.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfilePostControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfilePostController.REST_URL + '/';

    @Autowired
    private PostService postService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PostTestData.POST1_ID)
                .with(TestUtil.userHttpBasic(UserTestData.user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PostTestData.POST_MATCHER.contentJson(PostTestData.post1));
    }

    @Test
    void getAllOwnPosts() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PostTestData.POST_MATCHER.contentJson(PostTestData.post1));
    }
    @Test
    void createWithLocation() throws Exception {
        Post newPost = PostTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.user))
                .content(JsonUtil.writeValue(newPost)))
                .andExpect(status().isCreated());

        Post created = TestUtil.readFromJson(action, Post.class);
        int newId = created.id();
        newPost.setId(newId);
        PostTestData.POST_MATCHER.assertMatch(created, newPost);
        PostTestData.POST_MATCHER.assertMatch(postService.get(newId, UserTestData.USER_ID), newPost);
    }
}