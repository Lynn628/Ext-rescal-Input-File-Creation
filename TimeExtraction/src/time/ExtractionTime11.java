package time;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;

import com.github.andrewoma.dexx.collection.HashMap;

import edu.stanford.nlp.pipeline.AnnotationPipeline;
import edu.stanford.nlp.util.CoreMap;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import util.ManipulateExcel2;
import util.ReadFilePath;
import util.SUTimeTool2;
/**
 * 
 * @author Lynn
 * @date 3/19/2017
 * @description
 *   ��10�Ļ����Ͻ��иĽ�
 *     ��������ݼ����л�ϣ���ȡ�����е�ʱ����Ϣ����ǣ���List�洢Я��ʱ����Ϣ��property������ÿ��property��Я��ʱ����Ϣ��
 *     ȫ��property���ֵı��أ��Լ�����ÿһproperty��Я��ʱ����Ϣռ����object�����ľ�ֵ
 *     ����Excel����λ��manipulateExcel2
 */
public class ExtractionTime11 {
//�洢���е�timeProperty,property���ظ�
private ArrayList<String> timePropertyList;
//�洢����ʱ����Ϣ��Property��Ӧ��
private HashMap<String, List<Double>> timePropertyTimeProportionList;

//private HashMap<String, >
	
	public ArrayList<String> getTimePropertyList() {
		return timePropertyList;
	}
	
	public void setTimePropertyList(ArrayList<String> timePropertyList) {
		this.timePropertyList = timePropertyList;
	}
	
	public HashMap<String, List<Double>> getTimePropertyTimeProportionList() {
		return timePropertyTimeProportionList;
	}

	public void setTimePropertyTimeProportionList(HashMap<String, List<Double>> timePropertyTimeProportionList) {
		this.timePropertyTimeProportionList = timePropertyTimeProportionList;
	}
	
 public static void main(String[] args) throws IOException, RowsExceededException, WriteException{
		 //��ȡĿ¼·��
		  long t1 = System.currentTimeMillis();
		  System.out.println("Input the directory path:\n");
		  Scanner scanner= new Scanner(System.in);
		  String dirPath = scanner.nextLine();
		  System.out.println("Set the Excel name:\n");
		  String excelName = scanner.nextLine();
		  scanner.close();
		  //Ŀ¼�����·���б�
		  ArrayList<String> pathList = ReadFilePath.readDir(dirPath);
		  Iterator<String> iterator = pathList.iterator();
		   //��ʼ��Excel������
		   WritableWorkbook workbook = Workbook.createWorkbook(new File("C://Users//Lynn//Desktop//Academic//LinkedDataProject//markedFile//fileCompare//" + excelName + ".xls"));
		  System.out.println("WorkBook name~~~~~~" + workbook.toString());
		   //���ù���������
		   WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true);
	       WritableCellFormat wcf = new WritableCellFormat(wf);
		   //�½�������sheet�����󣬲����������ڵڼ�ҳ
		   WritableSheet firstSheet = workbook.createSheet("Sheet 1", 1);
		   ArrayList<Label> columnList = new ArrayList<>();
		   columnList.add(new Label(0, 0, "Orign file path", wcf));
		   columnList.add(new Label(1, 0, "Dst file path", wcf));
		   columnList.add( new Label(2, 0, "Triple amount", wcf));
		   columnList.add(new Label(3, 0, "Time property", wcf));
		   columnList.add(new Label(4, 0,"Time cost(S)", wcf ));
		   int size = columnList.size();
		   for(int k = 0; k < size; k++){
			 firstSheet.addCell(columnList.get(k));
		   }
		//C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\eswc-2013-15-complete.rdf
		  int i = 0;
		  //��ÿһ��rdf�ļ����д���
		  while(iterator.hasNext()){
			  i++;
		  //Դ�ļ�·��
		  String filePath = iterator.next();
		  //Ŀ���ļ��ļ���
		  String fileName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.indexOf("."));
		  FileWriter fileWriter = new FileWriter(new File("C:\\Users\\Lynn\\Desktop\\Academic\\LinkedDataProject\\markedFile\\fileCompare\\conferenceMarkedFile\\" + fileName + ".txt"));
		  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		  //�������󣬽���ָ���ļ�������������Ŀ���ļ���
		  ExtractionTime11 extractionObj = new ExtractionTime11();
		  int tripleNum = extractionObj.timeExtraction(filePath, fileName, bufferedWriter);
		
