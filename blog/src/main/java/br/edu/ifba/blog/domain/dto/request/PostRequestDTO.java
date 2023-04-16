package br.edu.ifba.blog.domain.dto.request;

import br.edu.ifba.blog.domain.enums.Category;
import br.edu.ifba.blog.entity.Post;

public record PostRequestDTO(String title, String text, UserRequestDTO user, Category category) {

    public Post toEntity(){
        return new Post(title, text, user.toEntity(), category);
    }

}
