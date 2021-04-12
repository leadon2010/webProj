package multiFile.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Butil {
	public static void dispatcher(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			RequestDispatcher dis = request.getRequestDispatcher(path);
			dis.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}
}
