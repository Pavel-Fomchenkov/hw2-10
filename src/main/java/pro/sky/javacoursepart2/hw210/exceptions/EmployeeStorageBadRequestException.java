package pro.sky.javacoursepart2.hw210.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeStorageBadRequestException extends RuntimeException {
    public EmployeeStorageBadRequestException(String message) {
        super(message);
        System.out.println(message);
    }
}
