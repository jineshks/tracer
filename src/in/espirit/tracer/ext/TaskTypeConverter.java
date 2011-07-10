package in.espirit.tracer.ext;

import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Task;

import java.util.Collection;
import java.util.Locale;


import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

public class TaskTypeConverter implements TypeConverter<Task> {

	@Override
	public Task convert(String url, Class<? extends Task> arg1,Collection<ValidationError> arg2) {
		if(url.equalsIgnoreCase("new")) { //only when there is /task/new
			Task ticket=new Task();
			ticket.setType("task");	
			return ticket;
		}
		else { 		
				try {
					return (Task) TicketDao.getTicket("task",url);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}		
		}
	}

	@Override
	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub
		
	}


}
