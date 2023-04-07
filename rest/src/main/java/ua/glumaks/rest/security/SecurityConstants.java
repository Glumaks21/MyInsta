package ua.glumaks.rest.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/auth/**";
    public static final String SECRET = "3273357638792F423F4528482B4D6250655368566D597133743677397A244326";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 600_000; //10 min

}
