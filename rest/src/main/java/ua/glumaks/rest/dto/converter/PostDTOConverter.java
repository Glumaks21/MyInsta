package ua.glumaks.rest.dto.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.glumaks.rest.dto.PostDTO;
import ua.glumaks.rest.model.Post;
import ua.glumaks.rest.repository.PostRepository;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PostDTOConverter implements Function<Post, PostDTO> {

    private final PostRepository postRepo;


    @Override
    public PostDTO apply(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getBody(),
                post.getAuthor().getUsername(),
                post.getLocation(),
                postRepo.countLikesById(post.getId())
        );
    }

}
