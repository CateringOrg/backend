package pl.edu.pw.ee.catering_backend.cart.domain;

import java.util.List;

import lombok.Getter;

public class InvalidCartDataException extends RuntimeException {

    @Getter
    private final List<String> violations;

    public InvalidCartDataException(String message, List<String> violations) {
        super(message);
        this.violations = violations;
    }
}
