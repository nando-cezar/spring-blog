package br.edu.ifba.blog.domain.dto.request;

import br.edu.ifba.blog.entity.User;

public record UserRequestDTO(Long id, String name, String login, String password) {

    public UserRequestDTO(User data){
        this(data.getId(), data.getName(), data.getLogin(), data.getPassword());
    }

    public User toEntity(){
        if(id == null) return new User(name, login, password);
        return new User(id, name, login, password);
    }

    public static UserRequestDTO toDto(User data) {
        return new UserRequestDTO(data);
    }
}
