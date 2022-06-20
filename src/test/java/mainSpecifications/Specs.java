package mainSpecifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
@JsonIgnoreProperties
public class Specs {

    public static RequestSpecification specRequest = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().all()
            .contentType(ContentType.JSON);

    public static RequestSpecification specWithoutPathRequest = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification specResponse400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build()
            .contentType(ContentType.JSON)
            .log().body();

    public static ResponseSpecification specResponse200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build()
            .contentType(ContentType.JSON);

    public static ResponseSpecification specResponse204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .build();

}
