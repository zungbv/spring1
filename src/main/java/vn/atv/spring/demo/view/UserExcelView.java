package vn.atv.spring.demo.view;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import vn.atv.spring.demo.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class UserExcelView extends AbstractXlsxView{

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        List<User> users=(List<User>)map.get("users");

        Sheet sheet=workbook.createSheet("all users");

        Row header=sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("First Name");
        header.createCell(2).setCellValue("Last Name");
        header.createCell(3).setCellValue("Address");
        header.createCell(4).setCellValue("Email");
        header.createCell(5).setCellValue("Phone");
        header.createCell(6).setCellValue("Username");

        int rowCount=1;
        Row userRow;
        for(User user:users){
            userRow=sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(user.getUser_id());
            userRow.createCell(1).setCellValue(user.getFirstName());
            userRow.createCell(2).setCellValue(user.getLastName());
            userRow.createCell(3).setCellValue(user.getAddress());
            userRow.createCell(4).setCellValue(user.getEmail());
            userRow.createCell(5).setCellValue(user.getPhone());
            userRow.createCell(6).setCellValue(user.getUsername());
        }




        httpServletResponse.setContentType("application/ms-excel");
        httpServletResponse.setHeader("Content-disposition","attachment;filename=userList.xlsx");




    }
}
