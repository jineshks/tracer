package in.espirit.tracer.ext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;

public class MyExceptionHandler extends DefaultExceptionHandler {
	private static final String VIEW = "/WEB-INF/jsp/exception.jsp";
	private static Logger logger = Logger.getLogger(MyExceptionHandler.class
			.getName());

	public Resolution catchAll(Throwable exc, HttpServletRequest req,
			HttpServletResponse resp) {
		logger.error("Exception caught - " + exc.getMessage());
		return new ForwardResolution(VIEW);
	}
}
