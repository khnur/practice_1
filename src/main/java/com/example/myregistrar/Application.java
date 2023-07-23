package com.example.myregistrar;

import com.example.myregistrar.dtos.BookDto;
import com.example.myregistrar.dtos.CourseDto;
import com.example.myregistrar.dtos.StudentDto;
import com.example.myregistrar.services.BookService;
import com.example.myregistrar.services.CourseService;
import com.example.myregistrar.services.StudentService;
import com.example.myregistrar.util.CLI;
import com.example.myregistrar.util.DateMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(
            CLI cli,
            StudentService studentService,
            CourseService courseService,
            BookService bookService
    ) {
        return args -> {
            studentService.createRandomStudents(8);
            courseService.createRandomCourses(7);
            bookService.createRandomBooks(10);

            List.of(
                    new StudentDto(
                            "aaa",
                            "bbb",
                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
                            "M"
                    ),
                    new StudentDto(
                            "aaa",
                            "ppp",
                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
                            "M"
                    )
            ).forEach(studentDto -> studentService.createStudent(studentDto.toStudent()));

            List.of(
                    new CourseDto(
                            "aaa",
                            "ppp",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    ),
                    new CourseDto(
                            "bbb",
                            "ppp",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    ),
                    new CourseDto(
                            "qqq",
                            "ppp",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    ),
                    new CourseDto(
                            "www",
                            "ppp",
                            "wdafdsfd",
                            "wdefsvfd",
                            7
                    )
            ).forEach(courseDto -> courseService.createCourse(courseDto.toCourse()));

            List.of(
                    new BookDto(
                            "nnn",
                            "mmm",
                            "mmm",
                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
                            "qwfewrf"
                    ),
                    new BookDto(
                            "nnn",
                            "qqq",
                            "mmm",
                            DateMapper.DATE_FORMAT.parse("1234-14-74"),
                            "qwfewrf"
                    )
            ).forEach(bookDto -> bookService.createBook(bookDto.toBook()));

            courseService.assignBooksToCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    bookService.getBooksByName("nnn")
            );

            courseService.assignStudentsToCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    studentService.getAllStudents()
            );


            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    courseService.getCourseByNameAndUniversity("bbb", "ppp")
            );

            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    courseService.getCourseByNameAndUniversity("qqq", "ppp")
            );

            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseByNameAndUniversity("qqq", "ppp"),
                    courseService.getCourseByNameAndUniversity("www", "ppp")
            );

            courseService.assignCoursePreRequisiteCourse(
                    courseService.getCourseByNameAndUniversity("www", "ppp"),
                    courseService.getCourseByNameAndUniversity("bbb", "ppp")
            );

            courseService.removeCoursePreRequisiteFromCourse(
                    courseService.getCourseByNameAndUniversity("aaa", "ppp"),
                    courseService.getCourseByNameAndUniversity("bbb", "ppp")
            );

            cli.init();
        };
    }
}
