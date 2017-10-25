package com.noyan.network.socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import com.noyan.network.socket.ServerSocketAdapter;
import com.noyan.network.socket.ServerSocketManager;

public class UdpServerSocket extends ServerSocketManager {

	private DatagramSocket socket;

	public UdpServerSocket(ServerSocketAdapter serverSocketAdapter, int port) {
		super(serverSocketAdapter);
		try {
			socket = new DatagramSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
			Objects.requireNonNull(serverSocketAdapter);
			serverSocketAdapter.processException(e);
		}
	}

	public UdpServerSocket(ServerSocketAdapter serverSocketAdapter, String localAddress, int port) {
		super(serverSocketAdapter);
		try {
			socket = new DatagramSocket(port, InetAddress.getByName(localAddress));
		} catch (Exception e) {
			e.printStackTrace();
			Objects.requireNonNull(serverSocketAdapter);
			serverSocketAdapter.processException(e);
		}
	}

	public UdpServerSocket(ServerSocketAdapter serverSocketAdapter, InetAddress localAddress, int port, int bufferLength) {
		super(serverSocketAdapter);
		try {
			socket = new DatagramSocket(port, localAddress);
			setBuffer(new byte[bufferLength]);
			setBufferLength(bufferLength);
		} catch (Exception e) {
			e.printStackTrace();
			Objects.requireNonNull(serverSocketAdapter);
			serverSocketAdapter.processException(e);
		}
	}

	@Override
	public void listenSocket() {
		setRunning(true);
		while (isRunning()) {
			try {
				DatagramPacket packet = new DatagramPacket(getBuffer(), getBufferLength());
				socket.receive(packet);
				CompletableFuture.runAsync(() -> {
					getServerSocketAdapter().processRecieveData(packet.getData(), packet.getAddress(), packet.getPort());
				});
			} catch (Exception e) {
				getServerSocketAdapter().processException(e);
			}
		}
	}

	@Override
	public int getPort() {
		if (Objects.isNull(socket)) {
			return -1;
		}
		return 0;
	}

	@Override
	public void send(byte[] buffer, InetAddress address, int port) {
		try {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
			socket.send(packet);
		} catch (Exception e) {
			getServerSocketAdapter().processException(e);
		}
	}

	@Override
	public void send(byte[] buffer, SocketAddress socketAddress) {
		try {
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, socketAddress);
			socket.send(packet);
		} catch (Exception e) {
			getServerSocketAdapter().processException(e);
		}
	}

}
