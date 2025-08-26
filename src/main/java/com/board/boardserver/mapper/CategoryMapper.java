package com.board.boardserver.mapper;

import com.board.boardserver.dto.CategoryDTO;

public interface CategoryMapper {

    public int register(CategoryDTO categoryDTO);
    public void updateCategory(CategoryDTO categoryDTO);
    public void deleteCategory(int categoryId);


}
