#!/bin/bash

mkdir -p ~/test && { echo "Catalog test was created succesfully" >> ~/report; touch ~/test/$(date +"%F_%R"); };
ping -c 1 -w 2 "yandex.ru" || echo "Error pinging yandex.ru" >> ~/report
