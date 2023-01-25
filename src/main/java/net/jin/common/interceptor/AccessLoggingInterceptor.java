package net.jin.common.interceptor;

import java.lang.reflect.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.method.*;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;
import lombok.extern.slf4j.*;
import net.jin.common.domain.*;
import net.jin.common.service.*;
import net.jin.common.util.*;

@Slf4j
public class AccessLoggingInterceptor implements HandlerInterceptor {

	@Autowired
	private AccessLogService service;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String requestUri = request.getRequestURI();

		String remoteAddr = NetUtils.getIp(request);

		log.info("requestURL : " + requestUri);
		log.info("remoteAddr : " + remoteAddr);

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();

			Class<?> clazz = method.getDeclaringClass();

			String className = clazz.getName();
			String classSimpleName = clazz.getSimpleName();
			String methodName = method.getName();

			log.info("[ACCESS CONTROLLER] " + className + "." + methodName);

			AccessLog accessLog = new AccessLog();

			accessLog.setRequestUri(requestUri);
			accessLog.setRemoteAddr(remoteAddr);
			accessLog.setClassName(className);
			accessLog.setClassSimpleName(classSimpleName);
			accessLog.setMethodName(methodName);

			service.register(accessLog);
		} else {
			log.info("handler : " + handler);
		}

	}

}
