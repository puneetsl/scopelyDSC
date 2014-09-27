package info.puneetsingh.scopely;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class LikePrior {
	static HashMap<Integer, HashMap<Integer, Integer>> LikeList = new HashMap<Integer, HashMap<Integer,Integer>>();
	public static void main(String[] args) {
		try{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/HashedLikes_woUsers.csv");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			int lineCount=0;
			int count=0;
			
			while ((strLine = br.readLine()) != null)   {
				if(strLine.equals(""))
					continue;
				String[] likeArr = strLine.split(",");
				
				if(likeArr.length<=45&&likeArr.length>3)
				{
					count++;
					for (int i = 0; i < likeArr.length; i++) {
						if(likeArr[i].equals(""))
							continue;
						int Key = Integer.parseInt(likeArr[i]);
						for (int j = 0; j < likeArr.length; j++) {
							if(i==j)
								continue;
							if(likeArr[j].equals(""))
								continue;
							int valKey = Integer.parseInt(likeArr[j]);
							if(LikeList.containsKey(Key))
							{
								if(LikeList.get(Key).containsKey(valKey))
								{
									LikeList.get(Key).put(valKey, LikeList.get(Key).get(valKey)+1);
								}
								else
								{
									LikeList.get(Key).put(valKey, 1);
								}
							}
							else
							{
								HashMap<Integer, Integer> tempMap = new HashMap<Integer, Integer>();
								tempMap.put(valKey, 1);
								LikeList.put(Key, tempMap);
							}
						}
					}
				}
				//				if(lineCount%3000==0&&lineCount!=0)
				//					break;
				if(lineCount%1000==0){
					System.err.println (((float)lineCount*100.0f/177797.0f)+"% Done");
					System.out.println(LikeList.size());
				}

				lineCount++;
			}
			System.out.println("Turnout:"+((float)count*100.0f/(float)lineCount));
			Iterator<Entry<Integer, HashMap<Integer, Integer>>> i = LikeList.entrySet().iterator(); 
			File file = new File("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/LikePrior.csv");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);


			while(i.hasNext()){
				Integer key = i.next().getKey();
				bw.write(key+", ");
				Iterator<Entry<Integer, Integer>> j = LikeList.get(key).entrySet().iterator();
				while(j.hasNext()){
					Integer valKey = j.next().getKey();
					if(LikeList.get(key).get(valKey)>0)//Customizable parameter 3 
						bw.write(valKey+":"+LikeList.get(key).get(valKey)+",");
				}
				bw.write("\n");
			}
			bw.close();
			in.close();
		}catch (Exception e){//Catch exception if any
			e.printStackTrace();
		}
	}
}
