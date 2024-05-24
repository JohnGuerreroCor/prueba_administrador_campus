/**
 * 
 */
package co.edu.usco.lcms.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author jankarlos
 *
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// System.out.printf("Session ID %s created at %s%n",
		// event.getSession().getId());
		event.getSession().setMaxInactiveInterval(30 * 60);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		// System.out.printf("Session ID %s destroyed at %s%n",
		// se.getSession().getId());
	}

}
