package net.wmfs.coalesce.aa.access;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class CustomExceptionController {
	
	@ExceptionHandler(Exception.class)
	public String handleException(HttpServletRequest req, Exception ex, Model model) {
		model.addAttribute("exceptionMessage", ex.getMessage());
		return "exception";
	}
}
