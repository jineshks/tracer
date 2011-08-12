package in.espirit.tracer.action;

import in.espirit.tracer.database.dao.LinkDao;
import in.espirit.tracer.model.Link;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/link/{id}")
public class LinkActionBean extends BaseActionBean {
	private static final String URL="/WEB-INF/jsp/link_edit.jsp";

	private Link link;
	private String id;
	
	@DefaultHandler
	public Resolution open() {
		return new ForwardResolution(URL);
	}
	
	
	public Resolution submit() throws Exception {
		link = getLink();
		if (link.getId() == null) {
			LinkDao.registerLink(link, getContext().getLoggedUser());
			getContext().getMessages().add(new SimpleMessage("New Link Added."));
		}
		else {
			LinkDao.editLink(link);
			getContext().getMessages().add(new SimpleMessage("Link Successfully Edited."));			
		}		
		return new RedirectResolution(LinkListActionBean.class).addParameter("type", "my");
	}
	
	public Resolution delete() throws Exception {
		link = getLink();
		LinkDao.deleteLink(link);
		getContext().getMessages().add(new SimpleMessage("Link Deleted."));	
		return new RedirectResolution(LinkListActionBean.class).addParameter("type", "my");
	}
	

	public Link getLink() throws Exception {
		if (id!=null && !id.equalsIgnoreCase("new")) {
			return LinkDao.getLink(id);		
		}
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
