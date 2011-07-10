package in.espirit.tracer.action;

import org.apache.log4j.Logger;

import in.espirit.tracer.ext.MyActionBeanContext;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

public class BaseActionBean implements ActionBean {

	private MyActionBeanContext ctx;
	
	protected static final Logger logger = Logger.getLogger(BaseActionBean.class.getName());
	
	@Override
	public MyActionBeanContext getContext() {
		return ctx;
	}

	@Override
	public void setContext(ActionBeanContext arg0) {
		this.ctx=(MyActionBeanContext) arg0;
		
	}

}