package network;

public enum EndpointsData {

    LIST_USER_POINT("api/users?page=2"),
    CREATE_POINT("/users"),
    SINGLE_USER("/users/2"),
    WRONG_EMAIL("sydney@fife"),
    REGISTER_POINT("/register");

    public final String title;

    EndpointsData(String title) {
        this.title = title;
    }
}
