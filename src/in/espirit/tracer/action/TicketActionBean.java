package in.espirit.tracer.action;


import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

public class TicketActionBean extends BaseActionBean implements ValidationErrorHandler{
	protected static final String URL="/WEB-INF/jsp/ticket.jsp";
	//protected static final Logger logger = Logger.getLogger(TicketActionBean.class.getName());
	
	public Resolution cancel() {
		ForwardResolution res = new ForwardResolution(ListActionBean.class);
		res.addParameter("list", "all");
		res.addParameter("type", "defect");
		return res;		
	}
	
	@Override
	public Resolution handleValidationErrors(ValidationErrors arg0)
			throws Exception {
		if (arg0.hasFieldErrors()) {
			arg0.addGlobalError(new SimpleError("Fields Short Description, Description, Priority and Milestone are required fields"));			
		}
		return null;
	}
}
