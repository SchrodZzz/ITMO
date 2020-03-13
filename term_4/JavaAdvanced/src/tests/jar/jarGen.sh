#!/usr/bin/env bash

cd ../../

DIRECTORY="gen"

mkdir ${DIRECTORY}
mkdir -p ${DIRECTORY}/ru/ifmo/ivshin/implementor
mkdir -p ${DIRECTORY}/info/kgeorgiy/java/advanced/implementor

cp ru/ifmo/ivshin/implementor/Implementor.java ${DIRECTORY}/ru/ifmo/ivshin/implementor
cp -R info/kgeorgiy/java/advanced/implementor/* ${DIRECTORY}/info/kgeorgiy/java/advanced/implementor

echo -e "Manifest-Version: 1.0\nMain-Class: ru.ifmo.ivshin.implementor.Implementor" > ${DIRECTORY}/MANIFEST.MF

javac ${DIRECTORY}/ru/ifmo/ivshin/implementor/Implementor.java --source-path ${DIRECTORY}
jar cfm ${DIRECTORY}/implementor.jar ${DIRECTORY}/MANIFEST.MF -C ${DIRECTORY} ru -C ${DIRECTORY} info

rm -r ${DIRECTORY}/ru
rm -r ${DIRECTORY}/info