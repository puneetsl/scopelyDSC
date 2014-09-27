my $file = $ARGV[0];
#head -100000 UniqueLikes.csv |tail -1
open my $info, $file or die "Could not open $file: $!";
$h=0;
while( my $line = <$info>)  { 
	chomp($line);
	@vals = split(',',$line);
	$freq = @vals[0];
	$pairtext = @vals[1];
	@pairs = split('_',$pairtext);
	$one = `head -@pairs[0] UniqueLikes.csv |tail -1`;
	chomp($one);
	$two = `head -@pairs[1] UniqueLikes.csv |tail -1`;
	chomp($two);
	print $one."_".$two.",".$freq."\n";
}