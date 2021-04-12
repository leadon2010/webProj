package ac.server.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.taglibs.standard.extra.spath.Step;

import ac.server.dto.BoardDto;
import ac.server.service.boardService;

public class PostInsertTempController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		ArrayList<BoardDto> boarddto = null;
		try {
			boarddto = boardService.getInstance().selectAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("board", boarddto);
		return "/post/postInsert.jsp";
	}

}
