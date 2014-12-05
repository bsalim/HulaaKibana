#!/bin/sh
NOW=db_$(date +"%Y_%m_%d_%H%M%S").sql
while true
do
	echo "choose the option below : "
	echo "[1] Update Source"
	echo "[2] compile"
	echo "[3] pack"
	echo "[4] Start"
	echo "[5] Log"
	echo "[7] Backup Database"
	echo "[0] exit"
	read input
	case "$input"
	in
		1) echo "prepare to update source, please wait..."
		   git pull origin master;;
		2) echo "prepare compile project, please wait..."
		   sbt compile;;
		3) echo "prepare Pack, please wait..."
		   sbt clean && sbt pack;;
		4) echo "prepare to start, please wait.."
		   nohup ./target/pack/bin/runner > ~/Mapper.info &;;
		5) echo "Show Log, please wait.."
		   tail -f ~/Mapper.info;;
		7) echo "prepare to backup, please wait.."
		   cd ../db_backup
		   mysqldump -h 54.179.130.8 -u hulaaa -pzUncLScvGq**36F6T5 hulaaa > $NOW
		   echo "Finish, dump to ../db_backup/$NOW";;
		0) exit 1;;
	esac
done

