package time;

import java.io.File;
/**
 * ��Ҫ���ݣ���ȡRDF�ļ������뵽Model�У���Model���
 */
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.jena.base.Sys;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;

import util.ReadFilePath;
import util.SUTimeTool;	
/**
 * 
 * @author Lynn
 * @Date 2017/3/2
 * //public static String inputFileName = "C:/Users/Lynn/Desktop/Academic/LinkedDataProject/DataSet/SWCC/conferences/dc-2010-complete.rdf";
   //C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\workshops\om-2011-complete.rdf
   //C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\workshops\sdow-2008-complete.rdf
   //C:\Users\Lynn\Desktop\jamendo-rdf\mbz_jamendo.rdf
	//E:\DataSet\DBLP2\testSet\dblp-publications-1944.rdf
	  C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC
 */
public class ExtractionTime2 {
   
	public static void main(String[] args) throws IOException{
	    long timeBegin =  System.currentTimeMillis();
	    String filePathInput = "";
	    String fileName = "";
	    File markedFile = new File("E:\\DataSet\\DBLP2\\markedFile.txt");
	    FileWriter  outStream= new FileWriter(markedFile); 
	   //�ӿ���̨����·���ж�ȡ�ļ������Ըĳɶ�ȡĳdir�µ������ļ�
	    do {
	      System.out.println("Please enter the file path:");
	      /*filePathInput = Console.readLine()*/
	      Scanner sc = new Scanner(System.in);
	      filePathInput = sc.nextLine();
	    } while (!ReadFilePath.filePathValidation(filePathInput));
	    
		ArrayList<String> pathList = ReadFilePath.readDir(filePathInput);
		Iterator<String> iter = pathList.iterator();
	  
	  //ʹ��Jena��FileManager�����ļ��������������ļ�ϵͳ�м���RDF�ļ����������Ѵ��ڵ�Model���ߴ����µ�Model
		Model model = ModelFactory.createDefaultModel();
		Model newModel = ModelFactory.createDefaultModel();
	    //i- indicate the num of file has been read; j - indicate the column of statement 
		int i = 0;
	    int columnNum =0;
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
		   columnNum++;
		   org.apache.jena.rdf.model.Statement statement = iterator.nextStatement();
		   Resource subject = statement.getSubject();
		   Property predicate = statement.getPredicate();
		   RDFNode object = statement.getObject(); 
		   outStream.write(columnNum +"  " + subject.toString() + "     ");
		   //дһ������Ҫ�ֱ��ж��Ƿ����к���ʱ����Ϣ�ĸ�����URI
		   if(SUTimeTool.SUTimeJudgeFunc(predicate.toString())){
			//���predicate����ʱ����Ϣ����predicate���ϱ��,���뵽�ļ���
			   //���ʱ����ʹ��Jena��local path�������������ĳ��ȡ�
			   outStream.write(predicate.toString() + "#*********#     "); 
		   }else{
			   outStream.write(predicate.toString() + "     "); 
		   }
		   //RDFNode�ǰ���Resource��literal�Ľӿڣ����������ж�һ��object��resource��literal������blank node
		   if(object instanceof Resource){
			   if(SUTimeTool.SUTimeJudgeFunc(object.toString())){
			       //object����ʱ����Ϣ�����ϱ�ǣ����
				   outStream.write(object.toString() + "#*********#\r\n\n");
			   }else{
				   outStream.write(object.toString() + "\r\n\n");
			   }
		    }else{
			   if(SUTimeTool.SUTimeJudgeFunc(object.toString())){
			       //object����ʱ����Ϣ�����ϱ�ǣ����
				   outStream.write(object.toString() + "#*********#\r\n\n");
			   }else{
				   outStream.write(object.toString() + "\r\n\n");
		          }
	        }
	    }
	  }
	     System.out.println("\n\n#############################File" + i + "#############################\n\n");
		 long timeEnd =  System.currentTimeMillis();
		 System.out.println("Num of file has been read " + i + "\n");
		 System.out.println("time total cost" + (timeEnd - timeBegin)/1000/60 + "min\n"); 
     
		outStream.close();
	}
   
}

