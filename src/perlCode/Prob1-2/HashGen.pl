use List::MoreUtils qw/ uniq /;
my $file = $ARGV[0];
open my $info, $file or die "Could not open $file: $!";
$hash;
$i=1;
while( my $line = <$info>)  { 
	chomp($line);
	$hash{$line} = $i;
	$i++;
}
$file = $ARGV[1];
open my $info, $file or die "Could not open $file: $!";
while( my $line = <$info>)  { 
	chomp($line);
	@vals = split('","',$line);
	$id = shift(@vals);
	print $id.",";
	undef(@lnArr);
	foreach(@vals)
	{
		$like = cleanDoubleInverted($_); 
		$like = $hash{$like};
		push(@lnArr,$like);	
	}
	@lnArr = sort @lnArr;
	@unique = uniq @lnArr;

	print join(",",@unique);
	print "\n";
}

sub cleanDoubleInverted()
{
	$text= shift;
	$text =~ s/"//g;
	return $text;
}