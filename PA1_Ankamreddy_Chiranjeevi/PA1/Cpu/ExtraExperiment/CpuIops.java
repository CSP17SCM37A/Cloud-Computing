import java.util.Scanner;

class  NumberOfExperiments extends Thread
{
   public static int NumThreads;
    public static int b;
	
	
	public NumberOfExperiments(int d, int z) {
		
		NumThreads=d;
                 
                b=z;
	
}

	public void run()
	{
		
			
			long IopsTottslTime = IopsTime();

			float NumGiops = calculationFlops(IopsTottslTime);
			
			int q,y=1;
                        q=b%60;
			if(q==0)
                        { 
                          System.out.println(" ------------------------------ Time "+y+" miniute-----------------");
			System.out.println("  Thread-"+NumThreads+"  Time for FLOPS	:"+ FlopsTotalTime+"ms");
                        System.out.println("  Thread-"+NumThreads+"  Number of GFLOPS	:"+ NumGiops*23*3);
                       
                         y++;
 
			}
			
			 
	
	}
	
	public static float calculationFlops(float value){
		
		float flops = ((10000000)/value)/100000;
		return flops;
	}


	


	private long IopsTime() {
		// TODO Auto-generated method stub
		long k=9,m=12,h=2;
             long i;
	     long sum=0;
	   long Initime = System.currentTimeMillis(); 
	     for(i=1;i<=1000000000;i++)
	     {
	    	 sum=sum*k+5*i+i*i-m*i-h;
                  
	     }
	   long FinalTime = System.currentTimeMillis();
      long OperationIntTime=FinalTime-Initime;
	
	return OperationIntTime;
	}
	
}


class CpuIops 
{    
	  

	public static void main(String args[]) throws InterruptedException
        {     
            
            	 
				 int NumThreads; 
                 Scanner scan = new Scanner(System.in);
                 
                 NumberOfExperiments[] Expment = new NumberOfExperiments[8];
                 
                               
                        for(int z=1; z<=600; z++){                                
                                
                       for(int d=1; d<=4; d++)
          			            {
                    	  		
                    	  			Expment[d-1] = new NumberOfExperiments(d,z);
                    	  			Expment[d-1].start();
                    	  			 
                    	  			 //Thread.sleep(1000);
          			            }		 
                                     
                    	          
                                
                     }
            		             
            		
        
        
        }

	

	
  }
