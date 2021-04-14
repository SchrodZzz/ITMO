python3 changer.py

sort -k 2 -n  data/ans/TlkAgg2/ds.tsv >> data/ans/tmp/tmp1.tsv
sort -k 2 -n  data/ans/tmp/tmp1.tsv >> res/TlkAgg2/ds.tsv

sort -k 2 -n  data/ans/TlkAgg2/mv.tsv >> data/ans/tmp/tmp2.tsv
sort -k 2 -n  data/ans/tmp/tmp2.tsv >> res/TlkAgg2/wawa.tsv

sort -k 2 -n  data/ans/TlkAgg2/wawa.tsv >> data/ans/tmp/tmp3.tsv
sort -k 2 -n  data/ans/tmp/tmp3.tsv >> res/TlkAgg2/mv.tsv



sort -k 2 -n  data/ans/TlkAgg5/ds.tsv >> data/ans/tmp/tmp4.tsv
sort -k 2 -n  data/ans/tmp/tmp4.tsv >> res/TlkAgg5/ds.tsv

sort -k 2 -n  data/ans/TlkAgg5/mv.tsv >> data/ans/tmp/tmp5.tsv
sort -k 2 -n  data/ans/tmp/tmp5.tsv >> res/TlkAgg5/wawa.tsv

sort -k 2 -n  data/ans/TlkAgg5/wawa.tsv >> data/ans/tmp/tmp6.tsv
sort -k 2 -n  data/ans/tmp/tmp6.tsv >> res/TlkAgg5/mv.tsv

rm -r data/ans/tmp/
mkdir data/ans/tmp/
