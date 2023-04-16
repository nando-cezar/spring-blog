package br.edu.ifba.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifba.blog.domain.dto.request.PostRequestDTO;
import br.edu.ifba.blog.domain.dto.response.PostResponseDTO;
import br.edu.ifba.blog.repository.PostRepository;
import br.edu.ifba.blog.repository.UserRepository;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostResponseDTO save(PostRequestDTO data){

        var searchedUser = userRepository.findByLogin(data.user().login());
        var convertedPost = data.toEntity();

        if(searchedUser.isPresent()){
            var user = searchedUser.get();
            var post = convertedPost;
            post.setUser(user);
            return PostResponseDTO.toDto(postRepository.save(post));
        }

        var savedUser = userRepository.save(data.user().toEntity());
        var post = convertedPost;
        post.setUser(savedUser);
        return PostResponseDTO.toDto(postRepository.save(post));
    }

    public List<PostResponseDTO> find(String title){
        if(title == null){
            return PostResponseDTO.toListDto(postRepository.findAll());
        }
        return PostResponseDTO.toListDto(postRepository.findByTitleContains(title));
    }
}
