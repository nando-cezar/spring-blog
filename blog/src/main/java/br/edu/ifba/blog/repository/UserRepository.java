package br.edu.ifba.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    public Optional<User> findByLogin(String login);
}
