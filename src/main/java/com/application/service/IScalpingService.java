package com.application.service;

import com.application.model.dto.BookDto;

import java.util.List;

public interface IScalpingService {
    List<BookDto> getDataFromBookStore(int page);

    List<BookDto> getDataFromBookStore(int page, int amount);
}
