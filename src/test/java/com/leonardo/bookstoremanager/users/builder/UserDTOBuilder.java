package com.leonardo.bookstoremanager.users.builder;

import com.leonardo.bookstoremanager.dto.UserDTO;
import com.leonardo.bookstoremanager.enums.Gender;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Leo Test";

    @Builder.Default
    private Integer age = 28;

    @Builder.Default
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String email = "leo@test.com";

    @Builder.Default
    private String userName = "leoTest";

    @Builder.Default
    private String password = "password";

    @Builder.Default
    private LocalDate birthDate = LocalDate.of(1994, 3, 15);

    public UserDTO buildUserDTO(){
        return new UserDTO(
                id,
                name,
                age,
                gender,
                email,
                userName,
                password,
                birthDate
        );
    }
}
