package dto;

import lombok.*;

//{
//        "email": "string",
//        "password": "string"
//        }
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class AuthRequestDto {

    String email;
    String password;
}
