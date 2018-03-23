package nyist.net;
import java.io.*;
import java.util.*;
import java.util.Scanner;
public class Chance {
	 ArrayList<File> fileList=new ArrayList<File>();
	//统计字符数
	public int charCounts(File f) throws Exception{
		String str;
		int charCount = 0;
		BufferedReader br=new BufferedReader(new FileReader(f));//当前工程目录下
		while((str=br.readLine())!=null){
			charCount += str.length();
		}
		File fs=new File("result.txt");
		FileWriter fw = new FileWriter(fs, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(f.getName()+",字符数: "+charCount);
		pw.flush();//flush实际上就是将所有的写入的流，一次性输出到文件中，之后进行关闭即可。如果没关闭流，也没进行flush，此时的内容并未写入到文件的。
		fw.flush();
		pw.close();
		fw.close();
		return charCount;
	}
	//统计单词数
	public int wordCounts(File f) throws Exception{
		String str;
		int wordCount = 0;
		BufferedReader br=new BufferedReader(new FileReader(f));
		while((str=br.readLine())!=null){
			wordCount += str.split(",| ").length;
		}
		File fs=new File("result.txt");
		FileWriter fw = new FileWriter(fs, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(f.getName()+",单词数: "+wordCount);
		pw.flush();//flush实际上就是将所有的写入的流，一次性输出到文件中，之后进行关闭即可。如果没关闭流，也没进行flush，此时的内容并未写入到文件的。
		fw.flush();
		pw.close();
		fw.close();
		return wordCount;
	}
	//统计总行数
	public  int lineCounts(File f) throws Exception{
		String str;
		int lineCount = 0;
		BufferedReader br=new BufferedReader(new FileReader(f));
		while((str=br.readLine())!=null){
			lineCount++;
		}
		File fs=new File("result.txt");
		FileWriter fw = new FileWriter(fs, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(f.getName()+",总行数: "+lineCount);
		pw.flush();//flush实际上就是将所有的写入的流，一次性输出到文件中，之后进行关闭即可。如果没关闭流，也没进行flush，此时的内容并未写入到文件的。
		fw.flush();
		pw.close();
		fw.close();
		return lineCount;
	}
	//递归处理目录下符合条件的文件
	public  ArrayList<File> recursion(String filedir) throws Exception{
		//ArrayList<File> listFiles=new ArrayList<File>();
		File file=new File(filedir);
		File []files=file.listFiles();
		if(files==null)return null;
		for(File f:files){
			if(f.isFile()){
				if(f.getName().endsWith(".c")){
					fileList.add(f);
				}
			}else if(f.isDirectory()){
				recursion(f.getAbsolutePath());
			}
		}
		return fileList;
	}
	//代码行，空行，注释行
	public void complex(File f) throws Exception{
		String str;
		boolean nodeflag = false;
		int codeLine = 0;
		int spaceLine = 0;
		int nodeLine = 0;
		BufferedReader br =new BufferedReader(new FileReader(f));
		while((str=br.readLine())!=null){
			if(str.matches("\\s*/\\*.*")&&str.matches(".*\\*/\\s*")){
				nodeLine++;
		        continue;
			}
			else if(str.matches("\\s*|}\\s*|\\{\\s*")){
				spaceLine++;
			}
			else if(str.matches("//.*")){
				nodeLine++;
			}else if(str.matches("\\s*/\\*.*")){
				nodeLine++;
				nodeflag = true;
			}else if(str.matches(".*\\*/\\s*")){
				nodeLine++;
				nodeflag = false;
			}else if(nodeflag)nodeLine++;
			else codeLine++;
		}
		File fs=new File("result.txt");
		FileWriter fw = new FileWriter(fs, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(f.getName()+",代码行/空行/注释行: "+codeLine+"/"+spaceLine+"/"+nodeLine);
		pw.flush();//flush实际上就是将所有的写入的流，一次性输出到文件中，之后进行关闭即可。如果没关闭流，也没进行flush，此时的内容并未写入到文件的。
		fw.flush();
		pw.close();
		fw.close();	
	}
	//停用词表
	public  void stopWords(File f) throws Exception{
		String str;
		int wordCount=0;
		int stopWord=0;
		BufferedReader br=new BufferedReader(new FileReader(f));
		while((str=br.readLine())!=null){
			String []line= str.split(",| ");
			wordCount+=line.length;
			for(int i=0;i<line.length;i++){
				if(line[i].equals("while"))stopWord++;
				if(line[i].equals("if"))stopWord++;
				if(line[i].equals("switch"))stopWord++;
			}
		}
		int count =wordCount-stopWord;
		File fs=new File("result.txt");
		FileWriter fw = new FileWriter(fs, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(f.getName()+",单词数: "+count);
		pw.flush();//flush实际上就是将所有的写入的流，一次性输出到文件中，之后进行关闭即可。如果没关闭流，也没进行flush，此时的内容并未写入到文件的。
		fw.flush();
		pw.close();
		fw.close();	
	}
	//main函数
	public static void main(String args[]) throws Exception {
		Chance zhj=new Chance();
		int n= args.length;
		boolean sj = false;
		boolean ej = false;
		String filename=null;
		String outputname= args[n-1];
		for(int i=0; i<args.length; i++){
			if(args[i].equals("-s")){
				sj= true;
			}
			if(args[i].equals("-e")){
				ej= true;
			}
		}//判断是否递归获取当前目录下的文件，和是否使用停用词表
		
		for(int i=0; i<args.length; i++){
			if(args[i].endsWith(".c")){
				filename=args[i];
			}
		}
		File g = new File(filename);
		//记录操作目标文件
		
		if(sj){
			zhj.fileList=zhj.recursion(".");//当前工程目录
			for(int i=0; i<args.length; i++){
				if(args[i].equals("-a")){
					for(File f:zhj.fileList){
						zhj.complex(f);
					}
				}
				if(args[i].equals("-l")){
					for(File f:zhj.fileList){
						zhj.lineCounts(f);
					}
				}
				if(args[i].equals("-c")){
					for(File f:zhj.fileList){
						zhj.charCounts(f);
					}
				}
				if(args[i].equals("-w")){
					if(ej){
						for(File f:zhj.fileList){
							zhj.stopWords(f);
						}
					}else{
						for(File f:zhj.fileList){
							zhj.wordCounts(f);
						}
					}
				}
				if(args[i].equals("-o")){
					String str;
					File fs=new File(outputname);
					FileWriter fw=new FileWriter(fs,true);
					PrintWriter pw=new PrintWriter(fw);
					//读取result.txt文件
					BufferedReader br =new BufferedReader(new FileReader("result.txt"));
					while((str=br.readLine())!=null){
						//将数据复制到output.txt文件中
						pw.println(str);
					}
					pw.flush();//flush实际上就是将所有的写入的流，一次性输出到文件中，之后进行关闭即可。如果没关闭流，也没进行flush，此时的内容并未写入到文件的。
					fw.flush();
					pw.close();
					fw.close();	
				}
				
			}
		}
		if(!sj){
			for(int i=0; i<args.length; i++){
				if(args[i].equals("-a")){
						zhj.complex(g);
				}
				if(args[i].equals("-c")){
						zhj.charCounts(g);
				}
				if(args[i].equals("-w")){
					if(ej){
							zhj.stopWords(g);
					}else{
							zhj.wordCounts(g);
					}
				}
				if(args[i].equals("-l")){
						zhj.lineCounts(g);
				}
				if(args[i].equals("-o")){
					String str;
					File fs=new File(outputname);
					FileWriter fw=new FileWriter(fs,true);
					PrintWriter pw=new PrintWriter(fw);
					//读取result.txt文件
					BufferedReader br =new BufferedReader(new FileReader("result.txt"));
					while((str=br.readLine())!=null){
						//将数据复制到output.txt文件中
						pw.println(str);
					}
					pw.flush();//flush实际上就是将所有的写入的流，一次性输出到文件中，之后进行关闭即可。如果没关闭流，也没进行flush，此时的内容并未写入到文件的。
					fw.flush();
					pw.close();
					fw.close();	
				}
				
			}
		}
		
	}
}
