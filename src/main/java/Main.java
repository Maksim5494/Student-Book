import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Student> students = readStudentsFromFile("src/main/resources/students.txt");

        students.stream()
                .peek(System.out::println)
                .map(Student::getBooks)
                .flatMap(List::stream)
                .sorted()
                .distinct()
                .filter(book -> book.getYear() > 2000)
                .limit(3)
                .map(Book::getYear)
                .findFirst()
                .ifPresentOrElse(
                        year -> System.out.println("Год выпуска найденной книги: " + year),
                        () -> System.out.println("Такая книга отсутствует")
                );
    }

    private static List<Student> readStudentsFromFile(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath))
                .stream()
                .map(line -> line.split(";"))
                .map(parts -> new Student(
                        parts[0],
                        Arrays.stream(parts[1].split("\\|"))
                                .map(bookData -> bookData.split(","))
                                .map(bookParts -> new Book(
                                        bookParts[0],
                                        Integer.parseInt(bookParts[1]),
                                        Integer.parseInt(bookParts[2])
                                ))
                                .toList()
                ))
                .toList();
    }
}
