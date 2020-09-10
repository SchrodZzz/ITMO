use strict;
use warnings FATAL => 'all';
while (<>) {
    print if /^.*(c|C)(a|A)(t|T).*$/;
}