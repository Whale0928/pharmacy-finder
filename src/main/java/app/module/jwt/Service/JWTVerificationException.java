package app.module.jwt.Service;

public class JWTVerificationException extends RuntimeException {
    public JWTVerificationException(String message) {
        super(message);
    }
}
