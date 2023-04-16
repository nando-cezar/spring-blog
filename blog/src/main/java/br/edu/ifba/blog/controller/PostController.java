package br.edu.ifba.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.blog.domain.dto.request.PostRequestDTO;
import br.edu.ifba.blog.domain.dto.response.PostResponseDTO;
import br.edu.ifba.blog.service.PostService;

@RestController
@RequestMapping(path = "/posts")
public class PostController {
    
    @Autowired
    public PostService service;


    @PostMapping
    public ResponseEntity<PostResponseDTO> save(@RequestBody PostRequestDTO data){
        var dataDto = service.save(data);
        return new ResponseEntity<PostResponseDTO>(dataDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> find(@RequestParam(required = false) String title){

        var list = service.find(title);

        if(list.isEmpty()){
            return new ResponseEntity<List<PostResponseDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<PostResponseDTO>>(list, HttpStatus.OK);
    }

    
}
