package exercise;

import java.util.List;
import java.util.Arrays;

// BEGIN
class App {
    public static void main(String args[]) {}

    public static long getCountOfFreeEmails(List<String> emails) {
        List<String> freeEmails = List.of("gmail.com", "yandex.ru", "hotmail.com");
        return emails.stream()
                .filter(email -> freeEmails.contains(email.split("@")[1]))
                .count();
    }
}

// END
