package com.matrix.jbt.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.matrix.jbt.entity.AdIntermission;
import com.matrix.jbt.service.impl.AdIntermissionServiceImpl;
import com.matrix.jbt.tool.AdminPool;

/**
 * Servlet implementation class UploadImageServlet
 */

public class UploadImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdIntermissionServiceImpl adManager = new AdIntermissionServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadImageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * upload an image
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		int showtime = 0;
		int type = 0;
		String image_path = "";
		try {
			// 1. create DiskFileItemFactory
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2. create an object of FileUpload
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 3. Determine whether the upload is a form
			boolean b = upload.isMultipartContent(request);
			if (!b) {
				// not a file upload
				out.write("Sorry, not a file upload form!");
				return;
			}
			// a file upload form
			// 4. Parse the request, to gain FileItem items
			List<FileItem> items = upload.parseRequest(request);
			// 5. Traverse the collection
			for (FileItem item : items) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString();
					// Manual conversion
					value = new String(value.getBytes("iso-8859-1"), "utf-8");
					if ("adminToken".equals(name)) {
						if (AdminPool.adminActions.get(value) == null) {
							response.sendRedirect("index_login.html");
							return;
						}
					} else if ("showtime".equals(name)) {
						showtime = Integer.valueOf(value);
					} else {
						if (value.equals("Intermission")) {
							type = 0;
						} else {
							type = 1;
						}
					}
				} else {
					// File upload fields
					// get the file name
					String filename = item.getName();
					filename = filename
							.substring(filename.lastIndexOf("\\") + 1);
					// System.out.println(filename);

					image_path = request.getRequestURL().toString()
							.replace("UploadImageServlet", "")
							+ filename;

					// create the file
					ServletContext context = getServletContext();
					String dir = context.getRealPath("/");
					// System.out.println(dir);
					File file = new File(dir, filename);
					file.createNewFile();
					// Get the stream, to read data and written to the file
					InputStream in = item.getInputStream();
					FileOutputStream fos = new FileOutputStream(file);

					int len;
					byte[] buffer = new byte[1024];
					while ((len = in.read(buffer)) > 0)
						fos.write(buffer, 0, len);
					fos.close();
					in.close();
					item.delete(); // Delete temporary files
				}
			}

			// System.out.println("showtime=" + showtime + "-------type=" +
			// type);
			// System.out.println("image_path:" + image_path);
			AdIntermission adIntermission = new AdIntermission(image_path,
					showtime * 1000, type, 1);
			AdIntermission getIntermission = adManager
					.getAdIntermission(image_path);
			// System.out.println(getIntermission);
			if (getIntermission == null) {
				adManager.addAdIntermission(adIntermission);
			} else {
				getIntermission.setShowTime(showtime * 1000);
				getIntermission.setPath(image_path);
				getIntermission.setType(type);
				getIntermission.setUsable(1);
				adManager.updateAdIntermission(getIntermission);
			}
			response.sendRedirect("index_uploadimage.html?uploadImage=true");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
