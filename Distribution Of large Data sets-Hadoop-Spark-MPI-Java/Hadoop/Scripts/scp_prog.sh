pscp -H ubuntu@54.175.243.79 -x "-oStrictHostKeyChecking=no -i nvirginia.pem" -r /home/jay2106/raid_script.sh /home/ubuntu/raid_script.sh
#sleep 10

pssh -i -h host.txt -t 100000000000 -x "-oStrictHostKeyChecking=no -i nvirginia.pem " sudo chmod u+x /home/ubuntu/raid_script.sh	
pssh -i -h host.txt -t 100000000000 -x "-oStrictHostKeyChecking=no -i nvirginia.pem"  sudo ./raid_script.sh 

