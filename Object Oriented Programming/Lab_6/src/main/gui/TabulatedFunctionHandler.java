package gui;

import functions.*;
import functions.exceptions.InappropriateFunctionPointException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TabulatedFunctionHandler implements TabulatedFunctionImpl, Cloneable {

    private static final String root = "./saves/";
    private String filename;
    private TabulatedFunctionImpl function;
    private boolean isModified;


    public TabulatedFunctionHandler() {
        this.function = null;
        this.filename = null;
        this.isModified = false;
    }

    public TabulatedFunctionHandler(String filename) throws FileNotFoundException {
        this.filename = root + filename;
        this.function = TabulatedFunctions.readTabulatedFunction(new FileReader(this.filename));
        this.isModified = false;
    }

    public TabulatedFunctionHandler(String filename, TabulatedFunctionImpl function) {
        this.function = function;
        this.filename = root + filename;
        this.isModified = false;
    }

    public void newFunction(double leftX, double rightX, int pointsCount) {
        function = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        isModified = true;
    }

    public void saveFunction() throws IOException {
        if (!isFilenameAssigned()) {
            System.err.println("Before save need to assign filename");
        }
        TabulatedFunctions.writeTabulatedFunction(function, new FileWriter(filename));
        isModified = false;
    }

    public void saveFunctionAs(String filename) throws IOException {
        this.filename = root + filename;
        saveFunction();
    }

    public void loadFunction() throws FileNotFoundException {
        function = TabulatedFunctions.readTabulatedFunction(new FileReader(filename));
    }

    public void loadFunction(String filename) throws FileNotFoundException {
        function = TabulatedFunctions.readTabulatedFunction(new FileReader(root + filename));
        this.filename = root + filename;
    }

    public void tabulateFunction(FunctionImpl function, double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
        this.function = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        isModified = true;
    }

    public void tabulateFunction(double leftX, double rightX, int pointsCount) throws InappropriateFunctionPointException {
        function = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        isModified = true;
    }

    public boolean isModified() {
        return isModified;
    }

    public boolean isFilenameAssigned() {
        return filename != null;
    }

    @Override
    public int getPointsCount() {
        return function.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) {
        return function.getPoint(index);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        function.setPoint(index, point);
        isModified = true;
    }

    @Override
    public double getPointX(int index) {
        return function.getPointX(index);
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        function.setPointX(index, x);
        isModified = true;
    }

    @Override
    public double getPointY(int index) {
        return function.getPointY(index);
    }

    @Override
    public void setPointY(int index, double y) {
        function.setPointY(index, y);
        isModified = true;
    }

    @Override
    public void deletePoint(int index) {
        function.deletePoint(index);
        isModified = true;
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        function.addPoint(point);
        isModified = true;
    }

    @Override
    public void addPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        function.addPoint(index, point);
        isModified = true;
    }

    @Override
    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return function.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return function.getFunctionValue(x);
    }

    @Override
    public String toString() {
        return function.toString();
    }

    @Override
    public int hashCode() {
        return function.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return function.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
