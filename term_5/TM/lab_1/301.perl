use strict;

my $res = "";
while (my $tmp = <>) {
    $tmp =~ s/( ){2,}/\1/g;
    $tmp =~ s/(^ )//g;
    $tmp =~ s/( $)//g;
    $res = $res.$tmp;
}

$res =~ s/^( \n|\n)*//;
$res =~ s/( \n|\n| )*$//;
$res =~ s/([\s]*\n){2,}/\n\n/g;

print $res;