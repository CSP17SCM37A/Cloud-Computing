I’ve mplemented sorting across with MPI (MPI Sort). Here,sorting application  reads a large file and sort it in place where program should be able to sort larger than memory files.Using gensort,I’ve generated a 10GB and 100GB dataset. For MPI Sort, I have to setup MPI on your virtual cluster(Star Cluster) of 16 nodes, and implemented MPI Sort. And Measured the performance of the MPI Sort at 1 node and 16 node scales.

In My code,Initially i am taking initial system time.and i am taking input file from starcluster folder which is generated using gensort of 10GB and 100 GB.It will parse each line from the input file.Here, it parse by string length of key and send the keydd to comparter function.It will compare the keys and retuens the keys and values.Now,sorting will be done by using key and maps the all values.All the sorted data will be gathered and copied into outputfile which will be in starcluster.now i am  taking last system time after sorting.Then i am doing valsort on outputfile and reading first 10 lines and last 10 lines of output file.

Procedure to run the Mpi code:

1.For MPI,we need to install and configure starcluster to run the sort on Amazon ec2.
Firstly,we need to download the starcluster package by,
 git clone git://github.com/jtriley/StarCluster.git
2.now,Navigate to Starcluster and run the distribute_setup.py.
$ cd StarCluster
$ sudo python distribute_setup.py
3.Install the cluster by,
$ sudo python setup.py install
4.Now,select the option to configure the cluster by,
$ starcluster help
Select  2
5.Generate the key by folloeing command and check the key in the config file.
starcluster createkey mykey -o ~/.ssh/mykey.rsa
In Config file,
# match your key name e.g.:
[key mykey]
KEY_LOCATION=~/.ssh/mykey.rsa

6.Now,open the Config file by 

chiru@chiru:~/Downloads/MPI/StarCluster$ chmod 777 /home/chiru/.starcluster/config
chiru@chiru:~/Downloads/MPI/StarCluster$ /home/chiru/.starcluster/config
gedit config
7.After opening the config file,we need to enter the al fileds related to amazon ec2.
AWS_ACCESS_KEY_ID = AKIAJQOIC55IK7WES4HQ
AWS_SECRET_ACCESS_KEY =ANS0tafXPZg+0jK7wvB0Pj++drU9nt8iOpze/MDI 
# replace this with your account number
AWS_USER_ID=104216196862 
# Uncomment to specify a different Amazon AWS region  (OPTIONAL)
# (defaults to us-east-1 if not specified)
# NOTE: AMIs have to be migrated!
#AWS_REGION_NAME = us-east-1c	
#AWS_REGION_HOST = ec2.us-east-1c.amazonaws.com

8.Now Enter the Size of the cluster in congig file.
# number of ec2 instances to launch
For 1 node
CLUSTER_SIZE = 1
For 16 nodes 
CLUSTER_SIZE = 16
9.Now compile the Mpi Sorting code by,
mpicc Mpi_sort.c -o Mpi_sort
10.Run the code by following command
mpirun -np 2 ./Mpi_sort
11.Generate the Gensort of 10gb for 1 node and 100GB for 16 nodes.
chmod 777 gensort
./gensort -a 100000000 /Star/input
12.validate the valsort on output.txt
./valsort output.txt
Now,Take down the first 10 lines and last 10 lines of output.
