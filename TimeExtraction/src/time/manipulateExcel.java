package time;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class manipulateExcel {
   public static void wirteToExcel() throws IOException, RowsExceededException, WriteException{
	   //������������writableworkbook�����󣬴�Excel�ļ����ļ��������ڣ��򴴽��ļ�
	   WritableWorkbook writeBook = Workbook.createWorkbook(new File("C://Users//Lynn//Desktop//Academic//LinkedDataProject//markedFile//fileCompare"));
      
	   //�½�������sheet�����󣬲����������ڵڼ�ҳ
	   WritableSheet firstSheet = writeBook.createSheet("Sheet 1", 1);
	   
	   //������Ԫ��label������
	   Label label1 = new Label(1, 2, "Orign file name");//��һ������ָ����Ԫ���������ڶ�������ָ����Ԫ��������������ָ��д���ַ�������
	   firstSheet.addCell(label1);
	   
	   //��������ʼд�ļ�
	   writeBook.write();
	   
	   //�ر���
	   writeBook.close();
   }
}
