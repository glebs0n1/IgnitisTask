package com.company.web.user;

import com.company.TestUtil;
import com.company.UserTestData;
import com.company.model.User;
import com.company.service.UserService;
import com.company.to.UserTo;
import com.company.util.UserUtil;
import com.company.web.AbstractControllerTest;
import com.company.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ProfileControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileController.REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.user)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_MATCHER.contentJson(UserTestData.user));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileController.REST_URL))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ProfileController.REST_URL)
                .with(TestUtil.userHttpBasic(UserTestData.admin)))
                .andExpect(status().isNoContent());
        UserTestData.USER_MATCHER.assertMatch(userService.getAll(), UserTestData.admin);
    }
    @Test
    void register() throws Exception {
        UserTo newTo = new UserTo(null, "MyNewemail@gmail.com", "MyNewPassword");
        User newUser = UserUtil.createNewFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(ProfileController.REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = TestUtil.readFromJson(action, User.class);
        int newId = created.id();
        newUser.setId(newId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void getWithPosts() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileController.REST_URL + "/with-posts")
                .with(TestUtil.userHttpBasic(UserTestData.user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.USER_WITH_POSTS_MATCHER.contentJson(UserTestData.user));
    }
}
