sudo apt-get update

sudo apt-get install memcached

# change to run on all network interfaces
sudo sed -i 's/127.0.0.1/0.0.0.0/g' /etc/memcached.conf 

# restart memcached after the above config change
sudo service memcached restart

