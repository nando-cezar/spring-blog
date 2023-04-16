package br.edu.ifba.blog.domain.dto.response;

import br.edu.ifba.blog.entity.User;

public record UserResponseDTO(Long id, String name, String login) {
    
    //public User toEntity(UserResponseDTO data){
    //    return new User(data.name(), data.login(), data.password());
    //}

    public static UserResponseDTO toDto(User data) {
        return new UserResponseDTO(data.getId(), data.getName(), data.getLogin());
    }
}
