package utils;

import io.restassured.path.json.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import javax.naming.ConfigurationException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Utils {
    public static Properties props;
    public static FileInputStream file;
    public static int generateRandomNumber(int min, int max){
        double randomId = Math.random()*(max-min)+min;
        return (int) randomId;
    }
    public static void saveUserInfo(String filePath, JSONObject jsonObject) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(filePath));
        jsonArray.add(jsonObject);
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();

    }
    public static  String getEmailList() throws IOException, ConfigurationException {

        props = new Properties();
        file = new FileInputStream("./src/test/resources/config.properties");
        props.load(file);
        RestAssured.baseURI="https://gmail.googleapis.com/";
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages");
        JsonPath jsonPath=res.jsonPath();
        return jsonPath.get("messages[0].id");
    }
    public static String readLatestMail() throws IOException, ConfigurationException {
        String messageId= getEmailList();
        RestAssured.baseURI="https://gmail.googleapis.com/";
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages/"+messageId);

        JsonPath jsonPath=res.jsonPath();
        String message= jsonPath.get("snippet");

        return message;

    }

}
