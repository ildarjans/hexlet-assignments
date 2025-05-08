package exercise.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
public class GuestDTO {
    private long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    @Size(min = 12, max = 14)
    @Pattern(regexp = "^\\+.*")
    private String phoneNumber;

    @Pattern(regexp = "\\d{4}")
    private String clubCard;

    @Future
    private LocalDate cardValidUntil;

    private LocalDate createdAt;
}
//Имя пользователя name должно быть не пустым
//Электронная почта email должна быть валидной
//Номер телефона phoneNumber должен начинаться с символа + и содержать от 11 до 13 цифр
//Номер клубной карты clubCard должен состоять ровно из четырех цифр
//Срок действия клубной карты cardValidUntil должен быть еще не истекшим