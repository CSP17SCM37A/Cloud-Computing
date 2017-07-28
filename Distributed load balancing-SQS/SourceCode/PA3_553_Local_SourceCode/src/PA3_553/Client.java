package PA3_553;

import java.io.*; 
 
import java.net.*;

import java.io.BufferedReader;

import java.io.BufferedWriter;

import java.io.DataInputStream;

import java.io.File;

import java.io.FileInputStream;

import java.io.FileWriter;

import java.io.InputStreamReader;

import java.util.Scanner;





class Client {
	
	public static int countLines(String filename) throws IOException {
	    LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
	int cnt = 0;
	String lineRead = "";
	while ((lineRead = reader.readLine()) != null) {}

	cnt = reader.getLineNumber(); 
	reader.close();
	return cnt;
	}
	
  
  public static void main(String args[])
    
    {   
           
       
  try 

         {        
           Scanner scan = new Scanner(System.in);
            
           System.out.println("Enter the Address of SQS:");
  
           String Address;
           //Address= scan.nextLine();
           System.out.println("Enter the port of SQS:");

           int port;
           //port= scan.nextInt();  
           Socket s=new Socket("127.0.0.1",5555);
 
              
     	   System.out.println("connecting to the SQS ");
 
    
           BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/chiru/workspace/PA3_553/src/PA3_553/tasks.txt")));     
           DataInputStream  inp = new DataInputStream(s.getInputStream());
   
           DataOutputStream oup = new DataOutputStream(s.getOutputStream());

          int len=countLines("/home/chiru/workspace/PA3_553/src/PA3_553/tasks.txt");
          String len1 = Integer.toString(len);
     
      

           oup.writeBytes(len1);
   
        String task;
 
          String line;
 
           int k=1;
           while ((line = br.readLine()) != null) 
  
             {
                    
                  

                task=line;
               
                oup.writeBytes(task);
                             
                oup.writeByte('\n');
        
                System.out.println("Task "+k+" sent to SQS ");
   
                 k++;                            
                            
             }

                 
          

       
     br.close();


           String output;
           for(int i=1;i<=k;k++){
            output = inp.readLine();
                                  
            System.out.println(output);
 
            System.out.println("\n");
 
             }              

               
                
            
         } 
 
            
         catch (Exception e)
          
         {
         
            System.err.println("Error: " + e.getMessage());
  
         }
   
}

}


