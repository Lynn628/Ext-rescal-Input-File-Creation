package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.jena.rdf.model.Property;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ManipulateExcel {
   //public static WritableWorkbook createExcel( ) throws IOException, RowsExceededException, WriteException{
	   //������������WritableWorkbook�����󣬴�Excel�ļ����ļ��������ڣ��򴴽��ļ�
	  /* WritableWorkbook writeBook = Workbook.createWorkbook(new File("C://Users//Lynn//Desktop//Academic//LinkedDataProject//markedFile//fileCompare//A.xlsx"));
	   //���ù���������
	   WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true);
       WritableCellFormat wcf = new WritableCellFormat(wf);
	   //�½�������sheet�����󣬲����������ڵڼ�ҳ
	   WritableSheet firstSheet = writeBook.createSheet("Sheet 1", 1);
	   ArrayList<Label> columnList = new ArrayList<>();
	   columnList.add(new Label(0, 0, "Orign file path", wcf));
	   columnList.add(new Label(1, 0, "Dst file path", wcf));
	   columnList.add( new Label(2, 0, "Triple amount", wcf));
	   columnList.add(new Label(3, 0, "Time cost", wcf));
	   int size = columnList.size();
	   for(int i = 0; i < size; i++){
		 firstSheet.addCell(columnList.get(i));
	   }*/
	   
	 /*  //������Ԫ��label������
	   Label label1 = new Label(0, 0, "Orign file path");//��һ������ָ����Ԫ���������ڶ�������ָ����Ԫ��������������ָ��д���ַ�������
	   firstSheet.addCell(label1);*/
	   //��������ʼд�ļ�
/*	   writeBook.write();
	   //�ر���
	   //writeBook.close();
	   return writeBook;
   }*/
   
   public static void writeToExcel(WritableWorkbook writeBook, String srcFileName, String dstFileName, 
		   int tripleAmount, double timeCost, int colNum) throws IOException, RowsExceededException, WriteException{
	   
	   WritableSheet firstSheet = writeBook.getSheet("Sheet 1");
	   //System.out.println("************" + firstSheet.getCell(0, 0).getContents());
	   //System.out.println("Whether sheet is null"+ firstSheet == null);
	   WritableFont wf = new WritableFont(WritableFont.TIMES,10,WritableFont.NO_BOLD,true);
       WritableCellFormat wcf = new WritableCellFormat(wf);
	   ArrayList<Label> columnList = new ArrayList<>();
	   columnList.add(new Label(0, colNum, srcFileName, wcf));
	   columnList.add(new Label(1, colNum, dstFileName, wcf));
	   columnList.add(new Label(2, colNum, String.valueOf(tripleAmount), wcf));
	   columnList.add(new Label(3, colNum, String.valueOf(timeCost), wcf));
	   int size = columnList.size();
	   for(int i = 0; i < size; i++){
		 Label content = columnList.get(i);
		 firstSheet.addCell(content);
    }
	   
	   //writeBook.close();
	   System.out.println("In the method of write to excel" + srcFileName + " " + dstFileName +
			                                            " " + tripleAmount + " " + timeCost + 
			                                            " " + colNum);
   }
   
   
   public static void writeToExcel(WritableWorkbook writeBook, String srcFileName, String dstFileName, 
		   int tripleAmount, HashSet<String> timeProperty, double timeCost, int colNum) throws IOException, RowsExceededException, WriteException{
	   
	   WritableSheet firstSheet = writeBook.getSheet("Sheet 1");
	   //System.out.println("************" + firstSheet.getCell(0, 0).getContents());
	   //System.out.println("Whether sheet is null"+ firstSheet == null);
	   Iterator<String> iterator = timeProperty.iterator();
	   String timePropertyStr = "";
	   while(iterator.hasNext()){
		   timePropertyStr += iterator.next().toString() + ";";
	   }
	   WritableFont wf = new WritableFont(WritableFont.TIMES,10,WritableFont.NO_BOLD,true);
       WritableCellFormat wcf = new WritableCellFormat(wf);
	   ArrayList<Label> columnList = new ArrayList<>();
	   columnList.add(new Label(0, colNum, srcFileName, wcf));
	   columnList.add(new Label(1, colNum, dstFileName, wcf));
	   columnList.add(new Label(2, colNum, String.valueOf(tripleAmount), wcf));
	   columnList.add(new Label(3, colNum, timePropertyStr, wcf));
	   columnList.add(new Label(4, colNum, String.valueOf(timeCost), wcf));
	   int size = columnList.size();
	   for(int i = 0; i < size; i++){
		 Label content = columnList.get(i);
		 firstSheet.addCell(content);
    }

   }
   /*public static void main(String[] args) throws RowsExceededException, WriteException, IOException{
	   WritableWorkbook writebook = createExcel();
	   String srcPath = "AAAAAAAAAAAAA";
	   String dStPath = "BBBBBBBBBBBBBBBBBB";
	   writeToExcel(writebook, srcPath, dStPath, 3330, 20.0, 2);
   }*/
   
}
