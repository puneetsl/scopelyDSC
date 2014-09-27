use Math::Combinatorics;
my $file = $ARGV[0];
open my $info, $file or die "Could not open $file: $!";
$max =0;
@arr;
$count=0;
$total=0;
while( my $line = <$info>)  { 
	chomp($line);
	@vals = split(',',$line);
	$first = shift(@vals);
	if(length($first)==32)
	{
		print $line."\n";
	}
	else
	{
		$line = ",".join(',',@vals);
		print $line."\n";
	}
	
}
