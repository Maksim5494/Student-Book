import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        StudentFileReader.read(Path.of("students.txt"))
                .stream()
                .peek(System.out::println)
                .flatMap(student -> student.getBooks().stream())
                .distinct()
                .filter(book -> book.getYear() > 2000)
                .sorted()
                .limit(3)
                .map(Book::getYear)
                .findAny()
                .ifPresentOrElse(
                        year -> System.out.println(
                                "Год выпуска найденной книги: " + year
                        ),
                        () -> System.out.println(
                                "Книга, выпущенная после 2000 года, отсутствует"
                        )
                );
    }
}
