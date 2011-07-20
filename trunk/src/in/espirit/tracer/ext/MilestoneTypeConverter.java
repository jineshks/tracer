package in.espirit.tracer.ext;

import in.espirit.tracer.database.dao.MilestoneDao;
import in.espirit.tracer.database.dao.TicketDao;
import in.espirit.tracer.model.Defect;
import in.espirit.tracer.model.Milestone;

import java.util.Collection;
import java.util.Locale;


import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

public class MilestoneTypeConverter implements TypeConverter<Milestone> {

	@Override
	public Milestone convert(String url, Class<? extends Milestone> arg1,Collection<ValidationError> arg2) {
		if(url.equalsIgnoreCase("new")) {   //only when there is /milestone/new
			Milestone milestone=new Milestone();
			return milestone;
		}
		else { 		
				try {
					return MilestoneDao.getEntry(url);
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
