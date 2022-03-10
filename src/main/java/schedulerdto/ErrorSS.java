package schedulerdto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ErrorSS {
    int code;
    String  details;
    String message;
}
