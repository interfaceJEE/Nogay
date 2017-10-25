package com.noyan.network.socket;

import java.net.InetAddress;

public interface ServerSocketAdapter {

	public void processRecieveData(byte[] data, InetAddress recieveAddress, int recievePort);

	public void processException(Exception exception);

}
