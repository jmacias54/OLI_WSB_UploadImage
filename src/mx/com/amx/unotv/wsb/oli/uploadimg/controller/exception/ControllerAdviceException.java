package mx.com.amx.unotv.wsb.oli.uploadimg.controller.exception;

import java.io.IOException;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;

/**
 * @author Jesus A. Macias Benitez
 *
 */


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceException {
	
	@ExceptionHandler(ControllerException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(SizeLimitExceededException.class)
	public ResponseEntity<ErrorResponse> exceptionSizeLimitExceededException(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.INSUFFICIENT_STORAGE.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INSUFFICIENT_STORAGE);
	}
	
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorResponse> exceptionIOException(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.METHOD_FAILURE.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.METHOD_FAILURE);
	}
}
