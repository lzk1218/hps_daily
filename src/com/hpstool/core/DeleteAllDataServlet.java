package com.hpstool.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpstool.core.request.DeleteAllDateRequest;
import com.hpstool.core.resolver.DeleteAllDataResolver;

public class DeleteAllDataServlet extends HttpServlet {

	private static final long serialVersionUID = -5674278889076297672L;
	private static final Logger LOG = LogManager.getLogger(GetStageServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String endDateStr = req.getParameter("endDate");
		String startDateStr = req.getParameter("startDate");
		SimpleDateFormat yyyymmddFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = yyyymmddFormat.parse(startDateStr);
			endDate = yyyymmddFormat.parse(endDateStr);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		System.out.println(startCalendar.compareTo(endCalendar));
		HttpSession session = req.getSession();
		String date = "";
		String httpSession = (String) session.getAttribute("httpSession");
		LOG.info(String.format("[startDate=%s][endDate=%s]", startDateStr, endDateStr));
		String scriptSession = (String) session.getAttribute("scriptSession");

		String personId = (String) session.getAttribute("personId");

		System.out.println(httpSession + scriptSession + personId);

		if (session.getAttribute("name") == null)
			resp.getWriter().print("error_session");

		CloseableHttpClient client = null;

		DeleteAllDateRequest request = null;

		DeleteAllDataResolver resolver = null;
		while (startCalendar.compareTo(endCalendar) <= 0) {
//			if (startCalendar.DAY_OF_WEEK ==  Calendar.SUNDAY || startCalendar.DAY_OF_WEEK ==  Calendar.SATURDAY)
//					continue;
			date = yyyymmddFormat.format(startCalendar.getTime());
			try {
				client = (CloseableHttpClient) session.getAttribute("client");
				request = new DeleteAllDateRequest();
				resolver = new DeleteAllDataResolver();
				Map<String, String> map = resolver
						.resolve(request.execute(client, scriptSession, httpSession, personId, date));

				ObjectMapper mapper = new ObjectMapper();

				String str = mapper.writeValueAsString(map);

				resp.getWriter().print(str);

			} catch (Exception e) {
				resp.getWriter().print("error_internal");
			}
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
}
