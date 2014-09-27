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
	shift(@vals);
	if($#vals<$ARGV[1])
	{
		my $combinat = Math::Combinatorics->new(count => 2,data => [@vals],);
		while(my @combo = $combinat->next_combination){
	    	@combo = sort @combo;
	    	print join('_', @combo)."\n";
	  	}
	}
	
}
