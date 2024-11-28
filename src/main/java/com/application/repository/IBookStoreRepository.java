package com.application.repository;

import com.application.model.dto.BookDto;

import java.util.List;

public interface IBookStoreRepository {
    void saveBooks(List<BookDto> bookDtoList);

    List<BookDto> getBooksList(int chunk, int amount);
}