		  long t2 = System.currentTimeMillis();
		  double timeCost = (t2 - t1)/1000.0;
	      ManipulateExcel2.writeToExcel(workbook, filePath, fileName, tripleNum, extractionObj.getTimePropertyList(), timeCost, i);
	     // System.out.println("ExtractionObj.getTimePropertyList is empty?" + extractionObj.getTimePropertyList().isEmpty());
	      bufferedWriter.close();
		  }
		  workbook.write();
		  workbook.close();
		  System.out.println("End of main~~~~~~");
	  }
 //C:\Users\Lynn\Desktop\Academic\LinkedDataProject\DataSet\SWCC\conferences\www-2007-complete.rdf
	  /**
	   *  
	   * @param filePath
	   * @param dstPath
	   * @param bufferedWriter
	   * @throws IOException
	   */
	  public int timeExtraction(String filePath, String dstPath, BufferedWriter bufferedWriter) throws IOException{
		 //initialize the annotationPipeline
		  AnnotationPipeline pipeline = SUTimeTool2.PipeInit();
		  int lineNum = 1;
		  if(ReadFilePath.filePathValidation(filePath)){
			 Model model = ModelFactory.createDefaultModel();
			 InputStream inputStream = FileManager.get().open(filePath);
			 if(inputStream != null){
				 model.read(inputStream, null);
				 StmtIterator iterator = model.listStatements();
				 //����һ���ռ�����ʱ������Ϣ��Property��set
				ArrayList<String> timePropList = new ArrayList();
				 while(iterator.hasNext()){
					 Statement statement = iterator.next();
					 Resource resource = statement.getSubject();
					 bufferedWriter.write(lineNum++ + "  " +resource.toString() + "    ");
					 Property property = statement.getPredicate();
				     bufferedWriter.write(property.getLocalName() + "    ");
					 RDFNode object = statement.getObject();
					
					 if(object instanceof Resource){
						bufferedWriter.write(object.toString());
						bufferedWriter.newLine();
					 }else{ 
						    List<CoreMap> list = SUTimeTool2.SUTimeJudgeFunc(pipeline, object.toString());
						 if(list.isEmpty()){
							 bufferedWriter.write(object.toString());
							 bufferedWriter.newLine();
						 }else{
							 //������ʱ����Ϣ��property����set��
							 String timeProperty = property.getLocalName();
							 timePropList.add(timeProperty);
							 
							 writeTimeObjectToFile(object, list, bufferedWriter);
						 }
					 }
				 }
				setTimePropertyList(timePropList);
				// System.out.println("Statement Number: " + lineNum);
			 }
	       }
			 return lineNum;
		 }
	  
	  /**
	   * 
	   * @param object
	   * @param list
	   * @param buff
	   * @throws IOException
	   */
	  public static void writeTimeObjectToFile(RDFNode object, List<CoreMap> list, BufferedWriter buff) throws IOException{
		  buff.write(object.toString() + "    ");
		//  System.out.println("Object:" + object.toString() + "\n");
		  int objectLength = object.toString().length();
		  int timeInfoSize = 0;
		  for(CoreMap cm: list){
			//  List<CoreLabel> tokens = cm.get(CoreAnnotations.TokensAnnotation.class);
			//timeInfoSize += tokens.size();
			  timeInfoSize += cm.toString().length();
			  buff.write("<br>" + cm.toString() + "</br>    ");
		  }
		  //ʱ����ϢռLiteral�ı���Ϣ�ı���
		  double percentage = timeInfoSize/(double)objectLength * 100;
		  buff.write(String.valueOf(percentage + "%"));
		  buff.newLine(); 
	  }
}
