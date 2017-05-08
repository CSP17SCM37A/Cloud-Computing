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
		
			
			long FlopsTotalTime = FlopsTime();
			
			float NumGflops = calculationFlops(FlopsTotalTime);
			
			int q,y=1;
                        q=b%60;
			if(q==0)
                        { 
                          System.out.println(" ------------------------------ Time "+y+" miniute-----------------");
			System.out.println("  Thread-"+NumThreads+"  Time for FLOPS	:"+ FlopsTotalTime+"ms");
                        System.out.println("  Thread-"+NumThreads+"  Number of GFLOPS	:"+ NumGflops*23*3);
                       
                         y++;
 
			}
			
			 
	
	}
	
	public static float calculationFlops(float value){
		
		float flops = ((10000000)/value)/100000;
		return flops;
	}


	


	private long FlopsTime() {
	     
		 double k=6.5f;
                 double m=3.2f;
		  double sum=1f;
			long i;
		  long Initime1 = System.currentTimeMillis();

		  for(i=1;i<=10000000;i++)
		     {    
		    	 sum=sum*k+m/k*k+1/m*m*m*m*m*m*m*m-k-k-k-k-k-k;
 
		    }
                   
		 long FinalTime1 = System.currentTimeMillis() ;
	     long OperationFloatTime=FinalTime1-Initime1;
		  
                 //long time=OperationFloatTime/1000;
		return OperationFloatTime;
	}
	
}


class CpuFlops 
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

                
              
