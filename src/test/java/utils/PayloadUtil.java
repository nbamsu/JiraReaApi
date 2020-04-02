package utils;

import MainUtils.ConfigReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static utils.Constance.JSON;

public class PayloadUtil {




    public static String getPetPayload(int id){
        return "{\n" +
                "  \"id\": " +id +",\n" +
                "  \"category\": {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Bomba\"\n" +
                "  },\n" +
                "  \"name\": \"Pony\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://www.google.com/search?q=pony&rlz=1C1CHBF_enUS770US770&oq=pony&aqs=chrome..69i57j0l7.1014j0j8&sourceid=chrome&ie=UTF-8\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 9636,\n" +
                "      \"name\": \"Hourse\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"not available\"\n" +
                "}";
    }


    public static String getUserPayload(String name, String job){
        return "{\n" +
                "    \"name\": \"" + name +" \",\n" +
                "    \"job\": \"" + job +"\"\n" +
                "}";
    }




//    public static String getAuthorization(){
//        return "{\n" +
//                "    \"email\": \"eve.holt@reqres.in\",\n" +
//                "    \"password\": \"cityslicka\"\n" +
//                "}";
//    }
    public static String getJiraIssuePayLoad(String project, String assignee, String summary,
                                             String description, String IssueType, String priority, String sprintName){
        return "{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \""+project+"\"\n" +
                "        },\n" +
                "        \"assignee\": {\n" +
                "            \"name\": \""+assignee+"\"\n" +
                "        },\n" +
                "        \"summary\": \""+summary+"\",\n" +
                "        \"description\": \""+description+"\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \""+IssueType+"\"\n" +
                "        },\n" +
                "        \"priority\": {\n" +
                "            \"name\": \""+priority+"\"\n" +
                "        }\"project\":\""+sprintName+"\"\n" +
                "    }\n" +
                "}";
    }
    public static String logInBody(){
        String userName= ConfigReader.getProperty("jirauserName");
        String password=ConfigReader.getProperty("jiraPassword");
        return "{\n" +
                "\t\"username\":\""+userName+"\",\n" +
                "\t\"password\":\""+password+"\"\n" +
                "}";
    }
    public static String createSprintBody(String sprintName, String startDate, String endDate, String goal){
        return "{\n" +
                "  \"name\": \""+sprintName+"\",\n" +
                "  \"startDate\": \""+startDate+"\",\n" +
                "  \"endDate\": \""+endDate+"\",\n" +
                "  \"originBoardId\": 1,\n" +
                "  \"goal\": \""+goal+"\"\n" +
                "}";
    }

    public static String generateCookie() throws URISyntaxException, IOException {
        HttpClient httpClient= HttpClientBuilder.create().build();
        URIBuilder uriBuilder=new URIBuilder();
        uriBuilder.setScheme("http").setHost("localhost:8080").setPath("rest/auth/1/session");
        HttpPost httpPost=new HttpPost(uriBuilder.build());
        httpPost.addHeader("Accept",JSON);
        httpPost.addHeader("Content-Type",JSON);
        HttpEntity entity=new StringEntity(logInBody());

        httpPost.setEntity(entity);
        HttpResponse response=httpClient.execute(httpPost);
        ObjectMapper objectMapper=new ObjectMapper();
        Map<String, Object> body=objectMapper.readValue(response.getEntity().getContent(),new TypeReference<Map<String, Object>>(){});
        Map<String, String> session=(Map<String, String>)body.get("session");

       // System.out.println(session.get("value"));
        return session.get("value");

    }

    public static String generateCookieRestAPI(){
        //http://localhost:8080/rest/auth/1/session
        RestAssured.baseURI="http://localhost:8080";
        RestAssured.basePath="rest/auth/1/session";
        Response response= given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(PayloadUtil.logInBody())
                .when().post().then().statusCode(200).extract().response();
        Map<String, Object> responseBody=response.getBody().as(new TypeRef<Map<String, Object>>() { });
        Map<String, String> session= (Map<String, String>)responseBody.get("session");
//       String value=session.get("name")+"="+session.get("value");
//            System.out.println(value);
        return session.get("value");
    }
    public static String createComment(String comment){

        return "{\n" +
                "    \"body\": \""+comment+"\"\n" +
                "}";
    }




}
