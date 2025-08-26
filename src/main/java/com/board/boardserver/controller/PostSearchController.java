package com.board.boardserver.controller;

import com.board.boardserver.dto.PostDTO;
import com.board.boardserver.dto.request.PostSearchRequest;
import com.board.boardserver.service.impl.PostSearchServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postSearchRequest){
        List<PostDTO> postDTOList = postSearchService.getPosts(postSearchRequest);
        return new PostSearchResponse(postDTOList);
    }

    // --response 객체 --
    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse{
        private List<PostDTO> postDTOList;
    }

    @GetMapping
    public PostSearchResponse searchByTagName(String tagName){
        System.out.println("tagName = " + tagName);
        List<PostDTO> postDTOList = postSearchService.getPostByTag(tagName);
        return new PostSearchResponse(postDTOList);
    }

}
