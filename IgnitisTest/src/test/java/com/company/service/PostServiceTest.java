package com.company.service;

import com.company.PostTestData;
import com.company.UserTestData;
import com.company.model.Post;
import com.company.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PostServiceTest extends AbstractServiceTest {

    @Autowired
    protected PostService service;

    @Test
    void get() {
        Post post = service.get(PostTestData.POST1_ID, UserTestData.USER_ID);
        PostTestData.POST_MATCHER.assertMatch(post, PostTestData.post1);
    }

    @Test
    void delete() {
        service.delete(PostTestData.POST1_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(PostTestData.POST1_ID, UserTestData.USER_ID));
    }

    @Test
    void deleteByPostId() {
        service.deleteByPostId(PostTestData.POST1_ID + 1);
        assertThrows(NotFoundException.class, () -> service.get(PostTestData.POST1_ID + 1, UserTestData.USER_ID));
    }

    @Test
    void getAllByUserId() {
        List<Post> all = service.getAllByUserId(UserTestData.USER_ID);
        PostTestData.POST_MATCHER.assertMatch(all, PostTestData.adminPost1);
    }

    @Test
    void getAll() {
        List<Post> all = service.getAll();
        PostTestData.POST_MATCHER.assertMatch(all, PostTestData.allPosts);
    }

    @Test
    void update() {
        Post updated = PostTestData.getUpdated();
        service.update(updated, UserTestData.USER_ID);
        PostTestData.POST_MATCHER.assertMatch(service.get(PostTestData.POST1_ID, UserTestData.USER_ID), PostTestData.getUpdated());
    }

    @Test
    void create() {
        Post created = service.create(PostTestData.getNew(), UserTestData.USER_ID);
        int newId = created.id();
        Post newPost = PostTestData.getNew();
        newPost.setId(newId);
        PostTestData.POST_MATCHER.assertMatch(created, newPost);
        PostTestData.POST_MATCHER.assertMatch(service.get(newId, UserTestData.USER_ID), newPost);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(UserTestData.NOT_FOUND, UserTestData.USER_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(UserTestData.NOT_FOUND, UserTestData.USER_ID));
    }
}