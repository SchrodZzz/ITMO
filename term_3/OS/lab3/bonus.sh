userName="bob"

echo | ps -a -u $userName -o pid,cmd | awk '{ print $1 " : " $2 }'
