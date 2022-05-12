package com.company.web.user;

import com.company.model.User;
import com.company.service.UserService;
import com.company.to.UserTo;
import com.company.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.company.util.ValidationUtil.assureIdConsistent;
import static com.company.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    public List<User> getAll() {
        return service.getAll();
    }

    public User get(int id) {
        return service.get(id);
    }

    public User create(User user) {
        checkNew(user);
        return service.create(user);
    }

    public User create(UserTo userTo) {
        checkNew(userTo);
        return service.create(UserUtil.createNewFromTo(userTo));
    }


    public void update(UserTo userTo, int id) {
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    public User getByMail(String email) {
        return service.getByEmail(email);
    }

    public User getWithPosts(int id) {
        return service.getWithPosts(id);
    }

    protected void delete(int authUserId) {
    }
}
