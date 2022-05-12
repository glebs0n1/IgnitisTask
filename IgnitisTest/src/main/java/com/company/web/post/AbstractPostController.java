package com.company.web.post;

import com.company.model.Post;
import com.company.service.PostService;
import com.company.util.ValidationUtil;
import com.company.web.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractPostController {

    @Autowired
    private PostService service;

    public Post get(int id) {
        int userId = SecurityUtil.authUserId();
        return service.get(id, userId);
    }

    public List<Post> getAllByUserId(int userId) {
        return service.getAllByUserId(userId);
    }

    public List<Post> getAll() {
        return service.getAll();
    }


    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
    }

    public void deleteByPostId(int id){
        service.deleteByPostId(id);
    }

    public Post create(Post post) {
        int userId = SecurityUtil.authUserId();
        ValidationUtil.checkNew(post);
        return service.create(post, userId);
    }

    public void update(Post post, int id) {
        int userId = SecurityUtil.authUserId();
        ValidationUtil.assureIdConsistent(post, id);
        service.update(post, userId);
    }
}
