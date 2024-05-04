public class UserLoginDetails {

    private String email;
    private String password;

    public UserLoginDetails(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserLoginDetails fromUser(User user) {
        return new UserLoginDetails(user.getEmail(), user.getPassword());
    }

    @Override
    public String toString() {
        return "UserLoginDetails{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
