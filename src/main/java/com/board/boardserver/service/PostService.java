package com.board.boardserver.service;

import com.board.boardserver.dto.CommentDTO;
import com.board.boardserver.dto.PostDTO;
import com.board.boardserver.dto.TagDTO;

import java.util.List;

public interface PostService {

    void register(String id, PostDTO postDTO);

    List<PostDTO> getMyPosts(int accountId);

    void updatePosts(PostDTO postDTO);

    void deletePosts(int userId, int postId);

    void registerComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deletePostComment(int userId, int commentId);

    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deletePostTag(int userId, int tagId);
}
