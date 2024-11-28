package com.application;

import com.application.model.dto.BookDto;
import com.application.repository.IBookStoreRepository;
import com.application.repository.impl.BookStoreRepository;
import com.application.service.IScalpingService;
import com.application.service.impl.ScalpingService;

import java.util.List;
import java.util.Scanner;

public class Application {

    private static final IScalpingService scalpingService = new ScalpingService();

    private static final IBookStoreRepository bookStoreRepository = new BookStoreRepository();

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int choose;
        boolean run = true;
        do {
            System.out.print("\n1 - Scalp and store data\n2 - exit\nChoose: ");
            choose = in.nextInt();
            switch (choose) {
                case 1 -> scalpAndSave();
                case 2 -> run = false;
                default -> System.out.println("Try again");
            }
        } while (run);
    }

    public static void scalpAndSave() {
        List<BookDto> bookDtoList = null;
        int choose;
        boolean run = true;

        do {
            System.out.print("\n1 - Scalp data\n2 - Get data from db\n"
                    + "3 - Print received data\n4 - Save data\n\n5 - exit\nChoose: ");
            choose = in.nextInt();
            switch (choose) {
                case 1 -> {
                    System.out.print("\nInput page, from 1 to 20: ");
                    int page = in.nextInt();

                    if (page < 1 || page > 20) {
                        System.out.println("Invalid value");
                    } else {
                        bookDtoList = scalpingService.getDataFromBookStore(page);
                    }

                    System.out.println("Data is successfully stored in locale storage.");
                }
                case 2 -> {
                    System.out.println("\nHint: LIMIT = amount, OFFSET = chunk * amount.");
                    System.out.print("Input chunk, from 0: ");
                    int chunk = in.nextInt();
                    System.out.print("Input amount of rows, from 1: ");
                    int amount = in.nextInt();
                    bookDtoList = bookStoreRepository.getBooksList(chunk, amount);
                    System.out.println("Data is successfully stored in locale storage.");
                }
                case 3 -> {
                    if (bookDtoList == null || bookDtoList.isEmpty()) {
                        System.out.println("\nList is null or empty, try to scalp or get data");
                    } else {
                        bookDtoList.forEach(System.out::println);
                    }
                }
                case 4 -> {
                    if (bookDtoList == null || bookDtoList.isEmpty()) {
                        System.out.println("\nList is null or empty, try to scalp or get data");
                    } else {
                        bookStoreRepository.saveBooks(bookDtoList);
                    }

                    System.out.println("Data is successfully stored in locale storage.");
                }
                case 5 -> run = false;
                default -> System.out.println("Try again");
            }
        } while (run);
    }
}
