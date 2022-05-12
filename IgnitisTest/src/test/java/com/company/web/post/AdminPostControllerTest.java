package com.company.web.post;

import com.company.PostTestData;
import com.company.TestUtil;
import com.company.UserTestData;
import com.company.model.Post;
import com.company.service.PostService;
import com.company.util.exception.NotFoundException;
import com.company.web.AbstractControllerTest;
import com.company.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminPostControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminPostController.REST_URL + '/';

    @Autowired
    private PostService postService;

    @Test
    void deleteByPostId() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + PostTestData.ADMIN_POST_ID)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> postService.get(PostTestData.ADMIN_POST_ID, UserTestData.ADMIN_ID));
    }
    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PostTestData.ADMIN_POST_ID)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PostTestData.POST_MATCHER.contentJson(PostTestData.adminPost1));
    }
    @Test
    void getAllByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "byuser/" + UserTestData.USER_ID)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PostTestData.POST_MATCHER.contentJson(PostTestData.post1));
    }
    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PostTestData.POST_MATCHER.contentJson(PostTestData.post1, PostTestData.adminPost1));
    }
    @Test
    void update() throws Exception {
        Post updatedPost = PostTestData.getUpdated();
        updatedPost.setId(PostTestData.ADMIN_POST_ID);
        perform(MockMvcRequestBuilders.put(REST_URL + PostTestData.ADMIN_POST_ID).contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.admin))
                .content(JsonUtil.writeValue(updatedPost)))
                .andDo(print())
                .andExpect(status().isNoContent());

        PostTestData.POST_MATCHER.assertMatch(postService.get(PostTestData.ADMIN_POST_ID, UserTestData.ADMIN_ID), updatedPost);
    }
    @Test
    void createWithLocation() throws Exception {
        Post newPost = PostTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.admin))
                .content(JsonUtil.writeValue(newPost)))
                .andExpect(status().isCreated());

        Post created = TestUtil.readFromJson(action, Post.class);
        int newId = created.id();
        newPost.setId(newId);
        PostTestData.POST_MATCHER.assertMatch(created, newPost);
        PostTestData.POST_MATCHER.assertMatch(postService.get(newId, UserTestData.ADMIN_ID), newPost);
    }
}