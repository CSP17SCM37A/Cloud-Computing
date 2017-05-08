import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;

class  BlocksizeExec extends Thread
{
    public static long BufferLen;
    public static float[] AverageWrite = new float[2];
	public static double[] AverageRead = new double[2];
	public static float[] AverageLatency = new float[2];
	public static float[] WriteTime = new float[2];
	public static double[] ReadTime = new double[2];
	public static float[] LatencyTime = new float[2];
	
	public static int ThreadNum;
	
	//public BlocksizeExec(int k)
	//{
		
//		BufferLen=k;
	
  //  }
	
	

	public static void Exec() throws InterruptedException 
	{
		BlocksizeExec[] run = new BlocksizeExec[8];
		for (int j = 0; j < 2; j++) 
		{
			int NumOfThreads = (int)Math.pow(2, j);
			for (int i = 0; i < NumOfThreads; i++)
			{
				run[i] = new BlocksizeExec();
				run[i].start();
				run[i].ThreadNum = j;
				Thread.sleep(100);
			}
			BlocksizeExec.AverageWrite[j] = ((WriteTime[j] / NumOfThreads)/ 1000000000)*(1048576/BufferLen);
			BlocksizeExec.AverageRead[j] = ((ReadTime[j] / NumOfThreads)/ 1000000000)*(1048576/BufferLen);
			BlocksizeExec.AverageLatency[j] = (LatencyTime[j] / NumOfThreads) / 1000000;
		}
	}
	public void run()
	{

		try
		{
			FileWrite(BlocksizeExec.BufferLen);
			FileRead();
			Latency();
		} 
		catch (Exception e) {
	    	e.printStackTrace();
		}
	}



	public static void Latency()
	{  try{
		File file = new File("/home/chiru/Desktop/chiru.txt");
		RandomAccessFile access = new RandomAccessFile(file, "rw");
		long StartTime = System.nanoTime();
		
		access.seek(file.length());
		    long FinalTime = System.nanoTime();
	        long TotalTime=FinalTime-StartTime;
	        long latency = TotalTime ;
		access.close();
		        BlocksizeExec.LatencyTime[ThreadNum] = BlocksizeExec.LatencyTime[ThreadNum]
				+ (latency/BufferLen);
	}
	catch (Exception e) {
    	e.printStackTrace();
	}
		
		
	}



public static void FileWrite(long BufferLen) throws Exception 
{
	
	   try{	
		   StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < BufferLen; i++) 
			{
				buffer.append("2");
			}
			File file = new File("/home/chiru/Desktop/chiru.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(
					file.getAbsoluteFile()));
			 long StartTime = System.nanoTime();
			bw.write(buffer.toString());
			 long FinalTime = System.nanoTime();
		        long TotalTime=FinalTime-StartTime;
		  BlocksizeExec.WriteTime[ThreadNum] = BlocksizeExec.WriteTime[ThreadNum]+ TotalTime;
			bw.close();  
		     
          }
    catch (Exception e) {
	e.printStackTrace();
} 
	}

public static void FileRead() throws Exception {	
	
	try{
		File file = new File("/home/chiru/Desktop/chiru.txt");;
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		
		long StartTime = System.nanoTime();
		fis.read(data);
		long FinalTime = System.nanoTime();
	    long TotalTime=FinalTime-StartTime;
	    BlocksizeExec.ReadTime[ThreadNum] = BlocksizeExec.ReadTime[ThreadNum]+ TotalTime;
		
		fis.close();		
	   }
	catch (Exception e) {
    	e.printStackTrace();
	}
}



	public static void result() {
		for (int i = 0; i < 2; i++) {
			System.out.println("Thread-" +(int)Math.pow(2, i) + " Write Speed  in Mbps	: " + (1 / BlocksizeExec.AverageWrite[i]));
			System.out.println("Thread-" +(int)Math.pow(2, i) + " Read  Speed  in Mbps	: " + (1 / BlocksizeExec.AverageRead[i]));
			System.out.println("Thread-" +(int)Math.pow(2, i) + " Latency Time in mSec	: " + BlocksizeExec.AverageLatency[i] + "\n");
		}
	}	
	
	
}

class DiskSeq 
{    
	  

	public static void main(String args[]) throws InterruptedException
        {     
		          int Blocksize;
		          Scanner scan = new Scanner(System.in);
                  System.out.println("1.1B ");
            	  System.out.println("2.1KB");
                  System.out.println("3.1MB ");
                  System.out.println("Select the BockSize: \n"); 
            	  int choice = scan.nextInt(); 
                       
            	          
            		 switch (choice)
            		 {
            		  case 1 :  
            				    Blocksize = 1;
            				    BlocksizeExec.BufferLen = Blocksize;
            					BlocksizeExec.Exec();
            					System.out.println("Sequential R/W Speeds for Block Size	: " + Blocksize + " Byte \n");
            					BlocksizeExec.result();
            					break;   
                                     		                                     
                               
                      case 2 :    Blocksize = 1024;
                                  BlocksizeExec.BufferLen = Blocksize;
  					              BlocksizeExec.Exec();
  					              System.out.println("Sequential R/W Speeds for Block Size	: " + Blocksize + " Bytes \n");
  					              BlocksizeExec.result();
  					              break; 
                                
                      case 3 :    Blocksize = 1024*1024;
                                  BlocksizeExec.BufferLen = Blocksize;
  					              BlocksizeExec.Exec();
  					              System.out.println("Sequential R/W Speeds for Block Size	: " + Blocksize + " Bytes \n");
  					              BlocksizeExec.result();
  					              break;   
                		  
                                 
				              
            		 }  
            	
            		  

       		  	}


}            
            		
        
        
