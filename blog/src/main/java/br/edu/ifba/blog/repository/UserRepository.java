package br.edu.ifba.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.blog.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public Optional<User> findByLogin(String login);
}
