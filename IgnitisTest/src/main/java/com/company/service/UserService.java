package com.company.service;

import com.company.model.User;
import com.company.repository.UserRepository;
import com.company.to.UserTo;
import com.company.util.ValidationUtil;
import com.company.web.AuthorizedUser;
import com.company.util.UserUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static com.company.util.UserUtil.prepareToSave;
import static com.company.util.ValidationUtil.checkNotFound;
import static com.company.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
    }

    public User get(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return ValidationUtil.checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.findAll();
    }


    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    private User prepareAndSave(User user) {
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    public User getWithPosts(int id) {
        return ValidationUtil.checkNotFoundWithId(repository.getWithPosts(id), id);
    }
}
