import java.io.File;
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
			for (int i = 0; i <NumOfThreads; i++)
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
        RandomAccessFile File1 = new RandomAccessFile(file, "rw");
        long StartTime = System.nanoTime();
        for(long i=0;i<BufferLen;i++)
	    {	
        	File1.seek((file.length()+i)-BufferLen);
	    }
	        long FinalTime = System.nanoTime();
        long TotalTime=FinalTime-StartTime;
        long latency = TotalTime ;
        
        File1.close();
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
		File file = new File("/home/chiru/Desktop/chiru.txt");
        RandomAccessFile File1 = new RandomAccessFile(file, "rw");
        
        File1.seek(file.length());
       
        if(BufferLen==1)
        {
        	for(long i=0;i<100;i++)
    	    {	
            	File1.write('2');
    	    }
        }
        else
        {
        	for(int i=0;i<BufferLen;i++)
    	    {	
            	File1.write('2');
    	    }
        }
        long StartTime = System.nanoTime();
        for(long i=0;i<BufferLen*10;i++)
	    {	
        	File1.seek((file.length()+i)-BufferLen);
        	File1.write('1');
	    }
        long FinalTime = System.nanoTime();
        long TotalTime=FinalTime-StartTime;
        BlocksizeExec.WriteTime[ThreadNum] = BlocksizeExec.WriteTime[ThreadNum]+ TotalTime;
        File1.close(); 
       }
    catch (Exception e) {
	e.printStackTrace();
} 
	}

public static void FileRead() throws Exception {	
	
	try{
	File file = new File("/home/chiru/Desktop/chiru.txt");
    RandomAccessFile File1 = new RandomAccessFile(file, "r");
    File1.seek(file.length());
    
    byte[] data = new byte[1];
    
    long StartTime = System.nanoTime();
    for(long i=0;i<BufferLen;i++)
    {	
    	File1.seek((file.length()+i)-BufferLen);
    	File1.read(data);
    }
    long FinalTime = System.nanoTime();

    long TotalTime=FinalTime-StartTime;

    BlocksizeExec.ReadTime[ThreadNum] = BlocksizeExec.ReadTime[ThreadNum]+ TotalTime;
   
       System.out.println(" hi " + BlocksizeExec.ReadTime[ThreadNum]);
    File1.close();

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

class DiskRandom 
{    
	  

	public static void main(String args[]) throws InterruptedException
        {     
		          long Blocksize;
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
            					System.out.println("Random R/W Speeds for Block Size	: " + Blocksize + " Byte \n");
            					BlocksizeExec.result();
            					break;   
                                     		                                     
                               
                      case 2 :    Blocksize = 1024;
                                  BlocksizeExec.BufferLen = Blocksize;
  					              BlocksizeExec.Exec();
  					              System.out.println("Random R/W Speeds for Block Size	: " + Blocksize + " Bytes \n");
  					              BlocksizeExec.result();
  					              break; 
                                
                      case 3 :    Blocksize = 1024*8;
                                  BlocksizeExec.BufferLen = Blocksize;
  					              BlocksizeExec.Exec();
  					              System.out.println("Random R/W Speeds for Block Size	: " + Blocksize + " Bytes \n");
  					              BlocksizeExec.result();
  					              break;   
                		  
                                 
				              
            		 }  
            	
            		  

       		  	}


} 
