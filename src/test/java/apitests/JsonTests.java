package apitests;

import jsonplaceholder.EndpointRunner;
import org.testng.annotations.Test;

public class JsonTests {
    EndpointRunner runner = new EndpointRunner();

    @Test(priority = 0)
    public void getUserEmailToConsole(){
        runner.logRandomUserEmailToConsole();
    }

    @Test(priority = 1)
    public void verifyIDRangePerUser(){
        runner.getIDsAndVerifyRange();
    }

    @Test(priority = 2)
    public void postCommentAndVerifyResponse(){
        runner.postCommentWithRandomID();
    }
}
