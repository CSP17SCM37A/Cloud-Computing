import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerServer implements Runnable {
	
	 private static ServerSocket server;
	 private Socket sock;
	
	public static void main(String[] args) {
        try {
            System.out.println("Server connecting...");
            server = new ServerSocket(1111); 
           
            while (true) {
                Socket connection = server.accept();
                
                if (connection != null) {
                	PeerServer s = new PeerServer();
                	s.sock=connection;
            	    Thread t=new Thread(s);
        			t.start();
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
	}


	@Override
	public void run() {
		
		System.out.println(" connection establised successfully \n");
        try {
            InputStream is = sock.getInputStream();
            OutputStream os = sock.getOutputStream();
            File output = new File("file.txt");
            FileOutputStream fos = new FileOutputStream(output);
            byte[] data = new byte[1 * 1024];
            int filelen = 0;
			System.out.println("Initiating Server to Client communication");		
            
			long Initime = System.currentTimeMillis();
            
            try {
                while ((filelen = is.read(data)) > 0) {
                    fos.write(data, 0, filelen);
                    fos.flush();
                }
                fos.close();
            } catch (Exception e) {
            }
            
            long FinalTime = System.currentTimeMillis();
		    long TotalTime=FinalTime-Initime;
            System.out.println("Network I / O Transfer Time	:	" + TotalTime+ " ms");
            System.out.println("file uploading completed! \n");
           
                Thread.sleep(1000);
           
            System.out.println("Initiating Client to Server communication");
            Initime = System.currentTimeMillis();
            FileInputStream fis = new FileInputStream(output);
            filelen = 0;
            while ((filelen = fis.read(data)) > 0) {
                os.write(data, 0, filelen);
                os.flush();
            }
            fis.close();
            os.close();
            sock.close();
            
             FinalTime = System.currentTimeMillis();
		     TotalTime=FinalTime-Initime;
		    
            System.out.println("Network I / O Transfer Time	:	" +TotalTime+ " ms");
			System.out.println("File downloaded");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}
	
}