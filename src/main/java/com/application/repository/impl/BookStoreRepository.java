package com.application.repository.impl;

import com.application.model.dto.BookDto;
import com.application.model.entity.Book;
import com.application.repository.IBookStoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.logging.Logger;

public class BookStoreRepository implements IBookStoreRepository {

    private static final Logger logger = Logger.getLogger(BookStoreRepository.class.getName());

    private final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("inforce_default");

    @Override
    public void saveBooks(List<BookDto> bookDtoList) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            List<Book> bookList = bookDtoList.stream().map(x -> Book.builder()
                    .title(x.getTitle())
                    .price(x.getPrice())
                    .availability(x.getAvailability())
                    .rating(x.getRating()).build()).toList();

            for (var book : bookList) {
                entityManager.persist(book);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<BookDto> getBooksList(int chunk, int amount) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            List<Book> books = entityManager.createQuery("SELECT b FROM book b", Book.class)
                    .setFirstResult(chunk * amount)
                    .setMaxResults(amount).getResultList();

            return books.stream().map(x -> BookDto.builder()
                    .title(x.getTitle())
                    .price(x.getPrice())
                    .availability(x.getAvailability())
                    .rating(x.getRating())
                    .build()).toList();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return null;
    }
}
