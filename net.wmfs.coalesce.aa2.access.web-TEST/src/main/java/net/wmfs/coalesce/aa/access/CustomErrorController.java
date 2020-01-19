package net.wmfs.coalesce.aa.access;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";
	private static final String ERROR_TEMPLATE = "customError";

	private final ErrorAttributes errorAttributes;

	@Autowired
	public CustomErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping(ERROR_PATH)
	public ModelAndView error(ModelAndView modelAndView, HttpServletRequest request) {

		Map<String, Object> error = getErrorAttributes(request, true);

		modelAndView.addObject("timestamp", error.get("timestamp"));
		modelAndView.addObject("status", error.get("status"));
		modelAndView.addObject("error", error.get("error"));
		modelAndView.addObject("message", error.get("message"));
		modelAndView.addObject("path", error.get("path"));

		modelAndView.setViewName(ERROR_TEMPLATE);

		return modelAndView;
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	@RequestMapping("/404")
	public String pageNotFound(Model model, HttpServletRequest request) {
		model.addAttribute("error", getErrorAttributes(request, true));
		return "404";
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}

}
