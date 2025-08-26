package com.board.boardserver.mapper;

import com.board.boardserver.dto.CommentDTO;

public interface CommentMapper {

    public int register(CommentDTO commentDTO);

    public void updateComments(CommentDTO commentDTO);

    public void deletePostComment(int commentId);
}
