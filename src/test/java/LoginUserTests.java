import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Авторизация пользователя (POST /api/auth/login)")
public class LoginUserTests extends TestDataAndConstants {

    UserMethods userMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        accessToken = userMethods.create(user).path("accessToken").toString();  // для удаления
    }

    @After
    public void cleanUp() {
        userMethods.delete(accessToken);
    }

    @Test
    @DisplayName("Авторизация пользователя в системе с существующими данными")
    public void checkSuccessUserLogin() {
        Response response = userMethods.login(UserLoginDetails.fromUser(user));
        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("accessToken", notNullValue()).and()
                .body("refreshToken", notNullValue()).and()
                .body("user.email", equalTo(user.getEmail())).and()
                .body("user.name", equalTo(user.getName()));
        Allure.step("Пользователь успешно авторизован");
    }

    @Test
    @DisplayName("Авторизация пользователя в системе без поля 'email'")
    public void checkUserLoginWithoutEmail() {
        user.setEmail(null);
        Response response = userMethods.login(UserLoginDetails.fromUser(user));
        String expectedMessage = LOGIN_401_UNAUTHORIZED_EMPTY_FIELD;
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Авторизация пользователя в системе без поля 'password'")
    public void checkUserLoginWithoutPassword() {
        user.setPassword(null);
        Response response = userMethods.login(UserLoginDetails.fromUser(user));
        String expectedMessage = LOGIN_401_UNAUTHORIZED_EMPTY_FIELD;
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Авторизация пользователя в системе с некорректным полем 'email'")
    public void checkUserLoginWithIncorrectEmail() {
        user.setEmail(faker.internet().emailAddress());
        Response response = userMethods.login(UserLoginDetails.fromUser(user));
        String expectedMessage = LOGIN_401_UNAUTHORIZED_EMPTY_FIELD;
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Авторизация пользователя в системе с некорректным полем 'password'")
    public void checkUserLoginWithIncorrectPassword() {
        user.setPassword(faker.internet().password());
        Response response = userMethods.login(UserLoginDetails.fromUser(user));
        String expectedMessage = LOGIN_401_UNAUTHORIZED_EMPTY_FIELD;
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }
}
