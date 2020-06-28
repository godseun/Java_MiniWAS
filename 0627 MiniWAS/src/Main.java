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
			try {
				servSocket = new ServerSocket(1230);
				
				Socket socket = servSocket.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String headLine = null;
				while((headLine = br.readLine()) != null) {
					if(headLine.equals("")) {
						break;
					}
					System.out.println(" header :: "+headLine);
				}
				
				OutputStream out = socket.getOutputStream();
				pw = new PrintWriter(new OutputStreamWriter(out));
				
				pw.println("HTTP/1.1 200 OK");
				pw.println("Content-Type: text/html; charset=UTF-8");
				pw.println("Content-Length: "+query.length());
				pw.println("");
				pw.flush();
				
				out.write(b_query);
				out.flush();
				
				System.out.println("\n :: sucess");
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					servSocket.close();
					pw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
