package exercise.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api")
public class PostsController {
    private static List<Post> posts = Data.getPosts();

    @GetMapping("/users/{id}/posts")
    public List<Post> getUserPosts(@PathVariable int id) {
        return posts.stream()
                .filter(p -> p.getUserId() == id)
                .toList();
    }

    @PostMapping("/users/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@PathVariable int id, @RequestBody Post body) {
        Post post = new Post();
        post.setUserId(id);
        post.setSlug(body.getSlug());
        post.setBody(body.getBody());
        post.setTitle(body.getTitle());

        posts.add(post);

        return post;
    }
}
// END
