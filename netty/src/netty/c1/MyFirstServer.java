package netty.c1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * @author nixx
 *
 */
public class MyFirstServer implements Runnable{
	
	private Socket socket;
	
	public MyFirstServer(Socket socket){
		this.socket = socket;
	}
	
	static Logger log = Logger.getLogger(MyFirstServer.class.getName());
	
	public static void main(String[] args) throws IOException {
		try(ServerSocket serverSocket = new ServerSocket(8080)){
			while(true){
				Socket socket = serverSocket.accept();
				Thread t = new Thread(new MyFirstServer(socket));
				t.start();
			}
		}
	}

    @Override
	public void run() {
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			String request;
			while((request = reader.readLine()) != null){
				if("Done".equals(request)){
					break;
				}
				log.info(request);
				writer.write(request);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
}
