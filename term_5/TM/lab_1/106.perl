use strict;
use warnings FATAL => 'all';
while (<>) {
    print if /^.*\b[0-9]+\b.*$/;
}