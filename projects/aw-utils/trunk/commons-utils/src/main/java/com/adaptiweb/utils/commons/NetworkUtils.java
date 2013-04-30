package com.adaptiweb.utils.commons;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Random;

public class NetworkUtils {
	private static final int MIN_PORT = 1024;
	private static final int MAX_PORT = 65534;
	private static final int MAX_TRIES = 200;
	
	private static Random rand;
	
	public static int reservePort() {
		for (int i = 0; i <= MAX_TRIES; i++) {
			int portCandidate = getRand().nextInt(MAX_PORT - MIN_PORT) + MIN_PORT + 1;
			if (isFree(portCandidate)) return portCandidate;
		}
		throw new RuntimeException("Unable to reserve free network port after " + MAX_TRIES + " tries");
	}
	
	private static Random getRand() {
		if (rand == null) rand = new Random(System.currentTimeMillis());
		return rand;
	}
	
	// copy-paste from maven-freeport-plugin: FreePortImpl
	private static boolean isFree(int port) {
		if ((port <= 0) || (port > MAX_PORT)) return false;
		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) ds.close();
			if (ss != null) {
				try { ss.close(); } catch (IOException e) { /* should not be thrown */ }
			}
		}
		return false;
	}

}
