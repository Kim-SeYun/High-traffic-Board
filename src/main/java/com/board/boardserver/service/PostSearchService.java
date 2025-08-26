package com.board.boardserver.service;

import com.board.boardserver.dto.PostDTO;
import com.board.boardserver.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {

    List<PostDTO> getPosts(PostSearchRequest postSearchRequest);

    List<PostDTO> getPostByTag(String tagName);
}
