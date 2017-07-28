I’ve Implemented the Shared-Memory Sort application using Java and measured its performance on 1 node on a c3.large instance (this will be called Share-Memory Sort); I made  Shared-Memory Sort is multi-threaded (  1-2-4-8 threads)   to take advantage of multiple cores and measured the time to sort on the 10GB dataset.
          Sort is an application which sorts a file-resident dataset,it should be able to sort datasets that are larger than memory. I’ve developed java code ,it runs  on any machine. But I did performance evaluation must be done on Amazon EC2 on a “c3.large” instance. And Measured the time to execute the Sort application on the 10GB dataset produced with the Gensort on 1 node.,Varied the number of threads from 1 to 8,evaluated the best performance. I’ve Saved the first 10 lines of output from the Shared-Memory Sort application, as well as the last 10 lines of output, for each dataset, in Sort-shared-memory-10GB.txt.



In My Sharedmemorycode,Initially i am reading input file  which is generated using gensort of 10GB.and reads the number of threads to run the sort.Based on the number of threads,work will be shared across the threads.and divide the inputfile into chuncks.Each chunk will be of 10GB.I am using map and arrray list to store all the files of each line.Using mapper,I will sort the keys and stored the ouput in output file.After sorting each file, i am merging all the intermediate outfiles into one output file.now i am  taking last system time after sorting.Then i am doing valsort on outputfile and reading first 10 lines and last 10 lines of output file.

Procedure to run the Sharedmemory code:
1.Initiallyy Downnload the AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY form AWS Security Credentials. And export it.

 export AWS_ACCESS_KEY_ID=AKIAJQOIC55IK7WES4HQ
 export AWS_SECRET_ACCESS_KEY=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

 2.Navigate the Directory and add the permission to the chiru.pem.
cd Sharedmemory/
chiru@chiru:~/Downloads/Sharedmemory$ chmod 400 chiru.pem
3.Now connect to the Instance by,
chiru@chiru:~/Downloads/Sharedmemory$ ssh -i /home/chiru/Downloads/Sharedmemory/chiruoregon.pem ubuntu@52.33.58.29
4.And send the Gensort,valsort and javacode to the intstance by following Commands.
scp -i chiru.pem /home/chiru/Downloads/Sharedmemory/valsort ubuntu@52.33.58.29:/home/ubuntu
scp -i chiru.pem /home/chiru/Downloads/Sharedmemory/gensort ubuntu@52.33.58.29:/home/ubuntu
scp -i chiru.pem /home/chiru/Downloads/Sharedmemory/SharedMemory.java ubuntu@52.33.58.29:/home/ubuntu
5. Send the external disk Esb by,
sudo mkfs.ext4 /dev/xvdf
sudo mke2fs -F  -t  ext4 /dev/xvdf
sudo mkdir /shared_store
sudo mount /dev/xvdf /shared_store
sudo chmod 777 /shared_store

6.Create Gensort of 10gb by
chmod 777 gensort
./gensort -a 100000000 /shared_store/input

7.Now install jdk thencompile the Code and run it by,
Javac Sharememory.java
Java Sharedmemory
And we storing outputfile in output.txt

Validation of Output:
8.Add the permission for valsort. 
Chmod 777 valsort
9.Run the Following command to validate the output file.
./valsort output.txt
10.Take the first 10 lines of outputfile by,
Head -10 output.txt
11.Take the flast 10 lines of outputfile by,
tail -10 output.txt
