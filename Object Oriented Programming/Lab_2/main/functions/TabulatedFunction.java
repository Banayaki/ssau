package functions;

@SuppressWarnings({"WeakerAccess", "unused"})
public class TabulatedFunction {
    private FunctionPoint[] values;
    private int countOfPoints;

    public TabulatedFunction(double leftX, double rightX, int pointCount) {
        if (pointCount < 2 || Math.abs(leftX - rightX) < Utils.EPS || rightX < leftX) {
            throw new IndexOutOfBoundsException();
        }
        this.values = new FunctionPoint[pointCount + 10];
        this.countOfPoints = pointCount;
        double length = (rightX - leftX) / (pointCount - 1.);
        for (int i = 0; i < pointCount; ++i) {
            this.values[i] = new FunctionPoint(leftX + length * i, 0);
        }
    }

    public TabulatedFunction(double leftX, double rightX, double[] values) {
        if (values == null || values.length == 0 || Math.abs(leftX - rightX) < 10e-14 || rightX < leftX) {
            throw new IndexOutOfBoundsException();
        }
        int pointCount = values.length;
        this.values = new FunctionPoint[pointCount + 10];
        this.countOfPoints = pointCount;
        double length = (rightX - leftX) / (pointCount - 1.);
        for (int i = 0; i < pointCount; ++i) {
            this.values[i] = new FunctionPoint(leftX + length * i, values[i]);
        }

    }

    public double getLeftDomainBorder() {
        return this.values[0].getX();
    }

    public double getRightDomainBorder() {
        return this.values[this.countOfPoints - 1].getX();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public double getFunctionValue(double x) {
        int i;
        if (this.values[0].getX() > x || this.values[this.countOfPoints - 1].getX() < x) return Double.NaN;
        for (i = 1; i < this.countOfPoints && this.values[i].getX() < x; ++i);
        double leftX = this.values[i - 1].getX();
        double leftY = this.values[i - 1].getY();
        double rightX = this.values[i].getX();
        double rightY = this.values[i].getY();
        return ((rightY - leftY) * (x - leftX)) / (rightX - leftX) + leftY;
    }

    public int getPointsCount() {
        return this.countOfPoints;
    }

    public FunctionPoint getPoint(int index) {
        isNotOutOfBounds(index);
        return new FunctionPoint(this.values[index]);
    }

    public void setPoint(int index, FunctionPoint point) {
        isNotOutOfBounds(index);
        if (index == 0) {
            if (this.countOfPoints > index + 1) {
                if (point.getX() <= this.values[index + 1].getX()) {
                    values[index] = new FunctionPoint(point);
                } else {
                    throw new IndexOutOfBoundsException();
                }
            } else { // Если в функции одна точка ноль
                values[index] = new FunctionPoint(point);
            }
        } else if (index == this.countOfPoints - 1) {
            if (this.countOfPoints != 1) {
                if (point.getX() >= this.values[index - 1].getX()) {
                    values[index] = new FunctionPoint(point);
                } else {
                    throw new IndexOutOfBoundsException();
                }
            }
        } else {
            if (point.getX() >= this.values[index - 1].getX() && point.getX() <= this.values[index + 1].getX()) {
                values[index] = new FunctionPoint(point);
            } else {
                throw new IndexOutOfBoundsException();
            }
        }
    }

    public double getPointX(int index) {
        isNotOutOfBounds(index);
        return values[index].getX();
    }

    public void setPointX(int index, double x) {
        isNotOutOfBounds(index);
        setPoint(index, new FunctionPoint(x, values[index].getY()));
    }

    public double getPointY(int index) {
        isNotOutOfBounds(index);
        return values[index].getY();
    }

    public void setPointY(int index, double y) {
        isNotOutOfBounds(index);
        values[index].setY(y);
    }

    public void deletePoint(int index) {
        if (this.countOfPoints < 3) {
            throw new IllegalStateException();
        }
        isNotOutOfBounds(index);
        this.values[index] = null;
        leftShift(index);
        --this.countOfPoints;
    }

    public void addPoint(FunctionPoint point) {
        if (this.values.length - 1 == this.countOfPoints) {
            moveInBiggerArray();
        }
        if (this.values[countOfPoints - 1].getX() >= point.getX()) {
            throw new IndexOutOfBoundsException();
        }
        this.values[countOfPoints++] = new FunctionPoint(point);

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.countOfPoints; i++) {
            builder.append(i).append(": ").append(values[i].toString()).append('\n');
        }
        return builder.toString();
    }

    private void isNotOutOfBounds(int index) {
        if (index < 0 || index >= countOfPoints) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void leftShift(int index) {
        for (; index < this.countOfPoints; ++index) {
            this.values[index] = this.values[index + 1];
        }
    }

    private void moveInBiggerArray() {
        FunctionPoint[] newArray = new FunctionPoint[(int) (this.values.length * 1.6)];
        System.arraycopy(this.values, 0, newArray, 0, this.countOfPoints);
        this.values = newArray;
    }
}
