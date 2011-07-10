package in.espirit.tracer.ext;

import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Defect;

import java.util.Collection;
import java.util.Locale;


import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

public class DefectTypeConverter implements TypeConverter<Defect> {

	@Override
	public Defect convert(String url, Class<? extends Defect> arg1,Collection<ValidationError> arg2) {
		if(url.equalsIgnoreCase("new")) {   //only when there is /defect/new
			Defect ticket=new Defect();
			ticket.setType("defect");	
			return ticket;
		}
		else { 		
				try {
					return (Defect) TicketDao.getTicket("defect",url);
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
