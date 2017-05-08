import java.util.Date;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.lib.TotalOrderPartitioner;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.util.Tool;	




public class Hs extends Configured implements Tool
{
	public int run(String[] args) throws Exception
	{
		int num_of_mappers=1;
		int num_of_reducers=1;
		Configuration conf=getConf();
		FileSystem fs = FileSystem.get(conf);
		JobConf job=new JobConf(conf,Hs.class);
		job.setJobName("TeraSort");
		if(args.length==6)
		{
			if(args[0].equals("-m"))
			{
				num_of_mappers=Integer.parseInt(args[1]);
			}
			
			if(args[2].equals("-r"))
			{
				num_of_reducers=Integer.parseInt(args[3]);
				
			}
		}
		job.setNumMapTasks(num_of_mappers);
		job.setNumReduceTasks(num_of_reducers);
		Path input=new Path(args[4]);
		Path out=new Path(args[5]);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		Class<? extends InputFormat> inputformat=KeyValueTextInputFormat.class;
		job.setInputFormat(inputformat);
		Class<? extends InputFormat> outputformat=KeyValueTextInputFormat.class;
		job.setInputFormat(outputformat);
		job.setPartitionerClass(TotalOrderPartitioner.class);
		job.setMapperClass((Class<? extends Mapper>) Mapr.class);
		job.setReducerClass((Class<? extends Reducer>) Rdcr.class);
	
		Date startTime = new Date();
		System.out.println("          Job started           " );
		JobClient client =new JobClient();
		client.runJob(job);
		Date end_time = new Date();
		System.out.println("Total Job Time" + (end_time.getTime() - startTime.getTime()) /1000 + " seconds.");
		
		return 0;
	}
	
	public static void main(String[] args) throws Exception
	{
		int result=ToolRunner.run(new Configuration(), new Hs(), args);
		System.exit(result);
	}

	}