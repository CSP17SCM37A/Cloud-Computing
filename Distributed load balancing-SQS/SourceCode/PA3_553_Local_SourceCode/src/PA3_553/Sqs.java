package PA3_553;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

class SqsQueue {
	  static String newLine = System.getProperty("line.separator");
	  
	  public static Queue queue = new LinkedList();

    void insert(String Item)
    {
       queue.add(Item);


       System.out.println(Item + " Added in to SQS"+ newLine);


       return;
    }
   void remove()
   {
	  
     queue.remove();
      return; 
   }

  String retrieve()
  {
    return (String) (queue.element()); 
  }
  
  String Peekvalue(){
  return (String) (queue.peek());
	
  }

}
class ThreadHandler extends Thread
{
  
     
     private static final String port1 = null;
     Socket News;
     int n;
 
     ThreadHandler(Socket s,int v)
     {
       News=s;
       n=v;
     }

     public void run()
     { 
      try
        {  
           System.out.println("Worker "+n+"  Running the Task.." );
    	   Scanner scan = new Scanner(System.in);
           DataInputStream  inp = new DataInputStream(News.getInputStream());
           DataOutputStream op = new DataOutputStream(News.getOutputStream());
           SqsQueue w = new SqsQueue();
           
           String ta= w.retrieve();	 
          
           //System.out.println(w.retrieve());
           
           String words[] = ta.split(" ");
           
          // System.out.println(words[0]);
           //System.out.println(words[1]);
           
                    
           int time = Integer.parseInt(words[1]);
                          
           Thread.sleep(time);

           StackTraceElement[] ste=Thread.currentThread().getStackTrace();

            if (ste.length == 0)
            {
              String res="value 1";
              System.out.println(res);
             op.writeBytes(res);
             op.writeByte('\n');
            }
            else
            {
             String res="value 0";
             System.out.println(res);
             op.writeBytes(res);
             op.writeByte('\n');
            }

           
                
         System.out.println("Worker "+n+"completed his Task.." );
                 
            w.remove();   
            //System.out.println( w.retrieve());
           

           
	                      
      // News.close();
        }
        
        catch(Exception e) 
        {System.out.println(e);}  
	
  }  
}



public class Sqs
{


public static void main(String[] args) 
{
           int req=1001;
	   try	
	    {      //System.out.println("Service started ");
		   int port=3333;
		   Scanner scan = new Scanner(System.in);
          // port= scan.nextInt();
               
               ServerSocket ss=new ServerSocket(port); 
             
               Socket s=ss.accept();   
               System.out.println("Service started ");
               System.out.println("Enter the number of Workers:   ");
               int numThreads;
               numThreads = scan.nextInt();
               DataInputStream  inp = new DataInputStream(s.getInputStream());
               DataOutputStream oup = new DataOutputStream(s.getOutputStream());
               
               
               SqsQueue quetasks=new SqsQueue();
               @SuppressWarnings("deprecation")
			  // String m=inp.readLine();
               
               int k=Integer.parseInt(inp.readLine());
             
               System.out.println( "Sqs Tasks:\n");
               
               System.out.println("Adding tasks to the Queue\n" );
               for(int i=1;i<=k;i++)
               { 
                  
                 String iptask = inp.readLine();   
                 quetasks.insert(iptask);
               }
             // System.out.println( quetasks.retrieve());
               
              
		while(quetasks.Peekvalue() != null)
               { for(int i=1;i<=numThreads;i++)
                  { //System.out.println( quetasks.retrieve());
              
                    Thread T =new ThreadHandler(s,i);
                     T.start();
                     T.sleep(50);
                     if(quetasks.Peekvalue() == null)
                    break;
                  } 
            
              } 
              
		 System.out.println("All tasks were Done.. \n" );    

 	    }
          catch(Exception e) 
          {System.out.println(e);}  
          }  
}
