#!/bin/sh

mysqld --datadir=./mysql-data --socket=./mysql.sock --port=3307 --initialize-insecure
mysqld --datadir=./mysql-data --socket=./mysql.sock --port=3307 --skip-networking=0 &
