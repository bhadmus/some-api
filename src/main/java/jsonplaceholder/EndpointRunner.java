package jsonplaceholder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class EndpointRunner {

    BasePath basePath;
    String baseURI = basePath.baseUrl;
    Random picker = new Random();
    int pick = getRandomUserID(getAllUserID());


    public ArrayList getAllUserID(){
        /**
            Get all userIDs from the all users endpoint
         **/
        Response resp = given()
                .contentType(ContentType.JSON)
                .when().get(baseURI+"/users");
        ArrayList id = resp.then().extract().path("id");
        return id;
    }


    public int getRandomUserID(ArrayList m){
        /**
          *  Gets a random userID from any Arraytlist
         **/
        return (int) m.get(picker.nextInt(m.size()));
    }


    public boolean rangeChecker(ArrayList l){
        /**
         * Checks if a list of number is withing 1 and 100
         */
        for(int i=0; i < l.size(); i++){
            return((int)l.get(i)>1  && (int)l.get(i)<100);
        }
        return false;
    }


    public void logRandomUserEmailToConsole(){
        /**
         * This would log a user email based on any random ID parsed into the baseUrl
         */
        String basePATH = String.format(baseURI+"/users/%d", pick);
        Response resp = given()
                .contentType(ContentType.JSON)
                .when()
                .get(basePATH);
        String email = resp.then().extract().path("email");
        System.out.println(email);
    }


    public void getIDsAndVerifyRange(){
        /**
         * Gets all userID of a random ID and checks if they are between 1 to 100
         */
        String basePATH = String.format(baseURI+"/users/%d/posts", pick);
        Response resp = given()
                .contentType(ContentType.JSON)
                .when().get(basePATH);
        ArrayList id = resp.then().extract().path("id");
        System.out.println(rangeChecker(id));

    }

    public void postCommentWithRandomID(){
        /**
         * Posts a comment based on a random ID with non-empty title and body
         * and verify the response
         */
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(String.format("{\n" +
                        "    \"title\": \"non-empty title for user ID %d\",\n" +
                        "    \"body\": \"non-empty body\",\n" +
                        "    \"userId\": %d\n" +
                        "}", pick, pick))
                .when().post(baseURI+"/posts");
        String title = resp.then().extract().path("title");
        boolean present = title.contains(String.format("user ID %d", pick));
        System.out.println(present);
        Assert.assertEquals(resp.getStatusCode(), 201);
    }

}
