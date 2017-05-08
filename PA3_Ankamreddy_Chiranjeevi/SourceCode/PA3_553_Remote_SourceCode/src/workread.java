 import java.util.Scanner;

import org.aspectj.weaver.loadtime.Options;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.model.Option;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import freemarker.core.CommandLine;
import freemarker.core.ParseException;

public class workread {

	public static CommandLine cmd;
	public static Options options;
	public static String file;
	public static int threadNum;
	//private static final int maxTaskNum=100000;
	public static boolean qExsists;
	public static AmazonSQS sqs;
	public static String taskURL;
	public static String responseURL;
	public static String globalName;
		
	public static void main(String[] args) throws ParseException, InterruptedException {
		AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "and is in valid format.",
                    e);
        }

        sqs = new AmazonSQSClient(credentials);
		
		String line="";
		System.out.println("Enter client/worker interface, xxx to exit");//prompt user for input
		Scanner scanner= new Scanner(System.in);
		line=scanner.nextLine();//get user input
		while (!line.equals("xxx")){
			String[] lineArr=line.split(" ");
			//processCLI(lineArr);//process line with commons.cli
			
			if (lineArr[0].equals("client") && qExsists){//if number of threads not included, just start client
				Thread cThread=new Thread(new Client());//create thread for worker
				cThread.start();//start thread
			}else if (lineArr[0].equals("worker") && qExsists){
				Thread wThread=new Thread(new Worker());//create worker thread
				wThread.start();//start worker thread
			}else{
				System.out.println("Input missing client or worker. Please reenter.");
			}
			
			line=scanner.nextLine();//get next line if more input is needed
			System.out.println("Extra time: " + Worker.duration3);
		}
		
		scanner.close();//close scanner		
	}
	
	
/*
	/*
	 * Method processes the input, using the apache commons cli
	 /*
	public static void processCLI(String[] args) throws ParseException{
		
		options=new Options();//create new options
		Option o1=new Option("s", "qname");//set up q name
		o1.setArgs(1);//q requires one argument
		Option o2=new Option("w","workload file");//get workload file name
		o2.setArgs(1);//one argument required for file
		Option o3=new Option("t","number of threads");//set up number of threads
		o3.setArgs(1);//threads requires one argument
		options.addOption(o1);//add the three command line options
		options.addOption(o2);
		options.addOption(o3);
		CommandLineParser parser=new DefaultParser();//create new parser
		cmd=parser.parse(options, args);//parse the input line
		
		if (cmd.hasOption("s")){//show user what values for queue and file and threads are being used
			System.out.println("Queue name: " + cmd.getOptionValue("s"));
			qExsists=exists(cmd.getOptionValue("s"));
			globalName=cmd.getOptionValue("s");
		}
		if (cmd.hasOption("w")){
			file=cmd.getOptionValue("w");
			System.out.println("Input file is: " + cmd.getOptionValue("w"));
		
		}
		if (cmd.hasOption("t")){
			threadNum=Integer.parseInt(cmd.getOptionValue("t"));
			System.out.println("Number of threads:  " + cmd.getOptionValue("t"));
		}	
	}
	*/
	/*
	 * Check if the queue, specified by the user, exists
	 * Returns true/false
	 */
	public static boolean exists(String qname){
		boolean found=false;
		GetQueueUrlRequest getQURLRequest=new GetQueueUrlRequest(qname);//queue request
		try {
			String url=sqs.getQueueUrl(getQURLRequest).getQueueUrl();//get queue url
		
			GetQueueUrlRequest getQURLRequest2=new GetQueueUrlRequest("RESPONSE");
			String url2=sqs.getQueueUrl(getQURLRequest2).getQueueUrl();
			System.out.println(qname + " url " + url);//Show what queue is being used
			if (url!=null){//if the url exists
				ListQueuesResult queues=sqs.listQueues();//get all queue
				for (String urlist : queues.getQueueUrls()){
					if (urlist.equalsIgnoreCase(url)){//if one queue matches print out that it exists
						taskURL=urlist;
						System.out.println("Queue exists.");
						found=true;
						break;
					}if(urlist.equalsIgnoreCase(url2)){
						responseURL=urlist;//double checks that response queue exists as well
					}
				}
			}else{//if no queue found...
				System.out.println("Queue " + qname + " does not exist, please create and try again");
			}
			return found;
		}catch (QueueDoesNotExistException e){//catch exception of queue not found
			System.out.println("Queue not found, try again with new queue");
			return false;
		}
	}
	

}
