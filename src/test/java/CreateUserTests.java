import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Регистрация пользователя (POST /api/auth/register)")
public class CreateUserTests extends TestDataAndConstants {

    UserMethods userMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userMethods.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Регистрация пользователя в системе")
    public void checkUserCreation() {
        Response response = userMethods.create(user);
        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("accessToken", notNullValue()).and()
                .body("refreshToken", notNullValue()).and()
                .body("user.email", equalTo(user.getEmail())).and()
                .body("user.name", equalTo(user.getName()));
        Allure.step("Пользователь успешно зарегистрирован в системе");
        accessToken = response.path("accessToken").toString();
    }

    @Test
    @DisplayName("Регистрация пользователя с уже существующими в системе данными (дубликат)")
    public void checkUserDoubleCreation() {
        Response response = userMethods.create(user);
        accessToken = response.path("accessToken").toString();

        String expectedMessage = CREATE_403_FORBIDDEN_EMPTY_FIELD_USER_ALREADY_EXISTS;
        userMethods.create(user).then()
                .statusCode(403).and().body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Регистрация пользователя без поля 'email'")
    public void checkUserCreationWithoutEmail() {
        user.setEmail(null);
        Response response = userMethods.create(user);

        String expectedMessage = CREATE_403_FORBIDDEN_EMPTY_FIELD;
        response.then()
                .statusCode(403).and().body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Регистрация пользователя без поля 'password'")
    public void checkUserCreationWithoutPassword() {
        user.setPassword(null);
        Response response = userMethods.create(user);

        String expectedMessage = CREATE_403_FORBIDDEN_EMPTY_FIELD;
        response.then()
                .statusCode(403).and().body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Регистрация пользователя без поля 'name'")
    public void checkUserCreationWithoutName() {
        user.setName(null);
        Response response = userMethods.create(user);

        String expectedMessage = CREATE_403_FORBIDDEN_EMPTY_FIELD;
        response.then()
                .statusCode(403).and().body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }
}