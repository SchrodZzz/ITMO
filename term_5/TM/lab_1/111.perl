use strict;
use warnings FATAL => 'all';
# horosho hronit' zapisi s practik po DM (-:
while (<>) {
    print if /^((1(01*0)*1)*|0)*$/;
}