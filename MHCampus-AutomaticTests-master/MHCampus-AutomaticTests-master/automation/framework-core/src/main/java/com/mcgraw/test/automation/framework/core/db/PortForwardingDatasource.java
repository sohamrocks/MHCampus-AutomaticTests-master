package com.mcgraw.test.automation.framework.core.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.ConnectionFactory;

import com.mcgraw.test.automation.framework.core.common.remote_access.ssh.ISshService;
import com.mcgraw.test.automation.framework.core.exception.SshServiceException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Apache DataSource extension. Opens port forwarding based on ssh connection
 * before creating datasource
 *
 * @author Andrei Varabyeu
 *
 */
public class PortForwardingDatasource extends BasicDataSource {

	/** JDBC Connection string pattern */
	private static final Pattern JDBC_CONNECTION_PATTERN = Pattern
			.compile("(.*//)(.*):([\\d]*)(/.*)");

	private ISshService sshDelegate;

	public PortForwardingDatasource() {
		super();
	}

	/**
	 * @param sshDelegate
	 *            the sshDelegate to set
	 */
	public void setSshDelegate(ISshService sshDelegate) {
		this.sshDelegate = sshDelegate;
	}

	/**
	 * Wraps default Apache implementation with opening ssh connection with port
	 * forwarding from local host to remote (host where database is placed)
	 * through ssh node
	 */
	@Override
	protected ConnectionFactory createConnectionFactory() throws SQLException {

		if (!sshDelegate.hasActiveForwarding()) {
			Logger.debug("Creating Port Forwarding datasource...");
			/*
			 * We have to parse connection string to know database server and
			 * port
			 */
			Matcher m = JDBC_CONNECTION_PATTERN.matcher(getUrl());
			if (!m.find()) {
				throw new RuntimeException("Incorrect JDBC URL!");
			}

			int localPort;
			try {
				/* Start port forwarding and retrieve binded local port */
				localPort = startPortForwarding(m.group(2),
						Integer.parseInt(m.group(3)));
			} catch (SshServiceException e) {
				throw new RuntimeException("Unable to setup port forwarding", e);
			}

			/*
			 * Replace remote server with localhost and remote port with binded
			 * port
			 */
			String forwardUrl = m.replaceAll("$1"
					+ ISshService.LOCALHOST_ADDRESS + ":" + localPort + "$4");

			/* Update connection pool with new JDBC url */
			setUrl(forwardUrl);
		}

		return super.createConnectionFactory();
	}

	/**
	 * Close SSH connection after connection pool closing
	 */
	@Override
	public synchronized void close() throws SQLException {
		try {
			super.close();
		} finally {
			sshDelegate.stopPortForwarding();
		}
	}

	/**
	 * Starts ssh connection and setups port forwarding
	 *
	 * @param databaseServer
	 *            - Database Server
	 * @param databasePort
	 *            - Database port
	 * @return
	 * @throws IOException
	 */
	private int startPortForwarding(String databaseServer, int databasePort)
			throws SshServiceException {
		return sshDelegate.startPortForwarding(databaseServer, databasePort);
	}

}
