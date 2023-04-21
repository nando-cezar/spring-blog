package br.edu.ifba.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifba.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
    public List<Post> findByTitleContains(String title);
}
