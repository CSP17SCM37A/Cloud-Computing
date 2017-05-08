chiru@chiru:~/Downloads/Sharedmemory$ chmod 777 gensort
chiru@chiru:~/Downloads/Sharedmemory$ ./gensort -a 100000000 input

cd Sharedmemory/
chiru@chiru:~/Downloads/Sharedmemory$ chmod 400 chiruoregon.pem
chiru@chiru:~/Downloads/Sharedmemory$ ssh -i /home/chiru/Downloads/Sharedmemory/chiruoregon.pem ubuntu@52.33.58.29



scp -i chiruoregon.pem /home/chiru/Downloads/Sharedmemory/valsort ubuntu@52.33.58.29:/home/ubuntu
scp -i chiruoregon.pem /home/chiru/Downloads/Sharedmemory/gensort ubuntu@52.33.58.29:/home/ubuntu
scp -i chiruoregon.pem /home/chiru/Downloads/Sharedmemory/SharedMemory.java ubuntu@52.33.58.29:/home/ubuntu



lsblk
rm input


sudo mkfs.ext4 /dev/xvdf
sudo mke2fs -F  -t  ext4 /dev/xvdf
sudo mkdir /shared_store
sudo mount /dev/xvdf /shared_store
sudo chmod 777 /shared_store

chmod 777 gensort
./gensort -a 100000000 /shared_store/input



