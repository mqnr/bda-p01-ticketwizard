#!/bin/sh

mysql -u root --socket=./mysql-data/mysql.sock < sql/schema.sql &&
mysql -u root --socket=./mysql-data/mysql.sock < sql/example_data.sql
