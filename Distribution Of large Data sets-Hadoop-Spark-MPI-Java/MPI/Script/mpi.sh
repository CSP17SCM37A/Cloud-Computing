chiru@chiru:~/Downloads/Mpi$ chmod 400 chiru.pem


$ git clone git://github.com/jtriley/StarCluster.git
$ cd StarCluster
$ sudo python distribute_setup.py
$ sudo python setup.py install

$ starcluster help

select 2



chiru@chiru:~/Downloads/MPI/StarCluster$ chmod 777 /home/chiru/.starcluster/config
chiru@chiru:~/Downloads/MPI/StarCluster$ /home/chiru/.starcluster/config

gedit config

export AWS_ACCESS_KEY_ID=AKIAJQOIC55IK7WES4HQ
export AWS_SECRET_ACCESS_KEY=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
export AWS_USER_ID =104216196862 
AWS_REGION_NAME = us-east-1c	
AWS_REGION_HOST = ec2.us-east-1c.amazonaws.com

starcluster createkey mykey -o ~/.ssh/mykey.rsa

For 1 node
CLUSTER_SIZE = 1

For 16 nodes 
CLUSTER_SIZE = 16



mpicc Mpi_sort.c -o Mpi_sort
	mpirun -np 2 ./Mpi_sort




chmod 777 gensort
./gensort -a 100000000 /Star/input
