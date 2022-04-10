package contactrestassered;

import com.jayway.restassured.RestAssured;
import dto.ContactDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class UpdateExistContactRestAssured {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    int index = (int)(System.currentTimeMillis()/1000)%3600;
    int id;


    ContactDto contact = ContactDto.builder()
            .name("John")
            .lastName("Wick")
            .email("wick"+index+"@gmail.com")
            .phone("33333"+index)
            .address("Haifa")
            .description("friend").build();

    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI ="https://contacts-telran.herokuapp.com/";
        RestAssured.basePath ="api";

        id = given().header("Authorization",token)
                .body(contact)
                .contentType("application/json")
                .when()
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println(id);

    }

    @Test
    public void updateExistContactSuccess(){
        ContactDto contactDto = ContactDto.builder()
                .name("wwwww")
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .id(id)
                .address(contact.getAddress())
                .description(contact.getDescription()).build();


        ContactDto dto = given().header("Authorization",token)
                .body(contactDto)
                .contentType("application/json")
                .when()
                .put("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(ContactDto.class);
        System.out.println(dto.getId());
        Assert.assertEquals(dto.getName(),contactDto.getName());


    }
}
