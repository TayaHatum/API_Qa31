package dto;


//{
//        "code": 0,
//        "details": "string",
//        "message": "string",
//        "timestamp": "2022-03-07T17:09:20.356Z"
//        }

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ErrorDto {

    int code;
    String  details;
    String message;

}
