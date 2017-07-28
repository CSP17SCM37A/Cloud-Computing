wget www-eu.apache.org/dist/spark/spark-1.6.0/spark-1.6.0-bin-hadoop2.6.tgz
tar -xzvf spark-1.6.0-bin-hadoop2.6.tgz
cd spark-1.6.0-bin-hadoop2.6
cd ec2

export AWS_ACCESS_KEY_ID=AKIAJQOIC55IK7WES4HQ
export AWS_SECRET_ACCESS_KEY=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



./spark-ec2 -k chiru -i /home/chiru/Downloads/Spark/chiru.pem -s 16 -t c3.large --spot-price=0.03 launch spark_workers  ///home/chiru/Downloads/Spark



./spark-ec2 -k chiru -i /home/chiru/Downloads/Spark/chiru.pem  login spark_workers
------------------------------------------------------
for external disk ESB

sudo mkfs.ext4 /dev/xvdh 
sudo mke2fs -F  -t  ext4 /dev/xvdh
sudo mkdir /ebs_store
sudo mount /dev/xvdh /ebs_store
sudo chmod 777 /ebs_store



for hdfs directory

cd /root/bin/ephemeral-hdfs/
bin/hadoop fs -mkdir /sparkdata

cd...
chmod 777 gensort

./gensort -a 1000000000 /ebs_store/sparkdaata

------------------------------------------To chech the Generted Data------------
ssh -i chiru.pem root@54.172.122.246
cd /ebs_store
ls

--------------------------------------------------------------------------------
 cd /root/ephemeral-hdfs/bin


./hadoop fs -Ddfs.replication=1 -put /ebs_store/sparkdaata /sparkdata
ls
pwd

--------------------------------------------

./spark-shell -i /root/sort.scala


bin/hadoop dfs -getmerge /sparkdata/output /ebs_store/expectedoutput





/home/chiru/Downloads/Spark-----------
scp -i chiru.pem /home/chiru/Downloads/Spark/valsort root@54.172.122.246:/root
scp -i chiru.pem /home/chiru/Downloads/Spark/gensort root@54.172.122.246:/root
scp -i chiru.pem /home/chiru/Downloads/Spark/sort.scala root@54.172.122.246:/root


i-86da491d---------h
