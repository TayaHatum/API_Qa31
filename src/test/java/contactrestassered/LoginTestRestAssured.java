package contactrestassered;

import com.jayway.restassured.RestAssured;
import dto.AuthRequestDto;
import dto.ErrorDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.AuthResponseDto;


import static com.jayway.restassured.RestAssured.given;

public class LoginTestRestAssured {

    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI ="https://contacts-telran.herokuapp.com/";
        RestAssured.basePath ="api";

    }

    @Test
    public void loginTestSuccess() {

        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa12345$").build();

        AuthResponseDto responseDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);

        System.out.println("Token is ---> " +responseDto.getToken());
    }

    @Test
    public void loginWrongPassword()  {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa").build();

        ErrorDto errorDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDto.class);

        Assert.assertEquals(errorDto.getMessage(),"Password length need be 8 or more symbols");
    }

    @Test
    public void loginWrongEmail()  {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noagmail.com")
                .password("Nnoa12345$").build();

        ErrorDto errorDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ErrorDto.class);

        System.out.println( errorDto.getMessage());
        Assert.assertTrue(errorDto.getMessage().contains("Wrong email format! Example: name@mail.com"));
    }
    @Test
    public void loginUnregisteredUser()  {
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noaSnow@gmail.com")
                .password("Nnoa12345$").build();

        ErrorDto errorDto = given()
                .body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);

        System.out.println( errorDto.getMessage());
       Assert.assertTrue(errorDto.getMessage().contains("Wrong email or password!"));
    }
}
