package br.edu.ifba.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifba.blog.domain.dto.request.PostRequestDTO;
import br.edu.ifba.blog.domain.dto.response.PostResponseDTO;
import br.edu.ifba.blog.entity.Post;
import br.edu.ifba.blog.repository.PostRepository;
import br.edu.ifba.blog.repository.UserRepository;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostResponseDTO save(PostRequestDTO data){
        return this.persist(data.toEntity());
    }

    public Optional<List<PostResponseDTO>> find(String title, Pageable pageable){
        
        if(title == null){
            var data = PostResponseDTO.toListDto(postRepository.findAll(pageable).toList());
            return Optional.of(data);
        }

        var data = PostResponseDTO.toListDto(postRepository.findByTitleContains(title, pageable));
        return Optional.of(data);
    }

    public Optional<PostResponseDTO> findById(Long id) {
        return postRepository.findById(id).map(PostResponseDTO::new);
    }

    public PostResponseDTO update(Long id, PostRequestDTO entity){
        var data = entity.toEntity();
        data.setId(id);
        return this.persist(data);
    } 

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    private PostResponseDTO persist(Post data){
        var searchedUser = userRepository.findByLogin(data.getUser().getLogin());

        if(searchedUser.isPresent()){
            var user = searchedUser.get();
            data.setUser(user);
            return PostResponseDTO.toDto(postRepository.save(data));
        }

        return PostResponseDTO.toDto(postRepository.save(data));
    }

}
