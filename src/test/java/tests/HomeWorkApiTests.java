package tests;

import model.CreateUser;
import lombok.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.WrongEmail;
import org.junit.jupiter.api.Test;
import network.EndpointsData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static java.nio.charset.StandardCharsets.UTF_8;
import static mainSpecifications.Specs.specRequest;
import static mainSpecifications.Specs.specResponse204;
import static mainSpecifications.Specs.specResponse400;
import static mainSpecifications.Specs.specWithoutPathRequest;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class HomeWorkApiTests {

    @Test
    public void listUsersTest() {
        List<User> users = given()
                .spec(specRequest)
                .when()
                .get(EndpointsData.LIST_USER_POINT.title)
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", User.class);
        users.forEach(x -> assertTrue(x.getAvatar().contains(x.getId().toString())));
        assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
        assertTrue(users.stream().allMatch(x -> x.getAvatar().endsWith("image.jpg")));
    }

    @Test
    public void listUsersTestWithIteration() {
        List<User> users = given()
                .spec(specRequest)
                .when()
                .get(EndpointsData.LIST_USER_POINT.title)
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data", User.class);
        users.forEach(x -> assertTrue(x.getAvatar().contains(x.getId().toString())));
        assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
        List<String> avatars = users.stream().map(User::getAvatar).collect(Collectors.toList());
        List<String> emails = users.stream().map(User::getEmail).collect(Collectors.toList());
        for (String avatar : avatars) {
            assertTrue(avatar.endsWith("image.jpg"));
        }
        for (String email : emails) {
            assertTrue(email.endsWith("@reqres.in"));
        }
    }

    @Test
    public void createTest() {
        String name = "morpheus";
        String job = "leader";
        CreateUser createData = new CreateUser();
        createData.setName(name);
        createData.setJob(job);
        given()
                .spec(specRequest)
                .body(createData)
                .when()
                .post(EndpointsData.CREATE_POINT.title)
                .then().log().all()
                .extract().as(CreateUser.class);
        assertEquals(name, createData.getName());
        assertEquals(job, createData.getJob());
    }

    @Test
    public void singleUserTest() {
        given()
                .spec(specWithoutPathRequest)
                .when()
                .get(EndpointsData.SINGLE_USER.title)
                .then()
                .log().all()
                .body("data.id", is(2));
    }

    @Test
    public void deleteUserTest() {
        given()
                .spec(specWithoutPathRequest)
                .when()
                .delete(EndpointsData.SINGLE_USER.title)
                .then()
                .spec(specResponse204);
    }

    @Test
    public void unsuccessfulRegisterTest() {
        given()
                .spec(specWithoutPathRequest)
                .body(new WrongEmail(EndpointsData.WRONG_EMAIL.title))
                .when()
                .post(EndpointsData.REGISTER_POINT.title)
                .then()
                .log().all()
                .spec(specResponse400)
                .body("error", is("Missing email or username"));
    }

    @Test
    public void unsuccessfulRegisterWithFilePathTest() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = classLoader.getResourceAsStream("jsons/unsuccessfulRegister.json")) {
            String json = (new String(is.readAllBytes(), UTF_8));
            JsonNode jsonNode = objectMapper.readTree(json);
            given()
                    .spec(specRequest)
                    .body(jsonNode)
                    .when()
                    .post(EndpointsData.REGISTER_POINT.title)
                    .then()
                    .spec(specResponse400)
                    .log().body()
                    .body("error", is("Missing password"));
        }
    }
}
