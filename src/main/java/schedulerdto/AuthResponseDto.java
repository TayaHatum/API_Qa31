package schedulerdto;

//"registration": true,
//        "status": "string",
//        "token": "string"

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class AuthResponseDto {
    boolean registration;
    String status;
    String token;
}
