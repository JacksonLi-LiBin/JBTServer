package com.matrix.jbt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.matrix.jbt.entity.AdIntermission;
import com.matrix.jbt.service.AdIntermissionService;
import com.matrix.jbt.service.impl.AdIntermissionServiceImpl;

/**
 * Servlet implementation class GetAdintermissionServlet
 */
public class GetAdintermissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdIntermissionService adIntermissionManager = new AdIntermissionServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAdintermissionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * get ads
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String type = request.getParameter("type");

		List<AdIntermission> adsList = null;
		switch (Integer.parseInt(type)) {
		case 0:
		case 1:
			adsList = adIntermissionManager.getAdByType(Integer.parseInt(type));
			break;
		case 2:
			adsList = adIntermissionManager.getAllAds();
			break;
		}
		try {
			if (adsList != null) {
				JSONArray listJSON = JSONArray.fromObject(adsList);
				out.write(listJSON.toString());
			} else {
				out.write("nodata");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
