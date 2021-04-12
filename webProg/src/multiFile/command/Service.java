package multiFile.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Service {
	public void excute(HttpServletRequest request,HttpServletResponse response);
}
