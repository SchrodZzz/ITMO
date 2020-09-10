use strict;
while (<>) {
    s/(\b(\d+)0\b)/\2/g;
    print ;
}