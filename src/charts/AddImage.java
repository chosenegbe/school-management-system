/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
public class AddImage {
    public static void main(String[] args) throws DocumentException, MalformedURLException, IOException {
	Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream("sample2.pdf"));
        document.open();
        document.add(new Paragraph("Sample 1: This is simple image demo."));
        Image img1 = Image.getInstance("school.png");
        Image img = Image.getInstance("teacher1.png");
        img1.scaleToFit(50f, 100f);
        //img.setAlignment(Image.RIGHT);
        img.setAbsolutePosition(450f,PageSize.A4.getHeight() - img.getScaledHeight());
        document.add(img1);
        document.add(img);
        document.close();
     
        
        
    
        
        
       
        System.out.println("Done");
    }
}