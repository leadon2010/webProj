package ac.server.controller;

import javax.servlet.*;
import javax.servlet.http.*;

public class HttpUtil {
	public static void forward(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		} catch (Exception ex) {
			System.out.println("forward 오류 : " + ex);
		}
	}
}
