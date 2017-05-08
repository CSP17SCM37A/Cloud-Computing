import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class Mapr extends MapReduceBase implements Mapper<Text,Text,Text,Text> 
{

	
	@Override
	public void map(Text key, Text value, OutputCollector<Text, Text> op,
			Reporter reporter) throws IOException {
		// TODO Auto-generated method stub
		
	}
	

}
