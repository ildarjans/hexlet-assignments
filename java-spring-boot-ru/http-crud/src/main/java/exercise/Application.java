package exercise;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> getPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit ) {
        int from = page * limit;
        int to = (page + 1) * limit;

        return IntStream.range(from, to).mapToObj(posts::get).collect(Collectors.toList());
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPostById(@PathVariable String id) {
        return posts.stream().filter(post -> post.getId().equals(id)).findFirst();
    }

    @GetMapping("/posts")
    public Post create(@RequestBody Post post) {
        posts.add(post);

        return post;
    }

    @GetMapping("/posts/{id}")
    public Post update(@PathVariable String id, @RequestBody Post data) {
        var post = posts.stream().filter(p -> p.getId().equals(id)).findFirst();

        if (post.isPresent()) {
            post.get().setTitle(data.getTitle());
            post.get().setBody(data.getBody());
        }

        return data;
    }

    @GetMapping("/posts/{id}")
    public void delete(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END
}
