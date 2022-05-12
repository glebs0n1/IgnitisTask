package com.company;

import com.company.model.AbstractBaseEntity;
import com.company.model.Post;

import java.util.List;

public class PostTestData {
    public static final TestMatcher<Post> POST_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Post.class, "user");

    public static final int NOT_FOUND = 10;
    public static final int POST1_ID = AbstractBaseEntity.START_SEQ ;
    public static final int ADMIN_POST_ID = AbstractBaseEntity.START_SEQ;

    public static final Post post1 = new Post(POST1_ID, "User Post_1", "User Hello");

    public static final Post adminPost1 = new Post(ADMIN_POST_ID, "Admin Post_1", "Best admin hey");

    public static final List<Post> userPosts = List.of(post1);
    public static final List<Post> adminPosts = List.of(adminPost1);

    public static final List<Post> allPosts = List.of(post1, adminPost1);

    public static Post getNew() {
        return new Post(null, "new post TITLE", "new post TEXT");
    }

    public static Post getUpdated() {
        return new Post(POST1_ID, post1.getTitle()+" Updated", "updated Text");
    }
}
