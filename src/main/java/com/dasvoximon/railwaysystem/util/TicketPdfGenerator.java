package com.dasvoximon.railwaysystem.util;

import com.dasvoximon.railwaysystem.model.entity.Reservation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.openpdf.text.*;
import org.openpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TicketPdfGenerator {

    public static ByteArrayInputStream generateTicket(Reservation reservation) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (Document document = new Document(PageSize.A4)) {
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Paragraph title = new Paragraph("🎟 Railway Ticket");
            title.setAlignment(Element.ALIGN_CENTER);
            title.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
            document.add(title);
            document.add(new Paragraph(" "));

            // Reservation details
            document.add(new Paragraph("PNR: " + reservation.getPnr()));
            document.add(new Paragraph("Passenger: " + reservation.getPassenger().getName().getFirstName()
                    + " " + reservation.getPassenger().getName().getLastName()));
            document.add(new Paragraph("Email: " + reservation.getPassenger().getEmail()));
            document.add(new Paragraph("Train: " + reservation.getSchedule().getRoute().getTrain().getName()
                    + " (" + reservation.getSchedule().getRoute().getTrain().getCode() + ")"));
            document.add(new Paragraph("From: " + reservation.getSchedule().getRoute().getOriginStation().getName()
                    + " → To: " + reservation.getSchedule().getRoute().getDestinationStation().getName()));
            document.add(new Paragraph("Travel Date: " + reservation.getSchedule().getTravelDate()));
            document.add(new Paragraph("Departure: " + reservation.getSchedule().getDepartureTime()
                    + " | Arrival: " + reservation.getSchedule().getArrivalTime()));
            document.add(new Paragraph("Seat Number: " + reservation.getSeatNumber()));
            document.add(new Paragraph("Status: " + reservation.getReservationStatus()));
            document.add(new Paragraph("Fare: ₦" + reservation.getSchedule().getBase_fare()));

            document.add(new Paragraph(" "));

            // Add QR Code for PNR
            Image qrCodeImage = generateQrCodeImage(reservation.getPnr());
            qrCodeImage.scalePercent(50);
            qrCodeImage.setAlignment(Element.ALIGN_CENTER);
            document.add(qrCodeImage);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static Image generateQrCodeImage(String text) throws WriterException, IOException, BadElementException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        var bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(qrImage, "png", baos);

        return Image.getInstance(baos.toByteArray());
    }
}
