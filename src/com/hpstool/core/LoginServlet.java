package com.hpstool.core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hpstool.core.request.EngineRequest;
import com.hpstool.core.request.LoginRequest;
import com.hpstool.core.request.PersonIdRequest;
import com.hpstool.core.resolver.PersonIdResolver;
import com.hpstool.core.resolver.ScriptSessionIDResolver;

public class LoginServlet extends HttpServlet {
	private static final Logger LOG = LogManager.getLogger(LoginServlet.class);

	static class UserInfo implements Comparable<Object> {
		UserInfo(String name, String password) {
			this.name = name;
			this.password = password;
		}

		public String getName() {
			return name;
		}

		// public void setName(String name) {
		// this.name = name;
		// }
		public String getPassword() {
			return password;
		}

		public String toString() {
			return String.format("[name = %s], [password = %s]", name, password);
		}

		// public void setPassword(String password) {
		// this.password = password;
		// }
		private String name = "";
		private String password = "";

		@Override
		public int compareTo(Object o) {
			if (null == o) {
				return 1;
			} else if (!(o instanceof UserInfo)) {
				return 1;
			}
			UserInfo ano = (UserInfo) o;
			System.out.println(ano);
			System.out.println(this);
			return this.name.equals(ano.getName()) && this.password.equals(ano.getPassword()) ? -1 : 1;
		}

		private void writeUserInfo(String ip, UserInfo curUserInfo, String fileDir) {
			System.out.println("writeUserInfo" + ip + "," + curUserInfo + "\n");
			userInfoMap.put(ip, curUserInfo);
			
			Path rf_Info_path = FileSystems.getDefault().getPath(fileDir, "conf", "userInfo.csv");
			Charset charset = Charset.forName("UTF-8");
			ArrayList<String> lines = new ArrayList<>();
			UserInfo uTmp = null;
			System.out.println(userInfoMap.keySet());
			for (String ipTmp : userInfoMap.keySet()) {
				uTmp = userInfoMap.get(ipTmp);
				lines.add(ipTmp + "," + uTmp.getName() + "," + uTmp.getPassword());
				if (LOG.isDebugEnabled()) {
					LOG.debug(ipTmp + "," + uTmp.getName() + "," + uTmp.getPassword() + "\n");
				}
			}
			try {
				Files.write(rf_Info_path, lines, charset, StandardOpenOption.CREATE);
			} catch (IOException e) {
				System.err.println(e);
			}

		}
	}

	private static final long serialVersionUID = -4054078177646627256L;
	// @SuppressWarnings("unchecked")
	private static Map<String, UserInfo> userInfoMap = null;

	private static Object obj = new Object();
	private transient String workSpacePath = null;

	public synchronized final void init(String path) {
		userInfoMap = Collections.synchronizedMap(new HashMap<String, UserInfo>());
		Path rf_Info_path = FileSystems.getDefault().getPath(path, "conf", "userInfo.csv");
		Charset charset = Charset.forName("UTF-8");
		String[] elements = null;
		try {
			List<String> lines = Files.readAllLines(rf_Info_path, charset);

			for (String line : lines) {
				if (line.matches("^[ \\s]*$")) {
					continue;
				}
				elements = line.split(",");
				if (elements.length != 3) {
					continue;
				}

				userInfoMap.put(elements[0].trim(), new UserInfo(elements[1].trim(), elements[2].trim()));

			}
		} catch (IOException e) {
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Lock lock = new ReentrantLock();
//		lock.tryLock();
		if (workSpacePath == null) {
			synchronized (obj) {
				String filePath = this.getClass().getClassLoader().getResource("").getPath();
				filePath = filePath.replaceFirst("^/", "");
				workSpacePath = FileSystems.getDefault().getPath(filePath).getParent().toString();

			}
		}
		System.out.println(workSpacePath);

		String name = req.getParameter("name");
		String password = req.getParameter("password");

		HttpSession session = req.getSession();

		String beforeName = (String) session.getAttribute("name");

		if (!name.equals(beforeName)) {

			session.setAttribute("name", name);

			CloseableHttpClient client = (CloseableHttpClient) session.getAttribute("client");

			if (client != null)
				client.close();

			client = HttpClients.createDefault();

			session.setAttribute("client", client);
			String remoteIp = req.getRemoteHost();
			if (null == userInfoMap) {
				System.out.println("init(workSpacePath)");
				init(workSpacePath);
			}
			if (null != name && !"".equals(name)) {
				UserInfo curUserInfo = new UserInfo(name, password);

				UserInfo bufUserInfo = userInfoMap.get(remoteIp);
				System.out.println(remoteIp);
				System.out.println(bufUserInfo);
				System.out.println(curUserInfo);
				System.out.println(curUserInfo.compareTo(bufUserInfo));
				if (curUserInfo.compareTo(bufUserInfo) == 1) {
					curUserInfo.writeUserInfo(remoteIp, curUserInfo, workSpacePath);
				}

			} else {
				UserInfo bufUserInfo = userInfoMap.get(remoteIp);
				name = bufUserInfo.getName();
				password = bufUserInfo.getPassword();
			}
			try {
				EngineRequest engineRequest = new EngineRequest();
				String scriptSession = new ScriptSessionIDResolver().resolve(engineRequest.execute(client));

				session.setAttribute("scriptSession", scriptSession);

				LoginRequest loginRequest = new LoginRequest();

				String httpSession = loginRequest.execute(client, name, password);
				if (httpSession != null) {
					session.setAttribute("httpSession", httpSession);
				}

				httpSession = (String) session.getAttribute("httpSession");

				if (httpSession == null)
					throw new Exception("http session is null.");

				PersonIdRequest personIdRequest = new PersonIdRequest();

				PersonIdResolver personIdResolver = new PersonIdResolver();
				String personId = personIdResolver
						.resolve(personIdRequest.execute(client, Util.toDate(new Date()), httpSession));

				session.setAttribute("personId", personId);

			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}

		resp.getWriter().print("OK");
		// req.getLocalAddr()
		LOG.info(String.format("user login [name=%s][password=%s][ip=%s]", name, password, req.getRemoteHost()));
	}

}
