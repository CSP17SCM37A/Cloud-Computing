import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import freemarker.core.ParseException;

public class Client implements Runnable{

	public static int numJ;
	public static long startTime;
	
	public void client() throws ParseException, FileNotFoundException {
		
		 AWSCredentials credentials = null;
	        try {
	            credentials = new ProfileCredentialsProvider("default1").getCredentials();
	        } catch (Exception e) {
	            throw new AmazonClientException(
	                    "Cannot load the credentials from the credential profiles file. " +
	                    "Please make sure that your credentials file is at the correct " +
	                    "location (/home/chiru/.aws/credentials), and is in valid format.",
	                    e);
	        }

			startTime=System.nanoTime();
			readWorkload(workread.file);
			
			
			long dur=System.nanoTime()-startTime;
			System.out.println("Time1 " + dur/1000000);
			
			waitForResponse();
			
			long dur2=System.nanoTime()-startTime;
			System.out.println("Time2 " + dur2/1000000);
	}
	
	/*
	 * class loops while the client waits for all of the tasks to be returned and checks for duplicates
	 */
	public static void waitForResponse(){
		int finished=0;
		int[] tracker=new int[numJ];
		while (finished<numJ){
			
				
				ReceiveMessageRequest rmr = new ReceiveMessageRequest(workread.responseURL);
	            List<Message> messages = workread.sqs.receiveMessage(rmr).getMessages();
				for (Message message : messages) {
					finished++;
				}
		}
			
				
	}
	
	/*
	 * Class reads input file and creates jobs, which are submitted to jobQ
	 */
	public static void readWorkload(String file) throws FileNotFoundException{
		File f=new File(file);//create file
		Scanner inF=new Scanner(f);//and scanner
		numJ=0;//start number of Jobs at 0
		while (inF.hasNext()){//while more tasks in file...
			String currentL=inF.nextLine();//get line, split
			String[] currentA=currentL.split(" ");
			Job currentJ=new Job(numJ,currentA[0],Integer.parseInt(currentA[1]));//make job with line
            workread.sqs.sendMessage(new SendMessageRequest(workread.taskURL, currentJ.toString()));
			numJ++;//increase number of jobs that need to be processed
		}
		inF.close();//close file
	}

	@Override
	public void run() {//executable by thread
		try {
			
			client();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
