package net.jin.common.exception;

import java.util.List;
import java.util.Locale;

import net.jin.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.info("handleExceptionInternal");

		ApiErrorInfo restError = new ApiErrorInfo();
		restError.setMessage(ex.toString());

		return super.handleExceptionInternal(ex, restError, headers, status, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleNotMyItemException(NotMyItemException ex, WebRequest request) {
		log.info("handleNotMyItemException");

		ApiErrorInfo restError = new ApiErrorInfo();

		String message = messageSource.getMessage("item.notMyItem", null, Locale.KOREAN);
		restError.setMessage(message);

		return super.handleExceptionInternal(ex, restError, null, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleNotEnoughCoinException(NotEnoughCoinException ex, WebRequest request) {
		log.info("handleNotEnoughCoinException");

		ApiErrorInfo restError = new ApiErrorInfo();

		String message = messageSource.getMessage("coin.notEnoughCoin", null, Locale.KOREAN);
		restError.setMessage(message);

		return super.handleExceptionInternal(ex, restError, null, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		log.info("handleAccessDeniedException");

		ApiErrorInfo restError = new ApiErrorInfo();

		String message = messageSource.getMessage("common.accessDenied", null, Locale.KOREAN);
		restError.setMessage(message);

		return super.handleExceptionInternal(ex, restError, null, HttpStatus.FORBIDDEN, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> handleSystemException(Exception ex, WebRequest request) {
		log.info("handleSystemException");

		ApiErrorInfo restError = new ApiErrorInfo();
		restError.setMessage(ex.toString());

		return super.handleExceptionInternal(ex, restError, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.info("handleMethodArgumentNotValid");

		ApiErrorInfo apiErrorInfo = new ApiErrorInfo();

		StringBuffer sbMessage = new StringBuffer();

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			String field = fieldError.getField();
			String defaultMessage = fieldError.getDefaultMessage();

			sbMessage.append(field + " : " + defaultMessage);
			sbMessage.append("\r\n");
		}

		apiErrorInfo.setMessage(sbMessage.toString());

		return super.handleExceptionInternal(ex, apiErrorInfo, headers, status, request);
	}

	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		log.info("handleBindException");

		ApiErrorInfo apiErrorInfo = new ApiErrorInfo();
		apiErrorInfo.setMessage(ex.toString());

		List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();

		for (ObjectError globalError : globalErrors) {
			apiErrorInfo.addDetailInfo(globalError.getObjectName(), globalError.getDefaultMessage());
		}

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

		for (FieldError fieldError : fieldErrors) {
			apiErrorInfo.addDetailInfo(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return super.handleExceptionInternal(ex, apiErrorInfo, headers, status, request);
	}

}
