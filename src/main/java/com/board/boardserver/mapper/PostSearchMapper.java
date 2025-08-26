package com.board.boardserver.mapper;

import com.board.boardserver.dto.PostDTO;
import com.board.boardserver.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchMapper {

    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);

    List<PostDTO> getPostByTag(String tagName);
}
