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
 * Servlet implementation class EditAdServlet
 */
public class EditAdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdIntermissionService adIntermissionManager = new AdIntermissionServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditAdServlet() {
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
	 * edit ad's infos
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		// System.out.println(request.getParameter("ad"));
		String adStr = request.getParameter("ad");
		if (adStr != null) {
			JSONArray ad = JSONArray.fromObject("[" + adStr + "]");
			// System.out.println(ad);
			List<AdIntermission> list = (List<AdIntermission>) JSONArray
					.toCollection(ad, AdIntermission.class);
			if (adIntermissionManager.updateAdIntermission(list.get(0))) {
				out.write("true");
			} else {
				out.write("false");
			}
		}

	}

}
