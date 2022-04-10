package contactrestassered;

import com.jayway.restassured.RestAssured;
import dto.ContactDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class DeleteContactByIdRestAssured {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    int id;
    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI ="https://contacts-telran.herokuapp.com/";
        RestAssured.basePath ="api";


        ContactDto contact = ContactDto.builder()
                .name("John")
                .lastName("Wick")
                .email("wick@gmail.com")
                .phone("3333333333")
                .address("Haifa")
                .description("friend").build();

        id = given().header("Authorization",token)
                .body(contact)
                .contentType("application/json")
                .when()
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");

    }

    @Test

    public void deleteByIdSuccess(){

        String  status = given().header("Authorization",token)
                .when()
                .delete("contact/"+id)
                .then()
                .assertThat().statusCode(200)
                .extract().path("status");
        System.out.println(status);
        Assert.assertEquals(status,"Contact was deleted!");
    }
}
