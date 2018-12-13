#!/binsh
export HADOOP_HOME=/usr/local/hadoop-2.7.3
export PATH=$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH

if [ 1 -eq 0 ]; then
wget https://www-eu.apache.org/dist/spark/spark-2.4.0/spark-2.4.0-bin-hadoop2.7.tgz -P ~/

tar -zxvf ~/spark-2.4.0-bin-hadoop2.7.tgz -C ~/

sudo mv ~/spark-2.4.0-bin-hadoop2.7 /usr/local/

cd /usr/local/spark-2.4.0-bin-hadoop2.7/

sudo cp ./conf/spark-env.sh.template ./conf/spark-env.sh
fi

sudo cp ./conf/slaves.template ./conf/slaves

# need to do "sudo hdfs dfs -mkdir xxx" in namenode.

export SPARK_HOME=/usr/local/spark-2.4.0-bin-hadoop2.7
export PATH=$PATH:$SPARK_HOME/bin

echo " export HADOOP_CONF_DIR=/usr/local/hadoop/hadoop-2.7.3/etc/hadoop" >> /usr/local/spark-2.4.0-bin-hadoop2.7/conf/spark-env.sh
echo " export HADOOP_HOME=/usr/local/hadoop-2.7.3" >> /usr/local/spark-2.4.0-bin-hadoop2.7/conf/spark-env.sh
echo " export SPARK_HOME=/usr/local/spark-2.4.0-bin-hadoop2.7" >> /usr/local/spark-2.4.0-bin-hadoop2.7/conf/spark-env.sh
echo " export SPARK_MASTER_IP=resourcemanager" >> /usr/local/spark-2.4.0-bin-hadoop2.7/conf/spark-env.sh
echo " export SPARK_WORKER_MEMORY=256m " >> /usr/local/spark-2.4.0-bin-hadoop2.7/conf/spark-env.sh
echo " export SPARK_WORKER_CORES=1" >> /usr/local/spark-2.4.0-bin-hadoop2.7/conf/spark-env.sh
#sudo scp -r /usr/local/spark-2.4.0-bin-hadoop2.7 slave1:/usr/local/

echo "slave0" >> ./conf/slaves
echo "slave1" >> ./conf/slaves
echo "slave1" >> ./conf/slaves
