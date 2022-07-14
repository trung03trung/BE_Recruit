package com.itsol.recruit.service.impl;


import com.itsol.recruit.entity.Job;
import com.itsol.recruit.repository.JobRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

@Service
public class PDFGenerator {
    private final JobRepository jobRepository;

    public PDFGenerator(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

//    public ByteArrayInputStream JobPDFReport(long id) {
//        Job job=jobRepository.findJobById(id);
//        Document document = new Document();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        try {
//
//            PdfWriter.getInstance(document,out);
//            document.open();
//
//            // Add Text to PDF file ->
//            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14,
//                    BaseColor.BLACK);
//            Paragraph para = new Paragraph("Bảng job", font);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            document.add(Chunk.NEWLINE);
//
//            PdfPTable table = new PdfPTable(5);
//            // Add PDF Table Header ->
//            Stream.of("ID", "Tên công việc", "Vị trí công việc","Năm kinh nghiệm","Địa chỉ").forEach(headerTitle ->
//            {
//                PdfPCell header = new PdfPCell();
//                Font headFont = FontFactory.
//                        getFont(FontFactory.HELVETICA_BOLD);
//                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                header.setHorizontalAlignment(Element.ALIGN_CENTER);
//                header.setBorderWidth(2);
//                header.setPhrase(new Phrase(headerTitle, headFont));
//                table.addCell(header);
//            });
//                PdfPCell idCell = new PdfPCell(new Phrase(job.getId().
//                        toString()));
//                idCell.setPaddingLeft(4);
//                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(idCell);
//                PdfPCell nameCell = new PdfPCell(new Phrase
//                    (job.getName()));
//                PdfPCell jobPositionCell = new PdfPCell(new Phrase
//                        (job.getJobPosition().getCode()));
//                PdfPCell numberExperienceCell=new PdfPCell(new Phrase
//                        (job.getNumberExperience()));
//                PdfPCell addressCell=new PdfPCell(new Phrase
//                    (job.getAddressWork()));
//                nameCell.setPaddingLeft(4);
//                nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                table.addCell(nameCell);
//                jobPositionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                jobPositionCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                jobPositionCell.setPaddingRight(4);
//                table.addCell(jobPositionCell);
//                numberExperienceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                numberExperienceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                numberExperienceCell.setPaddingRight(4);
//                 table.addCell(numberExperienceCell);
//                addressCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                addressCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//                addressCell.setPaddingRight(4);
//                 table.addCell(addressCell);
//
//
//            document.add(table);
//
//            document.close();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//        return new ByteArrayInputStream(out.toByteArray());
//    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Id", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Tên công việc", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Vị trí công việc", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Năm kinh nghiệm", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Địa chỉ", font));
        table.addCell(cell);
    }
    private void writeTableData(PdfPTable table, Job job) {
            table.addCell(String.valueOf(job.getId()));
            table.addCell(job.getName());
            table.addCell(job.getJobPosition().getCode());
            table.addCell(job.getNumberExperience());
            table.addCell(String.valueOf(job.getAddressWork()));

    }
    public void export(HttpServletResponse response,Long id) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Tin tuyển dụng", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
        Job job=jobRepository.findJobById(id);
        writeTableHeader(table);
        writeTableData(table,job);

        document.add(table);

        document.close();

    }

}
