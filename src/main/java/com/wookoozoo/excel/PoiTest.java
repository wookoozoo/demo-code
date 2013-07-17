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
                    "/home/atell/Dropbox/apps/isv-excel/5月ISV数据核对（11488.31）- 咕咕叽.xlsx"));
            Sheet sheet = wb.getSheetAt(0);

            int c = 0;
            boolean skiped = false;
            for (Row row : sheet) {
                if (!skiped) {
                    skiped = true;
                    continue;
                }
                Date gmtCreate = row.getCell(3).getDateCellValue();
                if (gmtCreate == null) {
                    continue;
                }
              //过滤一些日期
                Date date = DateUtils.parseDate("2013-05-01 00:00:00", new String[] { "yyyy-MM-dd HH:mm:ss" });
                if (gmtCreate.before(date)) {
                    continue;
                }
                date = DateUtils.parseDate("2013-05-23 00:00:00", new String[] { "yyyy-MM-dd HH:mm:ss" });
                if (gmtCreate.after(date)) {
                    continue;
                }
                c++;
                String companyName = null;
                try {
                    companyName = row.getCell(4).getStringCellValue();
                } catch (Exception e) {
                    companyName = String.valueOf(row.getCell(4).getNumericCellValue());
                }
                Date serviceBeginDate = row.getCell(9).getDateCellValue();
                Date serviceEndDate = row.getCell(10).getDateCellValue();
                String bizStatus = "X";
                double payment_amount = Double.valueOf(row.getCell(6).getNumericCellValue());
                double execPrice = Double.valueOf(row.getCell(5).getNumericCellValue());

                System.out.println(c + ":" + gmtCreate + "," + companyName + "," + serviceBeginDate + ","
                        + serviceEndDate + "," + bizStatus + "," + payment_amount + "," + execPrice);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
