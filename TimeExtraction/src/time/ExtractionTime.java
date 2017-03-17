package time;

import java.io.File;
/**
 * ��Ҫ���ݣ���ȡRDF�ļ������뵽Model�У���Model���
 */
import java.io.FileNotFoundException;
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

public class ExtractionTime {
   
   //public static String inputFileName = "C:/Users/Lynn/Desktop/Academic/LinkedDataProject/DataSet/SWCC/conferences/dc-2010-complete.rdf";
   //C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\workshops\om-2011-complete.rdf
   //C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\workshops\sdow-2008-complete.rdf
   //C:\Users\Lynn\Desktop\jamendo-rdf\mbz_jamendo.rdf
	public static void main(String[] args) throws FileNotFoundException{
	   
	   String filePathInput = "";
	   //�ӿ���̨����·���ж�ȡ�ļ������Ըĳɶ�ȡĳdir�µ������ļ�
				    do {
				      System.out.println("Please enter the file path:");
				      /*filePathInput = Console.readLine()*/
				      Scanner sc = new Scanner(System.in);
				      filePathInput = sc.nextLine();
				    } while (!filePathValidation(filePathInput));
	    
		ArrayList<String> pathList = ReadFilePath.readDir(filePathInput);
		Iterator<String> iter = pathList.iterator();
	  
	   //ʹ��Jena��FileManager�����ļ��������������ļ�ϵͳ�м���RDF�ļ����������Ѵ��ڵ�Model���ߴ����µ�Model
		while(iter.hasNext()){
	    Model model = ModelFactory.createDefaultModel();
		String inputFileName = iter.next();	
		System.out.println("**********************fileName" + inputFileName + "\n\n");
		InputStream in = FileManager.get().open(inputFileName);
	    if(in == null){
		   throw new IllegalArgumentException("File" + inputFileName + "not found");
	      }
	    model.read(in, null);
	  //��RDF�ļ�������Model��
	  //��һ�ֱ�׼������ڶ���������ļ���
	   StmtIterator iterator = model.listStatements();
	   while(iterator.hasNext()){
		   org.apache.jena.rdf.model.Statement statement = iterator.nextStatement();
		   //Resource subject = statement.getSubject();
		   Property predicate = statement.getPredicate();
		   System.out.print("*******Predicate " + predicate.toString() + "\n");
		   SUTimeTool.SUTimeFunc(predicate.toString());
		   //RDFNode�ǰ���Resource��literal�Ľӿڣ����������ж�һ��object��resource��literal������blank node
		   RDFNode object = statement.getObject();
		   if(object instanceof Resource){
			   System.out.print("********Obj-Rse " + object.toString()+ "\n");	   
			   SUTimeTool.SUTimeFunc(object.toString());  
		   }else{
			   System.out.print("*******Obj-Liter \"" + object.toString() + "\"\n");
			   SUTimeTool.SUTimeFunc(object.toString());
		   }
		  // System.out.println(" .");
	     }
		}
	
		//System.out.println("\n\nthe size of file is" + pathList.size());
   }
 
   //�ж��ļ��Ƿ����
	  public static Boolean filePathValidation(String filePath) {
	    File file = new File(filePath);
	    if (!file.exists()) {
	      System.out.println("Wrong path or no such file.");
	    }
	    return file.exists();
	  }
}
