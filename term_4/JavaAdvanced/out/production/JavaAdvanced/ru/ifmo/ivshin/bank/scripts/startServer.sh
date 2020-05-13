#!/usr/bin/env bash
bash compile.sh
cd ../
export CLASSPATH=out/
if [[ -z $(pidof rmiregistry) ]]
then
    rmiregistry &
    echo "Start rmiregistry";
fi
sleep 1
java ru.ifmo.ivshin.bank.Server