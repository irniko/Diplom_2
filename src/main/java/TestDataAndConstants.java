import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class TestDataAndConstants {

    Faker faker = new Faker();
    public static String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static String CREATE_403_FORBIDDEN_EMPTY_FIELD_USER_ALREADY_EXISTS = "User already exists";
    public static String CREATE_403_FORBIDDEN_EMPTY_FIELD = "Email, password and name are required fields";
    public static String LOGIN_401_UNAUTHORIZED_EMPTY_FIELD = "email or password are incorrect";
    public static String _403_NEED_AUTHORIZATION = "You should be authorised";
    public static String DELETE_202_USER_SUCCESSFULLY_REMOVED = "User successfully removed";
    public static String CREATE_ORDER_400_BAD_REQUEST = "Ingredient ids must be provided";
    public static String CREATE_ORDER_500_BAD_REQUEST = "500 Internal Server Error";

    public static String INCORRECT_HASHCODE = RandomStringUtils.random(24, true, true);
    String name = faker.name().username();
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    User user = new User(email, password, name);
}
