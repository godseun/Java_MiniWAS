import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		
		System.out.println(System.getProperty("user.dir")+"\n");
		
		String query = "Hello, World!";
		byte[] b_query = new byte[query.length()];
		for(int i=0; i<query.length(); i++) {
			b_query[i] = (byte) query.charAt(i);
		}
		
		while(true) {
			
			ServerSocket servSocket = null;
			PrintWriter pw = null;
			OutputStream out = null;
			BufferedReader br = null;
			Socket socket = null;
			try {
				servSocket = new ServerSocket(1230);
				
				socket = servSocket.accept();
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String headLine = null;
				while((headLine = br.readLine()) != null) {
					if(headLine.equals("")) {
						break;
					}
					System.out.println(" header :: "+headLine);
				}
				
				out = socket.getOutputStream();
				pw = new PrintWriter(new OutputStreamWriter(out));
				
				pw.println("HTTP/1.1 200 OK");
				pw.println("Content-Type: text/html; charset=UTF-8");
				pw.println("Content-Length: "+query.length());
				pw.println("");
				pw.flush();
				
				out.write(b_query);
				out.flush();
				
				System.out.println("\n :: sucess\n");
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					servSocket.close();
					pw.close();
					out.close();
					br.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
