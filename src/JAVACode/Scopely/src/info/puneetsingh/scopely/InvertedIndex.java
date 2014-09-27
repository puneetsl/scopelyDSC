package info.puneetsingh.scopely;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class InvertedIndex {
	static HashMap<String, ArrayList<String>> InvertedIndex = new HashMap<>();
	public static void main(String[] args) {
		try{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/HashedLikes.csv");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			int lineCount=0;
			while ((strLine = br.readLine()) != null)   {
				String[] likeArr = strLine.split(",");
				String user = likeArr[0]; 
				for (int i = 1; i < likeArr.length; i++) {
					if(InvertedIndex.containsKey(likeArr[i]))
					{
						InvertedIndex.get(likeArr[i]).add(user);
					}
					else
					{
						ArrayList<String> tempArrList = new ArrayList<String>();
						tempArrList.add(user);
						InvertedIndex.put(likeArr[i], tempArrList);
					}
				}
				if(lineCount%1000==0){
					System.err.println (((float)lineCount*100.0f/177797.0f)+"% Done");
				}
				lineCount++;
			}
			Iterator<Entry<String, ArrayList<String>>> i = InvertedIndex.entrySet().iterator();
			while(i.hasNext()){
				String key = i.next().getKey();
				System.out.print(key+", ");
				for (int j = 0; j < InvertedIndex.get(key).size(); j++) {
					System.out.print(InvertedIndex.get(key).get(j)+",");	
				}
				System.out.println();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
