import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jayway.restassured.RestAssured.given;

public class DemoQa {

    WebDriver wd;
    @BeforeMethod
    public void init(){
        wd = new ChromeDriver();
        wd.manage().window().maximize();
        wd.navigate().to("https://demoqa.com/broken");
        wd.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void brokenLinks(){
        checkBrokenLinks();
    }

    @Test
    public void brokenImageTest() {
        checkBrokenImages();
    }

    public void checkBrokenImages() {
        List<WebElement> images = wd.findElements(By.tagName("img"));
        System.out.println("We have " + images.size() + " " + "images");


        for (WebElement image : images) {
            String imageURL = image.getAttribute("src");
            System.out.println("URL of Image " + "  " + " is: " + imageURL);
            //verifyLinkOkHttp(imageURL);
            verifyLinkRestAssured(imageURL);

            try {
                boolean imageDisplayed = (Boolean) ((JavascriptExecutor) wd)
                        .executeScript("return (typeof arguments[0].naturalWidth != undefined && arguments[0].naturalWidth > 0);", image);
                if (imageDisplayed) {
                    System.out.println("DISPLAY - OK");
                    System.out.println("*******************");
                } else {
                    System.out.println("DISPLAY - BROKEN");
                    System.out.println("*******************");
                }
            } catch (Exception e) {
                System.out.println("Error Occured");
            }
        }
    }



    private void checkBrokenLinks() {
        List<WebElement> list = wd.findElements(By.cssSelector("a[href]"));
        for (WebElement el:list) {
           String url =  el.getAttribute("href");

           verifyLinkRestAssured(url);
           verifyLinkOkHttp(url);


        }
    }

    private void verifyLinkOkHttp(String url) {

        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url(url)
                    .head()
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                System.out.println(url + " code: "  + response.code() + " Not Broken");
            }else {
                System.out.println(url + " code: "  + response.code() + " Its  Broken link");
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void verifyLinkRestAssured(String url) {
        try{
           int code =  given().when().head(url).then().extract().statusCode();

           if(code==200){
               System.out.println(url + " code: "  + code + " Not Broken");
           }else {
               System.out.println(url + " code: "  + code + " Its  Broken link");
           }


        }catch (Exception e){
            System.out.println(url);
            System.out.println( "Your error -->" +e.getMessage());
        }


    }


}
