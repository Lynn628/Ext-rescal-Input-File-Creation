package time;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;

import util.ReadFilePath;
import util.SUTimeTool;
//C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\workshops\om-2011-complete.rdf
//C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\workshops\sdow-2008-complete.rdf    28kb
/**
 * 
 * @author Lynn
 * 1.�����ָ����Ǻõ���Ԫ�����洢���ļ����Ĺ���
 * 2.
 */
public class ExtractionTime3 {
	public static void main(String[] args) throws IOException{
	    long timeBegin =  System.currentTimeMillis();
	    String filePathInput = "";
	    String fileName = "";
	    Scanner sc = new Scanner(System.in);
	    //�ӿ���̨����·���ж�ȡ�ļ������Ըĳɶ�ȡĳdir�µ������ļ�
	    do {
	      System.out.println("Please enter the file path:");
	      /*filePathInput = Console.readLine()*/
	      filePathInput = sc.nextLine();
	      System.out.println("Please enter the marked file name");
	      fileName= sc.nextLine();
	     } while (!ReadFilePath.filePathValidation(filePathInput));
	    sc.close();
	    
	    File markedFile = new File("D:\\markedFile\\" + fileName + ".txt");
	    FileWriter  outStream= new FileWriter(markedFile); 
	    
		ArrayList<String> pathList = ReadFilePath.readDir(filePathInput);
		Iterator<String> iter = pathList.iterator();
	   //ʹ��Jena��FileManager�����ļ��������������ļ�ϵͳ�м���RDF�ļ����������Ѵ��ڵ�Model���ߴ����µ�Model
		Model model = ModelFactory.createDefaultModel();
	    //i- indicate the num of file has been read; j - indicate the column of statement 
		int i = 0;
	   //colunm Number
		int j =0;
		while(iter.hasNext()){
			i++;
		String inputFileName = iter.next();	
		System.out.println("**********************fileName********\n" + inputFileName + "\n\n");
		InputStream in = FileManager.get().open(inputFileName);
	    if(in == null){
		   throw new IllegalArgumentException("File" + inputFileName + "not found");
	      }
	    //��RDF�ļ�������Model��
	    model.read(in, null);
	    //��һ�ֱ�׼������ڶ���������ļ���
	   StmtIterator iterator = model.listStatements();
	   while(iterator.hasNext()){
		   j++;
		   org.apache.jena.rdf.model.Statement statement = iterator.nextStatement();
		   Resource subject = statement.getSubject();
		   Property predicate = statement.getPredicate();
		   RDFNode object = statement.getObject(); 
		   System.out.println("subject local name " + subject.toString() +"\n");
		   outStream.write(j + "  " + subject.toString() + "     ");
		   //дһ������Ҫ�ֱ��ж��Ƿ����к���ʱ����Ϣ�ĸ�����URI
		   if(SUTimeTool.SUTimeJudgeFunc(predicate.getLocalName())){
			//���predicate����ʱ����Ϣ����predicate���ϱ��,���뵽�ļ���
			   //���ʱ����ʹ��Jena��local path�������������ĳ��ȡ�
			   outStream.write(predicate.getLocalName() + "#*********#     "); 
		   }else{
			   outStream.write(predicate.getLocalName() + "     "); 
		   }
		   //RDFNode�ǰ���Resource��literal�Ľӿڣ����������ж�һ��object��resource��literal������blank node
		   if(object instanceof Resource){
			   if(SUTimeTool.SUTimeJudgeFunc(((Resource) object).getLocalName()) && ((Resource) object).getLocalName() != null){
				   System.out.println("Predicate Local name" + ((Resource) object).getLocalName());
			       //object����ʱ����Ϣ�����ϱ�ǣ����
				   outStream.write("<br>" + ((Resource) object).getLocalName() + "<br/>\r\n\n");
			   }else{
				   outStream.write(((Resource) object).getLocalName() + "\r\n\n");
			   }
		    }else{//object��literal
			   if(SUTimeTool.SUTimeJudgeFunc(object.toString())){
			       //object����ʱ����Ϣ�����ϱ�ǣ����
				   outStream.write("<br>" + object.toString() + "<br/>\r\n\n");
			   }else{
				   outStream.write(object.toString() + "\r\n\n");
		          }
	        }
	    }
	  }
	     System.out.println("\n\n#############################File" + i + "#############################\n\n");
		 long timeEnd =  System.currentTimeMillis();
		 System.out.println("Num of file has been read " + i + "\n");
		 System.out.println("time total cost: " + (timeEnd - timeBegin)/1000.0/60.0 + "min\n"); 
		 outStream.close();
	}
}
