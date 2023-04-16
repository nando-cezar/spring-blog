package br.edu.ifba.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifba.blog.domain.dto.request.PostRequestDTO;
import br.edu.ifba.blog.domain.dto.response.PostResponseDTO;
import br.edu.ifba.blog.service.PostService;
import jakarta.transaction.Transactional;

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
        var data = service.find(title).get();
        var isExists = data.isEmpty();
        return isExists ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> findById(@PathVariable Long id) {
        return service.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<PostResponseDTO> update(@PathVariable Long id, @RequestBody PostRequestDTO data){

        return service.findById(id)
        .map(record -> {
            var dataSaved = service.update(id, data);
            return ResponseEntity.ok().body(dataSaved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity<PostResponseDTO> delete(@PathVariable Long id){

        return service.findById(id)
        .map(record -> {
            var data = record;
            service.deleteById(id);
            return ResponseEntity.ok().body(data);
        }).orElse(ResponseEntity.notFound().build());
    }
}
