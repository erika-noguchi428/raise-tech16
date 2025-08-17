//package raisetech.StudentManagement.exception;

//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
// org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
//public class Handler {

 // @ExceptionHandler(TestException.class)

 // public ResponseEntity<String> handleTestException(TestException ex){
    //ログ出力
  //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
 // }

  //public class SearchConditionMissingException extends RuntimeException {
 //   public SearchConditionMissingException(String message) {
   //   super(message);
  //  }
 // }

//}
package raisetech.StudentManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  public ResponseEntity<String> handleTestException(TestException ex){
    //ログ出力
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
