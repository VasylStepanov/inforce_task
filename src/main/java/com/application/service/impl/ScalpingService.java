package com.application.service.impl;

import com.application.model.dto.BookDto;
import com.application.service.IScalpingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ScalpingService implements IScalpingService {

    private static final Logger logger = Logger.getLogger(ScalpingService.class.getName());

    private static final String bookStoreUrl = "https://books.toscrape.com/catalogue/page-%n.html";

    @Override
    public List<BookDto> getDataFromBookStore(int page) {
        Elements elements = parseElements(page);
        return getDataFromBookStore(elements, elements.size());
    }

    @Override
    public List<BookDto> getDataFromBookStore(int page, int amount) {
        Elements elements = parseElements(page);
        return getDataFromBookStore(elements, amount);
    }

    private List<BookDto> getDataFromBookStore(Elements elements, int amount) {
        List<BookDto> books = new ArrayList<>(elements.size());
        for (var element : elements) {
            books.add(getBookDto(element));
        }
        return books;
    }

    private Elements parseElements(int page) {
        try {
            Document document = Jsoup.connect(bookStoreUrl
                    .replace("%n", String.valueOf(page))).get();
            return document.select("article.product_pod");
        } catch (IOException e) {
            logger.warning(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private BookDto getBookDto(Element element) {
        return BookDto.builder()
                .title(element.select("h3 a").attr("title"))
                .price(Double.parseDouble(element.select("p.price_color")
                        .text().replaceAll("[^0-9.]", "")))
                .availability(element.select("p.instock.availability").is("In stock"))
                .rating(parseRating(element.select("p.star-rating")
                        .attr("class").replace("star-rating", "").trim()))
                .build();
    }

    private Short parseRating(String rate) {
        short value;
        switch (rate) {
            case "One" -> value = 1;
            case "Two" -> value = 2;
            case "Three" -> value = 3;
            case "Four" -> value = 4;
            case "Five" -> value = 5;
            default -> value = 0;
        };
        return value;
    }
}
