package com.company.web.user;

import com.company.TestUtil;
import com.company.UserTestData;
import com.company.model.User;
import com.company.service.UserService;
import com.company.util.exception.NotFoundException;
import com.company.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminController.REST_URL + '/';

    @Autowired
    private UserService userService;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.user, UserTestData.admin));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + UserTestData.ADMIN_ID)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.admin));
    }

    @Test
    void createWithLocation() throws Exception {
        User newUser = UserTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.admin))
                .content(UserTestData.jsonWithPassword(newUser, "newPass")))
                .andExpect(status().isCreated());

        User created = TestUtil.readFromJson(action, User.class);
        int newId = created.id();
        newUser.setId(newId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + UserTestData.USER_ID)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userService.get(UserTestData.USER_ID));
    }

    @Test
    void update() throws Exception {
        User updated = UserTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + UserTestData.USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(TestUtil.userHttpBasic(UserTestData.admin))
                .content(UserTestData.jsonWithPassword(updated, "updatedPas")))
                .andExpect(status().is2xxSuccessful());

        UserTestData.USER_MATCHER.assertMatch(userService.get(UserTestData.USER_ID), updated);
    }

    @Test
    void getByMail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "by?email=" + UserTestData.admin.getEmail())
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.admin));
    }

    @Test
    void getWithPosts() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + UserTestData.ADMIN_ID + "/with-posts")
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_WITH_POSTS_MATCHER.contentJson(UserTestData.admin));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.user)))
                .andExpect(status().isForbidden());
    }
}
