import java.net.Socket;
import java.net.ServerSocket;
import java.lang.Runnable;
import java.lang.String;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Thread;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;
public class Server {
	private int port;
	private String host;
	private ServerSocket server;
	String log;
	private ArrayList<ChatHost> hosts;
	public Server() throws Exception {
		port = 8081;
		host = "localhost";
		server = new ServerSocket(port);
		log = ""; 
		hosts = new ArrayList<ChatHost>();
	}

	public String toString() {
		return host+port;
	}

	public void start() throws Exception {
		while(true) {
			Socket s = server.accept();
			System.out.println(s);
			try {
} catch(Exception e) {System.out.println("failed on first print: "+e);}
			ChatHost host = new ChatHost(s, this, hosts);
			Thread runner = new Thread(host);
			(new PrintWriter(s.getOutputStream())).write("MSG");
			runner.start();
		}
	}

	public static void main(String[] args) throws Exception{
		try {
		(new Server()).start();
		} catch(Exception e) {System.out.println(e);}
	}

	public void updateLog(String add) throws Exception{
		System.out.println("updating log");
		StringBuilder s = new StringBuilder(log);
		s.append("\n"+add);
		log = s.toString();
	}
	
	public void printLog() {
		System.out.println("Log: "+log);
	}

	public String getLog() {
		return log;
	}

	private class ChatHost implements Runnable {
		Socket socket;
		Server server;
		ArrayList<ChatHost> hosts;
		public ChatHost(Socket s, Server ss, ArrayList<ChatHost> h) throws Exception {
			socket = s;
			server = ss;
			System.out.println(server);
			hosts = h;
			hosts.add(this);
		}
		public void run() {
			System.out.println("began thread");
			try {
			InputStream instream = socket.getInputStream();
			OutputStream outstream = socket.getOutputStream();
			Scanner in = new Scanner(instream);
			in.useDelimiter("\n");
			PrintWriter out = new PrintWriter(outstream);
			while(true) {
				try {
					System.out.println("Grabbing input");
					String inp = in.next();
					System.out.println("Found input: "+inp);
					server.updateLog(inp);
					server.printLog();
				} catch(Exception e) {System.out.println("Closing Connection");break;}
				try {
					System.out.println("Sending log");
					for(ChatHost i:hosts) {
						i.update();
					}
					System.out.println("Sent Log");
				} catch(Exception e) {System.out.println("Closing Connection");break;}
				Thread.sleep(50);
			}
			} catch(Exception e) {System.out.println("Failed at running with: "+e);}
		}
	public void update() {
try {
InputStream instream = socket.getInputStream();
			OutputStream outstream = socket.getOutputStream();
			Scanner in = new Scanner(instream);
			in.useDelimiter("\n");
			PrintWriter out = new PrintWriter(outstream);
		out.print(server.getLog()+"qwert");//"55kasoipwr23%^caleq!@`");
					out.flush();
	} catch(Exception e) {}
}
	}
}
