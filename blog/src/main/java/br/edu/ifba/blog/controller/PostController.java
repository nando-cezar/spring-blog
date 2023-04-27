package br.edu.ifba.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/posts")
@Tag(name = "Posts")
public class PostController {
    
    @Autowired
    public PostService service;

    @PostMapping
    @Operation(summary = "Save only one post")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "201", 
                description = "Saved with success", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "406", 
                description = "Not Acceptable", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<PostResponseDTO> save(
            @Parameter(description = "New post body content to be created")
            @RequestBody PostRequestDTO data,
            UriComponentsBuilder builder
    ){
        var dataDto = service.save(data);
        var uri = builder.path("/posts/{id}").buildAndExpand(dataDto.id()).toUri();
        return ResponseEntity.created(uri).body(dataDto);
    }

    @GetMapping
    @Operation(summary = "Retrieve all posts with or without filter")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful posts", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<List<PostResponseDTO>> find(
            @Parameter(description = "Title for post to be found (optional)")
            @RequestParam(required = false) String title,
            @RequestParam(required = true, defaultValue = "0") int page,
            @RequestParam(required = true, defaultValue = "10") int size
    ){

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "title"));
        var data = service.find(title, pageable).get();
        var isExists = data.isEmpty();
        return isExists ? 
            ResponseEntity.notFound().build() : 
            ResponseEntity.ok().body(data);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve post by id")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Retrieval of successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<PostResponseDTO> findById(
            @Parameter(description = "Post Id to be searched") @PathVariable Long id
    ) {
        return service.findById(id)
            .map(record -> ResponseEntity.ok().body(record))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update only one tip")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Updated with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<PostResponseDTO> update(
            @Parameter(description = "Post Id to be updated")
            @PathVariable Long id,
            @Parameter(description = "Post Elements/Body Content to be updated")
            @RequestBody PostRequestDTO data
    ){
        return service.findById(id)
        .map(record -> {
            var dataSaved = service.update(id, data);
            return ResponseEntity.ok().body(dataSaved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete only one post")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", 
                description = "Deleted with successful", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Not found", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PostResponseDTO.class)
                    )
                }    
            )
        }
    )
    public ResponseEntity<PostResponseDTO> delete(
            @Parameter(description = "Post Id to be deleted")
            @PathVariable Long id
    ){
        return service.findById(id)
        .map(record -> {
            service.deleteById(id);
            return ResponseEntity.ok().body(record);
        }).orElse(ResponseEntity.notFound().build());
    }
}
