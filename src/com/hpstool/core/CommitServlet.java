package com.hpstool.core;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.client.CloseableHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpstool.core.request.DailyRequest;
import com.hpstool.core.resolver.DailyResolver;

public class CommitServlet extends HttpServlet {

	private static final long serialVersionUID = -1110099333220172841L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String project = req.getParameter("project");

		String stage = req.getParameter("stage");

		String module = req.getParameter("module");

		String activity = req.getParameter("activity");

		String content = req.getParameter("content");

		System.out.printf("[project = %s] [stage = %s] [module = %s] [activity = %s] [content = %s]\n", 
				project,stage, module, activity, content);

		String[] dates = req.getParameter("date").split(",");

		HttpSession session = req.getSession();

		String httpSession = (String) session.getAttribute("httpSession");

		String scriptSession = (String) session.getAttribute("scriptSession");

		String personId = (String) session.getAttribute("personId");

		DailyRequest request = new DailyRequest();

		DailyResolver resolver = new DailyResolver();

		if (session.getAttribute("name") == null)
			resp.getWriter().print("error_session");

		CloseableHttpClient client = (CloseableHttpClient) session.getAttribute("client");

		Map<String, Boolean> map = new TreeMap<String, Boolean>();

		for (String date : dates) {
			try {
				map.put(date, resolver.resolve(request.execute(client, scriptSession, httpSession, project, stage,
						module, activity, content, personId, date)));

			} catch (Exception e) {
				e.printStackTrace();
				map.put(date, false);
			}
		}

		ObjectMapper mapper = new ObjectMapper();

		String str = mapper.writeValueAsString(map);

		resp.getWriter().print(str);
	}
}
