package in.espirit.tracer.ext;

import in.espirit.tracer.action.BaseActionBean;
import in.espirit.tracer.action.LoginActionBean;
import in.espirit.tracer.action.UserSignUpActionBean;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

@Intercepts(LifecycleStage.ActionBeanResolution)
public class LoginInterceptor implements Interceptor {
	
	@SuppressWarnings("unchecked")
	private static final List<Class<? extends BaseActionBean>> EXC = Arrays.asList(LoginActionBean.class, UserSignUpActionBean.class);
	
	@Override
	public Resolution intercept(ExecutionContext arg0) throws Exception {
		Resolution res = arg0.proceed();
		
		MyActionBeanContext ctx = (MyActionBeanContext) arg0.getActionBeanContext();
		
		BaseActionBean actBean = (BaseActionBean) arg0.getActionBean();
		
		Class<? extends ActionBean> cls = actBean.getClass();
		
		if (ctx.getLoggedUser()==null && !EXC.contains(cls)) {
			res = new RedirectResolution(LoginActionBean.class); 
		}
		
		return res;
	}
}