#!/bin/bash 
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo chmod 777 /mnt/raid
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo rm -rf /mnt/raid/tmp
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo mkdir -p /mnt/raid/tmp
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo chown -R ubuntu /mnt/raid/tmp
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo chmod 777 -R /mnt/raid/tmp

pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo rm -rf /mnt/raid/tmp/namenode
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo mkdir -p /mnt/raid/tmp/namenode
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo chown -R ubuntu /mnt/raid/tmp/namenode
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo chmod 777 -R /mnt/raid/tmp/namenode

pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo rm -rf /mnt/raid/tmp/datanode
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo mkdir -p /mnt/raid/tmp/datanode
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo chown -R ubuntu /mnt/raid/tmp/datanode
pssh -i -h host.txt -t 100000000000 -x "-i nvirginia.pem" sudo chmod 777 -R /mnt/raid/tmp/datanode

