package br.edu.ifba.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifba.blog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    public List<Post> findByTitleContains(String title);
}
