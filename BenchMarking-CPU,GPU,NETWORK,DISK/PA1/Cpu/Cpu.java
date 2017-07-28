import java.util.Scanner;

class  NumberOfExperiments extends Thread
{
   public static int NumThreads;
	
	
	public NumberOfExperiments(int i) {
		
		NumThreads=i;
	// TODO Auto-generated constructor stub
}

	public void run()
	{
		
			
			long FlopsTotalTime = FlopsTime();
			long IopsTottslTime = IopsTime();
			float NumGflops = calculationFlops(FlopsTotalTime);
			float NumGiops = calculationFlops(IopsTottslTime);
			
			
			System.out.println("  Thread-"+NumThreads+"  Time for FLOPS	:"+ FlopsTotalTime+"ms");
			System.out.println("  Thread-"+NumThreads+"  Time for IOPS	:"+ IopsTottslTime+"ms");
		        System.out.println("  Thread-"+NumThreads+"  Number of GFLOPS	:"+ NumGflops*23*3);
			System.out.println("  Thread-"+NumThreads+"  Number of GIOPS	:"+ NumGiops*24);
			
			 
	
	}
	
	public static float calculationFlops(float value){
		value = value/1000;
		float flops = ((1000000000)/value)/1000000000;
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


	private long FlopsTime() {
		// TODO Auto-generated method stub
		 double k=6.5f;
                 double m=3.2f;
		  double sum=1f;
			long i;
		  long Initime1 = System.currentTimeMillis(); 
		  for(i=1;i<=1000000000;i++)
		     {
		    	 sum=sum*k+m/k*k+1/m*k*5*m*k*m*k+m+m-m;
		     }
		 long FinalTime1 = System.currentTimeMillis() ;
	     long OperationFloatTime=FinalTime1-Initime1;
		
		return OperationFloatTime;
	}
	
}


class Cpu 
{    
	  

	public static void main(String args[]) throws InterruptedException
        {     
            
            	 
				 int NumThreads; 
                 Scanner scan = new Scanner(System.in);
                 
                 NumberOfExperiments[] Expment = new NumberOfExperiments[8];
                 
                                
                     		   	
       		    char ch;
				do{            
           		  
            	  System.out.println("1.one thread ");
            	  System.out.println("2.Two threads");
                  System.out.println("3.Four Threads ");
                  System.out.println("Enter How many threads to Run: \n"); 
            	  int choice = scan.nextInt(); 
                       
            
 	          
            		 switch (choice)
            		 {
            		  case 1 :  for(int i=1; i<=1; i++)
            			       {
            			          //NumberOfExperiments.NumThreads = i;
            			    
            			          Expment[i-1] = new NumberOfExperiments(i);
            			          Expment[i-1].start();
            			          
            			          System.out.println(" Thread "+i+" Started");
            			     	 Thread.sleep(1000);
            			       }		
                		                                   
                               break;   
                                     		                                     
                               
                      case 2 :    for(int i=1; i<=2; i++)
          			            {
                    	  		
                    	  			Expment[i-1] = new NumberOfExperiments(i);
                    	  			Expment[i-1].start();
                    	  			 System.out.println(" Thread "+i+" Started");
                    	  			 Thread.sleep(1000);
          			            }		 
                                     
                    	        break;   
                                
                      case 3 :for(int i=1; i<=4; i++)
                      			{
                    	  			
                    	  			Expment[i-1] = new NumberOfExperiments(i);
                    	  			Expment[i-1].start();
                    	  			 System.out.println(" Thread "+i+" Started");
                    	  			 Thread.sleep(1000);
                      			}		 
                		  
                                break;  
				              
            		 }  
            	
            		 System.out.println("Do you want to continue (Type y or n) \n");
                     ch = scan.next().charAt(0);   
                      String str = Character.toString(ch);		 

       		  	}while (ch == 'Y'|| ch == 'y');               
            		
        
        
        }

	

	
  }

                
              

               
                
                
 

