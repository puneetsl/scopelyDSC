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
import java.util.Map.Entry;

public class UserPrior {
	static HashMap<String, HashMap<String, Integer>> UserList = new HashMap<String, HashMap<String, Integer>>();
	static Runtime runtime = Runtime.getRuntime();
	public static void main(String[] args) {
		int mb = 1024*1024;
		try{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/InvertedIndex.csv");
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
				String[] userArr = strLine.split(",");

				if(userArr.length<=40&&userArr.length>1)
				{
					count++;
					for (int i = 1; i < userArr.length; i++) {
						if(userArr[i].equals(""))
							continue;
						String Key = userArr[i];
						for (int j = 0; j < userArr.length; j++) {
							if(i==j)
								continue;
							if(userArr[j].equals(""))
								continue;
							String valKey = userArr[j];
							if(UserList.containsKey(Key))
							{
								if(UserList.get(Key).containsKey(valKey))
								{
									UserList.get(Key).put(valKey, UserList.get(Key).get(valKey)+1);
								}
								else
								{
									UserList.get(Key).put(valKey, 1);
								}
							}
							else
							{
								HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
								tempMap.put(valKey, 1);
								UserList.put(Key, tempMap);
							}
						}
					}
				}
				//				if(lineCount%3000==0&&lineCount!=0)
				//					break;
				if(lineCount%10000==0){
					System.err.println (((float)lineCount*100.0f/3558080.0f)+"% Done");
//					System.gc();
					System.err.println("Used Memory:"
							+ (runtime.totalMemory() - runtime.freeMemory()) / mb);
					System.err.println("Size:"+UserList.size()+"\n");
				}

				lineCount++;
			}
			System.out.println("Turnout:"+((float)count*100.0f/(float)lineCount));
			Iterator<Entry<String, HashMap<String, Integer>>> i = UserList.entrySet().iterator(); 
			File file = new File("/home/puneet/Development/College/Intern/Scopely/src/LikesReco/UserPrior.csv");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);


			while(i.hasNext()){
				String key = i.next().getKey();
				if(UserList.get(key).size()>1)
				{
					bw.write(key+", ");
					Iterator<Entry<String, Integer>> j = UserList.get(key).entrySet().iterator();
					while(j.hasNext()){
						String valKey = j.next().getKey();
						if(UserList.get(key).get(valKey)>2)//Customizable parameter 3 
							bw.write(valKey+":"+UserList.get(key).get(valKey)+",");
					}
					bw.write("\n");
				}
			}
			bw.close();
			in.close();
		}catch (Exception e){//Catch exception if any
			e.printStackTrace();
		}
	}

}
