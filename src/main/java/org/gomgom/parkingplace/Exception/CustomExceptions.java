package org.gomgom.parkingplace.Exception;

public class CustomExceptions {

    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        private static final String DEFAULT_MESSAGE = "사용자를 찾을 수 없습니다.";

        public UserNotFoundException() {
            super(DEFAULT_MESSAGE);
        }

        public UserNotFoundException(String message) {
            super(message != null ? message : DEFAULT_MESSAGE);
        }
    }


}
