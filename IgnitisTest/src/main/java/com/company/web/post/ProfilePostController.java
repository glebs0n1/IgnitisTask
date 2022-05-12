package com.company.web.post;

import com.company.model.Post;
import com.company.web.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = ProfilePostController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfilePostController extends AbstractPostController{
    static final String REST_URL = "/profile/posts";

    @Override
    @GetMapping("/{id}")
    public Post get(@PathVariable int id) {
        return super.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {

        super.delete(SecurityUtil.authUserId());
    }

    @GetMapping
    public List<Post> getAllOwnPosts() {
        int userId = SecurityUtil.authUserId();
        return super.getAllByUserId(userId);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Post post, @PathVariable int id) {
        super.update(post, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> createWithLocation(@RequestBody Post post) {
        Post created = super.create(post);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
