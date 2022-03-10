package contactokhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTests {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();


    @Test
    public void registrationSuccessTest() throws IOException {
        int index =(int)(System.currentTimeMillis()/1000)%3600;

        AuthRequestDto auth = AuthRequestDto.builder().email("soll"+index+"@gmail.com").password("Soll12345$").build();

        RequestBody body = RequestBody.create(gson.toJson(auth),JSON) ;
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        AuthResponseDto responseDto = gson.fromJson(response.body().string(),AuthResponseDto.class);
        System.out.println(responseDto.getToken());
        Assert.assertEquals(response.code(),200);


    }


}
