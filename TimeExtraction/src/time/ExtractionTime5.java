package time;

import java.io.BufferedWriter;
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
/**
 * 
 * @author Lynn
 * ��ExtractionTime4�Ļ��������bufferд�ļ�
 */
//C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\workshops\sdow-2008-complete.rdf    28kb
//C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\conferences\dc-2010-complete.rdf    145kb
public class ExtractionTime5 {
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
	    //�������ʱ����ĵ��洢λ��
	    File markedFile = new File("C:\\Users\\Lynn\\Desktop\\Academic\\LinkedDataProject\\markedFile\\fileCompare\\" + fileName + ".txt");
	    FileWriter  outStream = new FileWriter(markedFile); 
	    BufferedWriter bufferedWriter = new BufferedWriter(outStream);
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
		bufferedWriter.write("**********************fileName********\n" + inputFileName + "\n\n");
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
		   bufferedWriter.write(j + "  " + subject.toString() + "     ");
		   bufferedWriter.write(predicate.getLocalName() + "     "); 
		   
		   //RDFNode�ǰ���Resource��literal�Ľӿڣ����������ж�һ��object��resource��literal������blank node
		   if(object instanceof Resource){
			   if(SUTimeTool.SUTimeJudgeFunc(object.toString())){
				   System.out.println("Object Local name" + object.toString());
			       //object����ʱ����Ϣ�����ϱ�ǣ����
				   bufferedWriter.write("<br>" + object.toString() + "<br/>\r\n\n");
			   }else{
				   bufferedWriter.write(object.toString() + "\r\n\n");
			   }
		    }else{//object��literal
			   if(SUTimeTool.SUTimeJudgeFunc(object.toString())){
			       //object����ʱ����Ϣ�����ϱ�ǣ����
				   bufferedWriter.write("<br>" + object.toString() + "<br/>\r\n\n");
			   }else{
				   bufferedWriter.write(object.toString() + "\r\n\n");
		          }
	        }
		   bufferedWriter.newLine();
	    }
	  }
		bufferedWriter.close();
	     System.out.println("\n\n#############################File" + i + "#############################\n\n");
		 long timeEnd =  System.currentTimeMillis();
		 System.out.println("Num of file has been read " + i + "\n");
		 System.out.println("time total cost: " + (timeEnd - timeBegin)/1000.0/60.0 + "min\n"); 
		 bufferedWriter.close();
	}
}
