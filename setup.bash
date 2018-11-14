#!/binsh

wget https://www-eu.apache.org/dist/spark/spark-2.4.0/spark-2.4.0-bin-hadoop2.7.tgz -P ~/

tar -zxvf ~/spark-2.4.0-bin-hadoop2.7.tgz -C ~/

mv ~/ spark-2.4.0-bin-hadoop2.7 /usr/local/

cd /usr/local/spark-2.4.0-bin-hadoop2.7/

cp ./conf/spark-env.sh.template ./conf/spark-env.sh
