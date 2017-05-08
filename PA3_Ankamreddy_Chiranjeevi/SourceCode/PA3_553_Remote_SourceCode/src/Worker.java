import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;
import com.amazonaws.services.sqs.model.GetQueueAttributesRequest;
import com.amazonaws.services.sqs.model.GetQueueAttributesResult;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;


public class Worker implements Runnable{

	public static long duration3;
    static AmazonDynamoDBClient dynamoDB;
    static String tableName;
	
	public void worker(){
			setupDDB(workread.globalName);//check that DDB is ready to go
			//GetQueueAttributesRequest request = new GetQueueAttributesRequest(ClientDriver.taskURL).withAttributeNames("ApproximateNumberOfMessages");
			List<String> ls=new ArrayList<String>();
			
			//System.out.println(numT.get("ApproximateNumberOfMessages"));

			int count=0;
			//creates executor, that is fixed pool of threads
			
			while (true) {//while another job needs to be processed...
				ExecutorService executor=Executors.newFixedThreadPool(workread.threadNum);
				ls.add("ApproximateNumberOfMessages");//variable to check if items in queue
				Map<String,String> numT=workread.sqs.getQueueAttributes(workread.taskURL, ls).getAttributes();
				if (Integer.parseInt(numT.get("ApproximateNumberOfMessages"))>0){//if items remain...
					ReceiveMessageRequest rmr = new ReceiveMessageRequest(workread.taskURL);//get message request
					List<Message> messages = workread.sqs.receiveMessage(rmr).getMessages();
					for (Message message : messages) {//for the messages received, process them
						Message m1=message;
						Runnable worker = new WorkerHandler(m1.getBody());//start the thread worker
						executor.execute(worker);//execute the worker
						count++;
						if (count%100==0){//only print out every 100 items, to decrease prints to consol
							System.out.println("Tasks Processed: " + count);
						}
					}
					executor.shutdown();//close down executor
					
		//	while (!executor.isTerminated()) {//keep looping if other threads not finished
	//		}
					
	            //System.out.println("Finished all threads");//let user know threads are finished
					//duration3=System.nanoTime()-Client.startTime;//display this time
					//System.out.println("Time3 " + duration3/1000000);
				}
			}
	}
	
	public void setupDDB(String name){
		AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();//get credentials
        } catch (Exception e) {
            throw new AmazonClientException(//let user know credentials are missing
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/home/laine/.aws/credentials), and is in valid format.",
                    e);
        }
        dynamoDB = new AmazonDynamoDBClient(credentials);
        tableName="TASKS";
        if (Tables.doesTableExist(dynamoDB, tableName)) {//if the table does exist, let user know details
            System.out.println("Table " + tableName + " is ACTIVE");
            DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
            TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
            System.out.println("DyanmoDB Table Description: " + tableDescription);
        } else {
        	System.out.println("Table " + tableName + " does not exist, please create and retry");
        }
	}

	@Override//runnable method that starts the worker
	public void run() {
		worker();
			
	}

}