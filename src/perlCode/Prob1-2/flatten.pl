my $file = $ARGV[0];
open my $info, $file or die "Could not open $file: $!";
$h=0;
while( my $line = <$info>)  { 
	chomp($line);
	@vals = split('","',$line);
	@vals[0] = cleanDoubleInverted(@vals[0]);
	@vals[$#vals] = cleanDoubleInverted(@vals[$#vals]);
	$id = shift(@vals);
	foreach(@vals)
	{
		# print $id."~".$_; #With ID
		print cleanDoubleInverted($_); #No ID
		print "\n";
	}
}

sub cleanDoubleInverted()
{
	$text= shift;
	$text =~ s/"//g;
	return $text;
}