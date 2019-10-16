net stop Dnscache
::ping –n 1 localhost > nul
net start > servicesWithLate.txt
fc /A servicesWithLate.txt services.txt > servicesDiff.txt
net start Dnscache
