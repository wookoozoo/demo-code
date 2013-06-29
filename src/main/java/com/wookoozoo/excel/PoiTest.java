package com.wookoozoo.excel;

import java.io.FileInputStream;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;

public class PoiTest {

    static String[] pattern = new String[] { "yyyy-MM-dd" };

    public static void main(String[] args) {

        try {
            Workbook wb = WorkbookFactory.create(new FileInputStream(
                    "xxxx.xlsx"));
            Sheet sheet = wb.getSheetAt(0);

            int c = 0;
            boolean skiped = false;
            for (Row row : sheet) {
                if (!skiped) {
                    skiped = true;
                    continue;
                }
                c++;
                Date gmtCreate = row.getCell(3).getDateCellValue();
                if(gmtCreate==null){
                    continue;
                }
                String companyName = row.getCell(4).getStringCellValue();
                Date serviceBeginDate = row.getCell(11).getDateCellValue();
                Date serviceEndDate = row.getCell(12).getDateCellValue();
                String bizStatus = "X";
                double payment_amount = Double.valueOf(row.getCell(6).getNumericCellValue());
                double execPrice = Double.valueOf(row.getCell(5).getNumericCellValue());

                System.out.println(c+":"+gmtCreate + "," + companyName + "," + serviceBeginDate + "," + serviceEndDate + ","
                        + bizStatus + "," + payment_amount + "," + execPrice);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
