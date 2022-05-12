package com.company.web.user;

import com.company.model.User;
import com.company.web.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends AbstractUserController {
    static final String REST_URL = "/admin/users";

    @GetMapping
    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    @Override
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody @Valid User user) {
        User created = super.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable int id) {
        super.delete(SecurityUtil.authUserId());
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)

    @Override
    @GetMapping("/by")
    public User getByMail(@RequestParam String email) {
        return super.getByMail(email);
    }

    @Override
    @GetMapping("/{id}/with-posts")
    public User getWithPosts(@PathVariable int id) {
        return super.getWithPosts(id);
    }
}
