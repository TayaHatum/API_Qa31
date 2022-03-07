package contactokhttp;

import com.google.gson.Gson;
import dto.ContactDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddNewContactTestOkHttp {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";


    @Test
    public void addNewContactSuccess() throws IOException {
        int index = (int) (System.currentTimeMillis()/1000);

        ContactDto contactDto = ContactDto.builder()
                .name("Maya")
                .lastName("Dow")
                .email("ya"+index+"@gmail.com")
                .phone("1133"+index)
                .address("Haifa")
                .description("university").build();
        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(body)
                .addHeader("Authorization",token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        ContactDto contact  = gson.fromJson(response.body().string(),ContactDto.class);
        Assert.assertEquals(contact.getName(),contactDto.getName());
        Assert.assertEquals(contact.getEmail(),contactDto.getEmail());
        Assert.assertEquals(contact.getPhone(),contactDto.getPhone());
        Assert.assertEquals(contact.getAddress(),contactDto.getAddress());

        System.out.println(contact.getId());

    }
}
