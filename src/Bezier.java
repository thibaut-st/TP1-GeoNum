/**
 * Created by thibaut on 06/02/15.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

public class Bezier extends JFrame {

    ArrayList<Point2D> ps;
    ArrayList<Point2D> psBezier;
    int nbItertation;

    public Bezier(ArrayList<Point2D> ps, int nbIteration) {
        this.ps = ps;
        this.nbItertation = nbIteration;
    }

    public static ArrayList<Point2D> calculC1(ArrayList<Point2D> ps) {
        ArrayList<Point2D> listPoints = ps;
        ArrayList<Point2D> tempPoints = new ArrayList<Point2D>();
        ArrayList<Point2D> returnList = new ArrayList<Point2D>();
        double x = (listPoints.get(0).getX() + listPoints.get(1).getX()) / 2 + 32;
        double y = (listPoints.get(0).getY() + listPoints.get(1).getY()) / 2 + 32;
        tempPoints.add(new Point2D.Double(x, y));

        for (int i = 1; i < listPoints.size() - 1; i++) {
            double x1 = listPoints.get(i).getX() * 2 - tempPoints.get(i - 1).getX();
            double y1 = listPoints.get(i).getY() * 2 - tempPoints.get(i - 1).getY();
            tempPoints.add(new Point2D.Double(x1, y1));
        }
        for (int i = 0; i < tempPoints.size(); i++) {
            returnList.add(listPoints.get(i));
            returnList.add(tempPoints.get(i));
        }
        returnList.add(listPoints.get(listPoints.size() - 1));
        return returnList;
    }

    public Point2D calculBezier(ArrayList<Point2D> pts, int nbIterate) {
        ArrayList<Point2D> tempPoints = new ArrayList<Point2D>();
        tempPoints.add(pts.get(0));
        for (int j = 0; j < nbIterate; j++) {
            double tValue = ((double) j + 1) / ((double) nbIterate + 1);
            tempPoints.add(calculMiddle(pts, tValue).get(0));
        }
        tempPoints.add(pts.get(pts.size() - 1));
        psBezier = tempPoints;
        return tempPoints.get(0);
    }

    public ArrayList<Point2D> calculMiddle(ArrayList<Point2D> tempPoints, double t) {
        ArrayList<Point2D> tempPoints2 = new ArrayList<Point2D>();
        for (int i = 0; i < tempPoints.size() - 1; i++) {
            double x = tempPoints.get(i).getX() + t * (tempPoints.get(i + 1).getX() - tempPoints.get(i).getX());
            double y = tempPoints.get(i).getY() + t * (tempPoints.get(i + 1).getY() - tempPoints.get(i).getY());
            Point2D middle = new Point2D.Double(x, y);
            tempPoints2.add(middle);
        }
        if (tempPoints2.size() > 1)
            tempPoints2 = calculMiddle(tempPoints2, t);
        return tempPoints2;
    }

    public void diff(ArrayList<Point2D> listP) {
        for (int i = 0; i < listP.size() - 2; i++) {
            System.out.println((listP.get(i + 1).getX() - listP.get(i).getX()) + " // " + (listP.get(i + 1).getY() - listP.get(i).getY()));
        }
    }

    public void paint(Graphics g) {

        g.setColor(Color.black);
        for (int i = 0; i < ps.size() - 1; i++) {
            Line2D l2d = new Line2D.Double(ps.get(i).getX(), ps.get(i).getY(), ps.get(i + 1).getX(), ps.get(i + 1).getY());
            ((Graphics2D) g).draw(l2d);
        }
        g.setColor(Color.red);
        for (int i = 0; i < psBezier.size() - 2; i += 2) {
            Line2D l2d = new Line2D.Double(psBezier.get(i).getX(), psBezier.get(i).getY(), psBezier.get(i).getX(), psBezier.get(i).getY());
            ((Graphics2D) g).draw(l2d);


            QuadCurve2D q = new QuadCurve2D.Double();
            q.setCurve(psBezier.get(i), psBezier.get(i + 1), psBezier.get(i + 2));
            ((Graphics2D) g).draw(q);
        }
    }

    public static void main(String[] args) {
        int nbIteration = 100;
        ArrayList<Point2D> ps = new ArrayList<Point2D>();
        Point2D p0 = new Point2D.Double(100, 100);
        Point2D p1 = new Point2D.Double(300, 200);
        Point2D p2 = new Point2D.Double(200, 300);
        Point2D p3 = new Point2D.Double(100, 400);
        /*Point2D p4 = new Point2D.Double(200, 400);
        Point2D p5 = new Point2D.Double(500, 500);
        Point2D p6 = new Point2D.Double(130, 280);*/
        ps.add(p0);
        ps.add(p1);
        ps.add(p2);
        ps.add(p3);
        /*ps.add(p4);
        ps.add(p5);
        ps.add(p6);*/
        System.out.println(ps);
        ps = calculC1(ps);
        System.out.println(ps);
        Bezier b = new Bezier(ps, nbIteration);
        b.diff(ps);
        b.calculBezier(ps, nbIteration);

        b.setSize(600, 600);
        b.setVisible(true);
    }


}
