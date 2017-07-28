#sudo apt-get update -y

#sudo apt-get install mdadm -y
line="yes"
sudo umount -l /dev/xvdb

echo "$line" | sudo mdadm --create --verbose /dev/md0 --level=0 --name=raid_disks --raid-devices=3 /dev/xvdb /dev/xvdc /dev/xvdd

sudo mkfs.ext4 -L raid_disks /dev/md0

sudo mkdir -p /mnt/raid

sudo chmod 777 /mnt/raid

sudo mount LABEL=raid_disks /mnt/raid

sudo cp /etc/fstab /etc/fstab.orig

sudo chmod 777 /etc/fstab

sudo cat >> /etc/fstab <<EOF

LABEL=raid_disks       /mnt/raid   ext4    defaults,nofail        0       2

EOF

sudo mount -a

