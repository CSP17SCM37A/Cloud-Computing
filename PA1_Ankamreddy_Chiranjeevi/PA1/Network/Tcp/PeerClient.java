import java.net.Socket;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


public  class PeerClient implements Runnable {
	

	@Override
	public void run() {
		try {			
			UploadSpeed(file,ThreadNumber);
			Thread.sleep(2000);
			DownloadSpeed(ThreadNumber);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private int ThreadNumber;
	static File file;
	static Socket sock;
	static long buffsize;
	static long FileSize;

	public static void main(String args[])
    {   
              
        try {
             Scanner scan = new Scanner(System.in);
             
            System.out.println("Enter Number of  Threads:");
     	
     		int TotalThreads = scan.nextInt();
     		System.out.println("Enter Buffer Size: 1,1024,65536");
     		long buffsize = scan.nextLong();
  
     		    		
     		System.out.println("Client connecting...");
     		
     		Socket sock = new Socket("localhost", 8888);
     		
     		System.out.println(" connected  successfully:)");
     		 file = new File("file1.txt");
    		long FileSize = file.length();
    		System.out.println("Size of the file		:	" + FileSize + " Byte(s) \n");
        	
    		int i;
    		for ( i = 1; i <= TotalThreads; i++) {
    			PeerClient client = new PeerClient();
    		    client.ThreadNumber = i;
    		    Thread t=new Thread(client);
    			t.start();
    		}	
        		
        		
          }
        catch (Exception e) {
		}
    }


	private void DownloadSpeed(int k) {
		try {
			InputStream is = sock.getInputStream();		
			File output = new File("f1.txt");
			FileOutputStream fos = new FileOutputStream(output);
			int filelen;
			byte[] data = new byte[1 * 1024];
			System.out.println(" Thread"+k);
			System.out.println(" File downloading started");
			
			long Initime = System.currentTimeMillis();
			while ((filelen = is.read(data)) > 0) {
				fos.write(data, 0, filelen);
				fos.flush();
			}
			fos.close();
			is.close();
			sock.close();
			long FinalTime = System.currentTimeMillis();
		    long TotalTime=FinalTime-Initime;
		    float FileSizeinMb = (float)FileSize/1048576;
			float Totalseconds = (float)TotalTime/1000;  
		    float Speed = FileSizeinMb/Totalseconds ;	
						
			System.out.println(" file Dowanloaded");
			System.out.println(" Time taken for download	:	" + TotalTime+ " MilliSecs");
			System.out.println(" Avg download speed of	:	" + Speed + " Mbps");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

	public static void UploadSpeed(File file1, int n) {
		try {
			 OutputStream os = sock.getOutputStream();
			 
			FileInputStream fis = new FileInputStream(file1);
			byte[] data = new byte[(int) buffsize];
			int FileLength;
			System.out.println("Thread"+n);
			System.out.println("File uploading started");
			
			 long Initime = System.currentTimeMillis(); 
			while ((FileLength = fis.read(data)) > 0) {
				os.write(data, 0, FileLength);
				os.flush();
			}
			fis.close();
			long FinalTime = System.currentTimeMillis();
		    long TotalTime=FinalTime-Initime;
		    float FileSizeinMb = (float)FileSize/1048576;
			float Totalseconds = (float)TotalTime/1000;  
		    float Speed = FileSizeinMb/Totalseconds ;	
			
			System.out.println(" File uploaded");
			System.out.println(" Time taken for upload	:	" + TotalTime + " MilliSecs");
			System.out.println(" Avg upload speed of	:	" + Speed + " Mbps");
			}
		catch (Exception e) {
				e.printStackTrace();
			}
		
	}
}