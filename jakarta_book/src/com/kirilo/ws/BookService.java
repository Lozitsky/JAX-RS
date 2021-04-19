package com.kirilo.ws;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.StreamingOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ApplicationScoped
public class BookService {

    private static final Map<String, Book> bookRepository = new ConcurrentHashMap<>();

    @Inject
    JSonStreamer streamer;

    static {
        Book book1 = new Book(); book1.setId(1); book1.setTitle("Harry Potter");
        Book book2 = new Book(); book2.setId(2); book2.setTitle("The Lord of The Rings");
        Book book3 = new Book(); book3.setId(3); book3.setTitle("The Golden Compass");
        bookRepository.put("1", book1);
        bookRepository.put("2", book2);
        bookRepository.put("3", book3);
    }

    public List<Book> findAllBooks() {
        return new ArrayList<>(bookRepository.values());
    }

    public Book findBookById(String id) {
        return bookRepository.get(id);
    }

    public StreamingOutput createBooksStream() {
        return outputStream -> {

            Stream<Book> bookStream = IntStream.range(0, 50).mapToObj(i -> new Book(i, "Book " + i));

            streamer.getStream(outputStream, bookStream, 100);
//            streamer.getJsonbStream(outputStream, bookStream, 100);
        };
    }

}
