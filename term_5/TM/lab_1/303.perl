use strict;
use warnings FATAL => 'all';
my $tmp = "";
while (<>) {
    $tmp = $tmp.$_;
}
my %res;
$tmp =~ s/\s+//g;
$tmp =~ s/>([^<].*?)|()</>愛\n</g;
foreach (split "愛", $tmp) {
    $res{$+{host}."\n"} = "愛" if /<a(.*)href="(.+?:\/\/)?(?<host>\w+.*?)[\"\/\:].*>/;
}
foreach (sort keys %res) {
    print $_;
}