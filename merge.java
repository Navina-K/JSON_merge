package jsonMerge;
import java.io.*;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class merge {
	
	public void doMerge(File file,String ip,String op,double size)
    {
        String itr="1";
        String itr2="1";
        JSONArray output=new JSONArray();
		File[] list = file.listFiles();
        if(list!=null)
        for (File fil : list)
        {
            if (fil.isDirectory())
            {
                doMerge(fil,ip,op,size);
            }
            else if ((fil.getName()).startsWith(ip)&&fil.getName().contains(itr) )
            {
            	int a=(Integer.valueOf(itr)+1);
            	itr=Integer.toString(a);
            	String filename=fil.getName();
            	
            	JSONParser jsonParser = new JSONParser();
                try (FileReader reader = new FileReader(filename+".json"))
                {
                    //Read JSON file
                    Object obj = jsonParser.parse(reader);
                    JSONArray input = (JSONArray) obj; 
                    output.addAll(input); 
                    File f = new File(file+op+itr2+".json");
                    if (f.createNewFile())
                    {
                    	FileWriter writer = new FileWriter(f);
                    	writer.write(output.toJSONString());
                    	writer.close();
                    }
                    else
                    {
                    	FileWriter writer = new FileWriter(f);
                    	writer.write(output.toJSONString());
                    	writer.close();
                    }
                    if(f.length()<size)
                    {
                    	continue;
                    }
                    else
                    {
                    	int b=(Integer.valueOf(itr2)+1);
                    	itr2=Integer.toString(b);
                    	for(int i=0;i<output.size();i++)
                    	{
                    		output.remove(i);
                    	}
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                } 
            }
        }
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		merge obj=new merge();
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter source");
		String src=sc.next();
		System.out.println("Enter input file base name");
		String input=sc.next();
		System.out.println("Enter output file base name");
		String output=sc.next();
		System.out.println("Enter maximum file size");
		double size=sc.nextDouble();
		obj.doMerge(new File(src),input,output,size);
		
	}

}
