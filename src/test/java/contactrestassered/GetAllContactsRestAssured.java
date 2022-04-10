package contactrestassered;

import com.jayway.restassured.RestAssured;
import dto.ContactDto;
import dto.ErrorDto;
import dto.GetAllContactDto;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;


public class GetAllContactsRestAssured {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";

    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI ="https://contacts-telran.herokuapp.com/";
        RestAssured.basePath ="api";


    }

    @Test
    public void getAllContactSuccess() {

       GetAllContactDto all = given().header("Authorization",token)
                .when()
                .get("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(GetAllContactDto.class);
        List<ContactDto> contacts = all.getContacts();
        for (ContactDto contact:contacts){
            System.out.println(contact.toString());
            System.out.println("********");
            System.out.println(contact.getId());
        }



    }
    @Test
    public void getAllContactNegative(){

       ErrorDto errorDto =  given().header("Authorization","dfmbfkj")
                .when()
                .get("contact")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);
        System.out.println(errorDto.getMessage());
        Assert.assertEquals(errorDto.getMessage(),"Wrong token format!");

    }

}
