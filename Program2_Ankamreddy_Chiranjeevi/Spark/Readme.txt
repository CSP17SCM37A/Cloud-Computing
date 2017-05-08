In spark, we need to Install Linux distribution in a virtual machine on Amazon EC2 and setup virtual cluster of 1 node and 17 nodes(1Master and 16 slaves) on Amazon EC2 using c3.large instance. Moreover, Add an additional EBS volume while creating your instance to load the dataset for16  multi-node cluster. And configure it to run the HDFS file system in order to be able to handle the 100GB dataset. We should turn off data replication on the HDFS file system to ensure you get the most performance out of my system. Also configured  RDD storage in such a way that it  do not create replicas of 100GB data; this will also give you the best performance at the cost of resilience in case of failures.  In Spark Sort application,  I evaluated its performance on 1 node and 16 nodes, did strong scaling experiments from 1 node to 16 nodes.
In My Script,Initially i am taking system time.and i am taking input file from hdfs which is generated using gensort of 10GB and 100 GB.It will parse each line from the input file.Here, it parse by sstring length of 10 and drop of string of length 10.Now,ssorting will be done by key and maps the all values.All the sorted data will be stored in Sharedoutput file.and taking system time after sorting.Then i am doing valsort on outputfile and reading first 10 lines and last 10 lines of output file.

Procedure to run the Scala code:

1 Node:
Here,we need to Setup virtual cluster of 1 node on Amazon EC2 using c3.large instance and sort the 10GB of Dataset.
1.	Initially we need to download the package of Spark and extract it by following Commands:
wget www-eu.apache.org/dist/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz
   tar -xzvf spark-1.6.0-bin-hadoop2.6.tgz

   2.Now navigate the Ec2 folder by,
    
   cd spark-1.6.0-bin-hadoop2.6
   cd ec2
  
   3.Get the AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY form AWS Security                                          Credentials. And export it.

 export AWS_ACCESS_KEY_ID=AKIAJQOIC55IK7WES4HQ
 export AWS_SECRET_ACCESS_KEY=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

  4.Add permission for chiru.pem file by,
  chmod 400 Parth.pem  
   And execute the command for buils the cluster for 1 node.





./spark-ec2 -k chiru -i /home/chiru/Downloads/Spark/chiru.pem -s 1 -t c3.large --spot-price=0.03 launch spark_workers 

5.Now Login into the cluster(master node) by following command.


./spark-ec2 -k chiru -i /home/chiru/Downloads/Spark/chiru.pem  login spark_workers

And the export the data form Local storage by,
scp -i chiru.pem /home/chiru/Downloads/Spark/valsort root@54.172.122.246:/root
scp -i chiru.pem /home/chiru/Downloads/Spark/gensort root@54.172.122.246:/root
scp -i chiru.pem /home/chiru/Downloads/Spark/sort.scala root@54.172.122.246:/root


6.Send the external disk Esb by,


sudo mkfs.ext4 /dev/xvdh 
sudo mke2fs -F  -t  ext4 /dev/xvdh
sudo mkdir /ebs_store
sudo mount /dev/xvdh /ebs_store
sudo chmod 777 /ebs_store

7.Navigate to Hdfs directory and add permission for gensort to create dataset.


cd /root/bin/ephemeral-hdfs/
bin/hadoop fs -mkdir /sparkdata

cd...
chmod 777 gensort

8.Now create Dataset of 10Gb by,

./gensort -a 1000000000 /ebs_store/sparkdaata
Inorder To check the Generted Data,enter the following Command.
ssh -i chiru.pem root@54.172.122.246
cd /ebs_store
ls

9.Move to hdfs data set.

cd /root/ephemeral-hdfs/bin

./hadoop fs -Ddfs.replication=1 -put /ebs_store/sparkdaata /sparkdata
ls

10.Execute the scala code to sort the 10Gb Data set by,

Spark-shell -i /root/sort.scala

And notedown the Readings.

11.Copy the Expected output from Hdfs store by,


bin/hadoop dfs -getmerge /sparkdata/output /ebs_store/expectedoutput


Validation of Output:
12.Add the permission for valsort. 
Chmod 777 valsort
13.Run the Following command to validate the output file.
./valsort expectedoutput
14.Take the first 10 lines of outputfile by,
Head -10 expectedoutput
15.Take the flast 10 lines of outputfile by,
tail -10 expectedoutput



16 Nodes:
In 16 Nodes,I need to  virtual cluster of 1 node and 17 nodes(1Master and 16 slaves) on Amazon EC2 using c3.large instance. Moreover, Add an additional EBS volume while creating your instance to load the dataset for16  multi-node cluster. And configure it to run the HDFS file system in order to be able to handle the 100GB dataset
1.	Initially we need to download the package of Spark and extract it by following Commands:
wget www-eu.apache.org/dist/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz
   tar -xzvf spark-1.6.0-bin-hadoop2.6.tgz

   2.Now navigate the Ec2 folder by,
    
   cd spark-1.6.0-bin-hadoop2.6
   cd ec2
  
   3.Get the AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY form AWS Security                                          Credentials. And export it.

 export AWS_ACCESS_KEY_ID=AKIAJQOIC55IK7WES4HQ
 export AWS_SECRET_ACCESS_KEY=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

  4.Add permission for chiru.pem file by,
  chmod 400 Parth.pem  
   And execute the command for buils the cluster for 1 node.





./spark-ec2 -k chiru -i /home/chiru/Downloads/Spark/chiru.pem -s 16 -t c3.large --spot-price=0.03 launch spark_workers 

5.Now Login into the cluster(master node) by following command.


./spark-ec2 -k chiru -i /home/chiru/Downloads/Spark/chiru.pem  login spark_workers

And the export the data form Local storage by,
scp -i chiru.pem /home/chiru/Downloads/Spark/valsort root@54.172.122.246:/root
scp -i chiru.pem /home/chiru/Downloads/Spark/gensort root@54.172.122.246:/root
scp -i chiru.pem /home/chiru/Downloads/Spark/sort.scala root@54.172.122.246:/root


6.Send the external disk Esb by,


sudo mkfs.ext4 /dev/xvdh 
sudo mke2fs -F  -t  ext4 /dev/xvdh
sudo mkdir /ebs_store
sudo mount /dev/xvdh /ebs_store
sudo chmod 777 /ebs_store

7.Navigate to Hdfs directory and add permission for gensort to create dataset.


cd /root/bin/ephemeral-hdfs/
bin/hadoop fs -mkdir /sparkdata

cd...
chmod 777 gensort

8.Now create Dataset of 100Gb by,

./gensort -a 10000000000 /ebs_store/sparkdaata
Inorder To check the Generted Data,enter the following Command.
ssh -i chiru.pem root@54.172.122.246
cd /ebs_store
ls

9.Move to hdfs data set.

cd /root/ephemeral-hdfs/bin

./hadoop fs -Ddfs.replication=1 -put /ebs_store/sparkdaata /sparkdata
ls

10.Execute the scala code to sort the 100Gb Data set by,

Spark-shell -i /root/sort.scala

And notedown the Readings.

11.Copy the Expected output from Hdfs store by,


bin/hadoop dfs -getmerge /sparkdata/output /ebs_store/expectedoutput


Validation of Output:
12.Add the permission for valsort. 
Chmod 777 valsort
13.Run the Following command to validate the output file.
./valsort expectedoutput
14.Take the first 10 lines of outputfile by,
Head -10 expectedoutput
15.Take the flast 10 lines of outputfile by,
tail -10 expectedoutput


