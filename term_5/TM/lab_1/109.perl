use strict;
use warnings FATAL => 'all';
while (<>) {
    print if /(^[^\s].*[^\s]$)|^\S*$/;
}