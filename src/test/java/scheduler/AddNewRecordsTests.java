package scheduler;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.DateDto;
import schedulerdto.RecordsDto;

import static com.jayway.restassured.RestAssured.given;

public class AddNewRecordsTests {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI ="https://super-scheduler-app.herokuapp.com/";
        RestAssured.basePath ="api";

    }

    @Test
    public void addNewRecordTest(){

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

        int id = given().body(record)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .post("record")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println(id);
    }
}
