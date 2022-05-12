package com.company.service;

import com.company.UserTestData;
import com.company.model.Role;
import com.company.model.User;
import com.company.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService service;

    @Test
    void create() {
        User created = service.create(UserTestData.getNew());
        int newId = created.id();
        User newUser = UserTestData.getNew();
        newUser.setId(newId);
        UserTestData.USER_MATCHER.assertMatch(created, newUser);
        UserTestData.USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void get() {
        User user = service.get(UserTestData.ADMIN_ID);
        UserTestData.USER_MATCHER.assertMatch(user, UserTestData.admin);
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("admin@gmail.com");
        UserTestData.USER_MATCHER.assertMatch(user, UserTestData.admin);
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        UserTestData.USER_MATCHER.assertMatch(all, UserTestData.user, UserTestData.admin);
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, "  ","password",  Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, " test@gmail.com","password", new Date(), Set.of())));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, " testgmail.com","password", Role.USER)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new User(null, " testgmail.com","pas", Role.USER)));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(UserTestData.NOT_FOUND));
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(null, "user@gmail.com", "newPass", Role.USER)));
    }
}