package scheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.DateDto;
import schedulerdto.RecordsDto;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class DeleteRecordByIdTests {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
   int id;


    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI ="https://super-scheduler-app.herokuapp.com/";
        RestAssured.basePath ="api";

        RecordsDto record = RecordsDto.builder()
                .breaks(1)
                .currency("CRN")
                .date(DateDto.builder().dayOfMonth(1).dayOfWeek("1").month(1).year(2022).build())
                .hours(4)
                .id(0)
                .timeFrom("18:00")
                .timeTo("21:00")
                .title("Title")
                .type("Type")
                .totalSalary(300)
                .wage(100)
                .build();

        id = given().body(record)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .post("record")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");

    }

    @Test
    public void deleteRecordById(){

        String  status = given().header("Authorization",token)
                .when()
                .delete("record/"+id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("status",containsString(id +" was deleted"))
                .extract().path("status");
        System.out.println(status);
    }

    @Test
    public void deleteRecordWrongId(){
        given().header("Authorization",token)
                .when()
                .delete("record/"+11)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message",containsString("11 doesn't exist!"));


        //Record with id {id} doesn’t exist!

    }

}
