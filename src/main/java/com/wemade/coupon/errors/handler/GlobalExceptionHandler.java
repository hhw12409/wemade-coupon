package com.wemade.coupon.errors.handler;

import com.wemade.coupon.errors.errorcode.CustomErrorCode;
import com.wemade.coupon.errors.errorcode.ErrorCode;
import com.wemade.coupon.errors.exception.RestApiException;
import com.wemade.coupon.errors.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.wemade.coupon.controller")
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        // Swagger 요청 경로 제외
        if (isSwaggerRequest()) {
            return ResponseEntity.ok().build(); // 200 OK 반환
        }

        final ErrorCode errorCode = CustomErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    // Rest API Exception
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleApiException(final RestApiException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    // Parameter Validation Error
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(final IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        final ErrorCode errorCode = CustomErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ResponseEntity<Object> handleExceptionInternal(final ErrorCode errorCode, final String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    // help Method
    private boolean isSwaggerRequest() {
        // Swagger 요청 경로를 체크
        String path = org.springframework.web.context.request.RequestContextHolder
                .getRequestAttributes()
                .toString();
        log.info("path = {}",path);
        return path.contains("/v3/api-docs") || path.contains("/swagger-ui");
    }

    /**
     * ErrorResponse 생성 (ErrorCode 받는 경우)
     * @param errorCode
     * @return ErrorResponse
     */
    private ErrorResponse makeErrorResponse(final ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    /**
     * ErrorResponse 생성 (ErrorCode, message 받는 경우)
     * @param errorCode
     * @param message
     * @return ErrorResponse
     */
    private ErrorResponse makeErrorResponse(final ErrorCode errorCode, final String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }
}
