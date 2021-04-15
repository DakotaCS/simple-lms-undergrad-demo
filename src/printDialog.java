//I declare that this project is my own work and that all material previously written or published in any source by any other person has been duly acknowledged in the assignment. 
//I have not submitted this work, or a significant part thereof, previously as part of any academic program. 
//In submitting this assignment I give permission to copy it for assessment purposes only.
//Dakota C. Soares

//: src/printDialog.java

/***********************************************************************
 * Assignment3, COMP482
 * Class: printDialog.java
 * 
 * Purpose: This class creates an object of type printDialog
 * 
 * @author: (Unknown): https://www.javaknowledge.info/print-jtextarea-using-printerjob-in-java/
 * 
 * Student ID: 3318342
 * @date: February 26th, 2021 
 * 
 * Notes: see included report for test cases, parameters, etc. 
 * 
 */


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.RepaintManager;
 
/**
 * Sourced from: https://www.javaknowledge.info/print-jtextarea-using-printerjob-in-java/
 * @author User
 */
public class printDialog implements Printable {
 
    private Component print_component;
 
    public static void printComponent(Component c) {
        new printDialog(c).doPrint();
    }
 
    public printDialog(Component comp) {
        this.print_component = comp;
    }
 
    public void doPrint() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException pe) {
                System.out.println("Error printing: " + pe);
            }
        }
    }
 
    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return (NO_SUCH_PAGE);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            disableDoubleBuffering(print_component);
            print_component.paint(g2d);
            enableDoubleBuffering(print_component);
            return (PAGE_EXISTS);
        }
    }
 
    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }
 
    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }
}
//end of class PrintDialog.java