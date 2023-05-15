package com.haivn.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionResponse {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleNullPointerException(Exception ex,
                                                                   HttpServletRequest request, HttpServletResponse response) {
//        log.info("Entering into the handleAllException method");
//        System.out.println("Exception is : " + ex.getClass());
        Map<String, Object> result = new HashMap<>();
        ResponseMessage error = new ResponseMessage();
        String errorMsg = "";
        if(ex.getLocalizedMessage().contains("ConstraintViolationException")) {
            errorMsg = "Không thể thực hiện yêu cầu. Bản ghi này có lỗi về ràng buộc dữ liệu.";
        }
        else if(ex.getLocalizedMessage().contains("Access is denied")){
            errorMsg = "Truy cập bị từ chối";
        }
        else if(ex.getLocalizedMessage().contains("NumberFormatException")){
            errorMsg = "Dữ liệu đưa vào không đúng định dạng";
        }
        else
            errorMsg = HttpStatus.BAD_REQUEST + " | " + ex.getLocalizedMessage() + " | " + LocalDateTime.now();

        result.put("result", errorMsg);
        result.put("success", false);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> httpMessageNotReadableException(HttpMessageNotReadableException exception,
                                                                               WebRequest webRequest) {
        Map<String, Object> result =new HashMap<String, Object>();
        result.put("result","Dữ liệu đưa vào không đúng định dạng");
        result.put("success",false);

        return new ResponseEntity(result, HttpStatus.OK);
    }

//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<Map<String, Object>> handlerNullPointerException(NullPointerException exception,
//                                                                               WebRequest webRequest) {
//        Map<String, Object> result =new HashMap<String, Object>();
//        exception.getClass().getFields().
//        result.put("result","Dữ liệu đưa vào không đúng định dạng");
//        result.put("success",false);
//
//        return new ResponseEntity(result, HttpStatus.OK);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidArgument(MethodArgumentNotValidException exception,
                                                                     WebRequest webRequest) {
        Map<String, Object> result = new HashMap<String, Object>();
        exception.getBindingResult().getFieldErrors().forEach(ex -> {
            result.put("result", ex.getDefaultMessage());
        });
        result.put("success",false);

        return new ResponseEntity(result, HttpStatus.OK);
    }
}
