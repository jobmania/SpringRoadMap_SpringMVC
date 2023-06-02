package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class) // 해당 컨트롤러에서 예외를 catch
    public ErrorResult illegalExceptionHandler(IllegalArgumentException e){
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ExceptionHandler// parameter와 같을경우 생략가능!
    public ResponseEntity<ErrorResult> userExHandler(UserException e){
        log.error("[exceptionHandler]",e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }



    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e){
        log.error("[exceptionHandler] ex",e);
        return new ErrorResult("EX", "내부오류");
    }
}
