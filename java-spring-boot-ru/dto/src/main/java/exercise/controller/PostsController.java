package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private List<CommentDTO> getCommentsByPostId(Long postId) {
       return commentRepository.findByPostId(postId).stream().map(comment -> {
         CommentDTO commentDTO = new CommentDTO();
         commentDTO.setId(comment.getId());
         commentDTO.setBody(comment.getBody());
         return commentDTO;
       }).toList();
    }

    private PostDTO mapPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();

        postDTO.setBody(post.getBody());
        postDTO.setTitle(post.getTitle());
        postDTO.setId(post.getId());

        postDTO.setComments(getCommentsByPostId(post.getId()));

        return postDTO;
    }

    private String getNotFoundMessage(Long id) {
        return "Post with id " + id + " not found.";
    }

    @GetMapping(path = "")
    public List<PostDTO> getPosts() {
        return postRepository.findAll().stream().map(p -> mapPostDTO(p)).toList();
    }

    @GetMapping(path = "/{id}")
    public PostDTO getPost(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(getNotFoundMessage(id)));

        return mapPostDTO(post);
    }
}
// END
