package ac.server.controller;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

public interface Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException;

}
