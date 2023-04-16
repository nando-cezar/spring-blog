package br.edu.ifba.blog.domain.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.ifba.blog.domain.enums.Category;
import br.edu.ifba.blog.entity.Post;

public record PostResponseDTO(Long id, String title, String text, UserResponseDTO user, Category category) {

    public PostResponseDTO(Post post){
        this(post.getId(), post.getTitle(), post.getText(), UserResponseDTO.toDto(post.getUser()), post.getCategory());
    }

    public static List<PostResponseDTO> toListDto(List<Post> list){
        return list.stream().map(PostResponseDTO::new).collect(Collectors.toList());
    }

    public static PostResponseDTO toDto(Post data) {
        return new PostResponseDTO(data.getId(), data.getTitle(), data.getText(), UserResponseDTO.toDto(data.getUser()), data.getCategory());
    }

}
