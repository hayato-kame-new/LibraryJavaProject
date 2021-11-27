package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import model.Book;


public class LibraryUtil {


    public List<Book> readFromFile(String fileName){
        List<Book> books = new ArrayList<Book>();
        try(BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), "utf-8"))){
            String line;
            while((line = in.readLine()) != null){
                line = line.trim();
                if(Objects.equals(line, "") || line.startsWith("#")){
                    continue;
                }
                String[] data = line.split(",");
                Book book = this.createBook(data[0], data[1], data[2], data[3]);
                books.add(book);
            }
        } catch(IOException e){
            e.printStackTrace();
            // ignore
        }
        return Collections.unmodifiableList(books);
    }

    @SuppressWarnings("deprecation")
    public Book createBook(String title, String authors, String publisher, String publishYear){
        return this.createBook(title, authors, publisher, new Integer(publishYear));
    }

    public Book createBook(String title, String authors, String publisher, Integer publishYear){
        Book book = new Book();
        book.title = title;
        book.authors = authors;
        book.publisher = publisher;
        book.publishYear = publishYear;

        return book;
    }

}
