package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionProject {
	
	public void saveSessionString(HttpServletRequest request,String key, String value) {
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}
	
	public void saveSessionInt(HttpServletRequest request, String key, int value) {
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}
	
	public void saveSessionTimeOut(HttpServletRequest request, int time) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(time);
	}
	
	public void invalidateSession(HttpServletRequest request) {
		request.getSession().invalidate();
	}
	
	public String getSessionString(HttpServletRequest request, String key) {
		HttpSession session = request.getSession(false);
		if(session == null) return null;
		return (String) session.getAttribute(key);
	}
	
	public int getSessionInt(HttpServletRequest request, String key) {
		HttpSession session = request.getSession(false);
		if(session == null) return 0;
		Object val = session.getAttribute(key);
		return (val == null)? 0 : (int) val;
	}
	
	public boolean existeSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session != null && session.getAttribute(Constantes.LOGIN) != null);
	}

}
