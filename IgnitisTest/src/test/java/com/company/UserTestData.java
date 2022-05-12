package com.company;

import com.company.model.AbstractBaseEntity;
import com.company.model.Role;
import com.company.model.User;
import com.company.web.json.JsonUtil;
import org.assertj.core.api.Assertions;

import java.util.Collections;
import java.util.Date;

import static com.company.PostTestData.*;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "registered", "posts", "password");
    public static TestMatcher<User> USER_WITH_POSTS_MATCHER =
            TestMatcher.usingAssertions(User.class,
                    (a, e) -> Assertions.assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "posts.user", "password").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static final int USER_ID = AbstractBaseEntity.START_SEQ;
    public static final int ADMIN_ID = AbstractBaseEntity.START_SEQ ;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID,"FirstUser@gmail.com", "HelloPassword", Role.USER);

    public static final User admin = new User(ADMIN_ID,"BestAdmin@gmail.com", "IamAdmin", Role.ADMIN, Role.USER);

    static {
        user.setPosts(userPosts);
        admin.setPosts(adminPosts);
    }

    public static User getNew() {
        return new User(null, "new@gmail.com", "newPass", new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setPassword("newPass");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(Object user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
