package com.liferay.portal.kernel.servlet.context.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

@WebListener
public class NettyCleanupServletContextListener
		implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.print("Shutting down netty");
		FastThreadLocal.removeAll();
		System.out.print("Netty shut down");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	}
	
}
