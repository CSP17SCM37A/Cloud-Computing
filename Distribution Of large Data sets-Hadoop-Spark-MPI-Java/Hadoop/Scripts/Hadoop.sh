// Initially download the Pem form Amazon AWS  ,and  chiru.pem and Hadoop xml Scripts files at /home/ec2-user/  location.
//Add permission to chiru .pem file using following command 
 
chmod 400 chiru.pem  
3.Generate the key for agent Session by following command. 

eval `ssh-agent -s` 
ssh-add chiru.pem 
 
4.Install the Haddop by following command,
-Install Hadoop  
And execute the script Hadoopinstall.sh and RAID 0 the underlying Disk.

5.Set the all the  environment variables in .bashrc file 
 
export CONF=/usr/local/hadoop/etc/hadoop 
export JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk-1.7.0.95.x86_64 export HADOOP_INSTALL=/usr/local/hadoop export PATH=$PATH:$HADOOP_INSTALL/bin export PATH=$PATH:$HADOOP_INSTALL/sbin export HADOOP_COMMON_HOME=$HADOOP_INSTALL export HADOOP_MAPRED_HOME=$HADOOP_INSTALL export YARN_HOME=$HADOOP_INSTALL export HADOOP_PREFIX=/usr/local/hadoop export HADOOP_HDFS_HOME=$HADOOP_INSTALL export HADOOP_CONF_DIR=/usr/local/hadoop/etc/hadoop export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar 
6.Now save the Bashrc and execute the fllowing command,
  ~/.bashrc 
 
7.now,Update the following  xml files at path  /usr/local/hadoop/etc/hadoop  with same configuaration as provided in hadoopsetup folder. 
•	mapred-site.xml 
•	core-site.xml • hdfs-site.xml 
•	yarn-site.xml 
 
8.Create AMI of Master Node and 1 slave for 1 node,and create 16 slaves instances using AMI of master node. 
 

9.Update  the slaves file of Master at location /usr/local/hadoop/etc/hadoop and invoke the IP’s of all 16 running slaves. 
 
10.Copy slaves file to following path  /home/ec2-user 
 
11.now,configure_slaves.sh  on slave nodes and execute configure_slaves.sh on  slaves nodes using following commands from master node. And copy all files on slaves node.
 
scp -i chiru.pem -r /home/ec2-user/slaves slavesip:/home/ec2-user 
scp -i chiru.pem -r /home/ec2-user/configure_slaves.sh  slavveip:/home/ec2-user 
12. Running configure_slaves  ssh -i  chiru.pem slavesip '/home/ec2-user/configure_slaves.sh' 
13.On the Mater node execute the following command to format name node 
 
hadoop namenode -format 
 
14.now Execute the script  c on Master Node. 
 
15.Use Jps command and you will see the following running proces on the Master Node 
•	NameNode 
•	Secondary NameNode 
•	Resource Manager 
 
16.Please check the cluster report using following command                           
       Hadoop dfsadmin  -report   and you will see 16 live Data nodes. 
17. create a imput file under new directory.
hadoop fs -mkdir input 
18.Add input.txt file in input folder   
hadoop fs -put input.txt input 
19.Now Compile java program and run it. 
hadoop hadoop com.sun.tools.javac.Main  HadoopSort.java 
20.Creating jar from .class file 
jar cf  HadoopSort.jar  *.class 
21.Now,Execute jar hadoop jar HadoopSort.jar Sample argument1 argument2 The following are the Now,I’ve to modify these configuration files as you go to a multi-node run on  hadoop 2.7.2 cluster. 
