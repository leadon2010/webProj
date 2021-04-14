package jsp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = -2893737361570313739L;

	@Override
	public void init(ServletConfig config) {
		System.out.println("init() 시랭됨");
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) {
		System.out.println("service() 실행됨");
	}
}
