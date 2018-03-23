package nyist.net;
import java.io.*;
import java.util.*;
import java.util.Scanner;
public class Chance {
	 ArrayList<File> fileList=new ArrayList<File>();
	//ͳ���ַ���
	public int charCounts(File f) throws Exception{
		String str;
		int charCount = 0;
		BufferedReader br=new BufferedReader(new FileReader(f));//��ǰ����Ŀ¼��
		while((str=br.readLine())!=null){
			charCount += str.length();
		}
		File fs=new File("result.txt");
		FileWriter fw = new FileWriter(fs, true);
		PrintWriter pw = new PrintWriter(fw);
		pw.println(f.getName()+",�ַ���: "+charCount);
		pw.flush();//flushʵ���Ͼ��ǽ����е�д�������һ����������ļ��У�֮����йرռ��ɡ����û�ر�����Ҳû����flush����ʱ�����ݲ�δд�뵽�ļ��ġ�
		fw.flush();
		pw.close();
		fw.close();
		return charCount;
	}
	//ͳ�Ƶ�����
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
		pw.println(f.getName()+",������: "+wordCount);
		pw.flush();//flushʵ���Ͼ��ǽ����е�д�������һ����������ļ��У�֮����йرռ��ɡ����û�ر�����Ҳû����flush����ʱ�����ݲ�δд�뵽�ļ��ġ�
		fw.flush();
		pw.close();
		fw.close();
		return wordCount;
	}
	//ͳ��������
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
		pw.println(f.getName()+",������: "+lineCount);
		pw.flush();//flushʵ���Ͼ��ǽ����е�д�������һ����������ļ��У�֮����йرռ��ɡ����û�ر�����Ҳû����flush����ʱ�����ݲ�δд�뵽�ļ��ġ�
		fw.flush();
		pw.close();
		fw.close();
		return lineCount;
	}
	//�ݹ鴦��Ŀ¼�·����������ļ�
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
	//�����У����У�ע����
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
		pw.println(f.getName()+",������/����/ע����: "+codeLine+"/"+spaceLine+"/"+nodeLine);
		pw.flush();//flushʵ���Ͼ��ǽ����е�д�������һ����������ļ��У�֮����йرռ��ɡ����û�ر�����Ҳû����flush����ʱ�����ݲ�δд�뵽�ļ��ġ�
		fw.flush();
		pw.close();
		fw.close();	
	}
	//ͣ�ôʱ�
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
		pw.println(f.getName()+",������: "+count);
		pw.flush();//flushʵ���Ͼ��ǽ����е�д�������һ����������ļ��У�֮����йرռ��ɡ����û�ر�����Ҳû����flush����ʱ�����ݲ�δд�뵽�ļ��ġ�
		fw.flush();
		pw.close();
		fw.close();	
	}
	//main����
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
		}//�ж��Ƿ�ݹ��ȡ��ǰĿ¼�µ��ļ������Ƿ�ʹ��ͣ�ôʱ�
		
		for(int i=0; i<args.length; i++){
			if(args[i].endsWith(".c")){
				filename=args[i];
			}
		}
		File g = new File(filename);
		//��¼����Ŀ���ļ�
		
		if(sj){
			zhj.fileList=zhj.recursion(".");//��ǰ����Ŀ¼
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
					//��ȡresult.txt�ļ�
					BufferedReader br =new BufferedReader(new FileReader("result.txt"));
					while((str=br.readLine())!=null){
						//�����ݸ��Ƶ�output.txt�ļ���
						pw.println(str);
					}
					pw.flush();//flushʵ���Ͼ��ǽ����е�д�������һ����������ļ��У�֮����йرռ��ɡ����û�ر�����Ҳû����flush����ʱ�����ݲ�δд�뵽�ļ��ġ�
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
					//��ȡresult.txt�ļ�
					BufferedReader br =new BufferedReader(new FileReader("result.txt"));
					while((str=br.readLine())!=null){
						//�����ݸ��Ƶ�output.txt�ļ���
						pw.println(str);
					}
					pw.flush();//flushʵ���Ͼ��ǽ����е�д�������һ����������ļ��У�֮����йرռ��ɡ����û�ر�����Ҳû����flush����ʱ�����ݲ�δд�뵽�ļ��ġ�
					fw.flush();
					pw.close();
					fw.close();	
				}
				
			}
		}
		
	}
}
