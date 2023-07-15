package com.example.practice_1.services;

import com.example.practice_1.exceptions.BookAlreadyExistsException;
import com.example.practice_1.exceptions.BookNotFoundException;
import com.example.practice_1.models.Book;
import com.example.practice_1.models.Course;
import com.example.practice_1.models.Registration;
import com.example.practice_1.models.Student;
import com.example.practice_1.tables.BookTable;
import com.example.practice_1.tables.CourseTable;
import com.example.practice_1.tables.StudentTable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class BookService extends AbstractService {
    public BookService(StudentTable studentData, CourseTable courseData, BookTable bookData) {
        super(studentData, courseData, bookData);
    }

    public void createBook(Book book) {
        if (bookData.existsByNameAndAuthor(book.getName(), book.getAuthor())) {
            throw new BookAlreadyExistsException("Book with such name and author already exists");
        }
        bookData.save(book);
    }

    public void createRandomBooks(int n) {
        IntStream.range(0, n)
                .filter(i -> {
                    try {
                        createBook(Book.createRandomBook());
                        return true;
                    } catch (Exception ignored) {
                        return false;
                    }
                })
                .forEach(i -> {
                });
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = bookData.findAll();
        if (bookList.isEmpty()) {
            throw new BookNotFoundException("There is no book");
        }
        return bookList;
    }

    public List<Book> getBooksByName(String name) {
        List<Book> bookList = bookData.findBooksByName(name);
        if (bookList.isEmpty()) {
            throw new BookNotFoundException("There is no book with such name");
        }
        return bookList;
    }

    public List<Book> getBooksByAuthor(String author) {
        List<Book> bookList = bookData.findBooksByAuthor(author);
        if (bookList.isEmpty()) {
            throw new BookNotFoundException("There is no book with such author");
        }
        return bookList;
    }

    public Book getBookByNameAndAuthor(String name, String author) {
        return bookData.findBookByNameAndAuthor(name, author)
                .orElseThrow(() -> new BookNotFoundException("There is no book with such name and author"));
    }

    public List<Book> getBooksByStudent(Student student) {
        List<Course> courses = student.getRegistrations().stream()
                .map(Registration::getCourse)
                .toList();
        if (courses.isEmpty()) {
            throw new BookNotFoundException("The student does not have any book");
        }
        return bookData.findBooksByCourseIn(courses);
    }

    public List<Book> getBooksByCourse(Course course) {
        List<Book> booksByCourse = bookData.findBooksByCourse(course);
        if (booksByCourse.isEmpty()) {
            throw new BookNotFoundException("There is no book in this course");
        }
        return booksByCourse;
    }
}
