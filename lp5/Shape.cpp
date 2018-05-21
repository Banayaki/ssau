#include "Shape.h"

Shape::Shape() : BrokenLine() {
}

Shape::Shape(const Shape &shape) : BrokenLine(shape) {
    if (this->countOfPoints > 1 && this->countOfPoints != this->lines.size())
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
}

Shape::Shape(const Point &point) : BrokenLine(point) {}

Shape::Shape(const BrokenLine &polygon) : BrokenLine(polygon) {
    if (this->countOfPoints > 1 && this->countOfPoints != this->lines.size())
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
}

Shape::Shape(const vector<Point> &points) : BrokenLine(points) {
    if (this->countOfPoints > 1 && this->countOfPoints != this->lines.size())
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
}

Shape::~Shape() = default;

ifstream &operator>>(ifstream &stream, Shape &shape) {
    Shape target(getBrokenLine(stream));
    shape += target;
    return stream;
}

Shape &Shape::operator=(const Shape &shape) {
    if (this != &shape) {
        Shape shp(shape);
        BrokenLine::swap(*this, shp);
    }
    return *this;
}

Shape &Shape::operator+=(Shape &shape) {
    *this = (*this + shape);
    return *this;
}

Shape &Shape::operator-=(Shape &shape) {
    *this = (*this - shape);
    return *this;
}

// ПОЛИМОРФИЗМ НЕ, НЕ СЛЫШАЛ
Shape Shape::operator+(Shape &shape) {
    Shape target = BrokenLine(*this) + shape;
    if (target.countOfPoints > 1 && target.countOfPoints != target.lines.size())
        target.lines.emplace_back(Line(target.points.front(), target.points.back()));
    return target;
}

Shape Shape::operator-(Shape &shape) {
    Shape target = BrokenLine(*this) - shape;
    if (target.countOfPoints > 1 && target.countOfPoints != target.lines.size())
        target.lines.emplace_back(Line(target.points.front(), target.points.back()));
    return target;
}

bool Shape::haveIntersection(const Shape &shape) {
    for (int i = 0; i < shape.getSize(); ++i) {
        for (int j = 0; j < shape.getSize(); ++i) {
            if (i == j) continue;
            Line first = shape.lines[i];
            Line second = shape.lines[j];
            double det = first.getA() * second.getB() - second.getA() * second.getB();
            double dx = first.getC() * second.getB() - first.getB() * second.getC();
            double dy = first.getA() * second.getC() - first.getC() * second.getA();
            return (det == 0 && dx != 0 && dy != 0);
        }
    }
    return false;
}

bool Shape::isInPolygon(const Point &point, const double &eps) {
    bool c = false;
    for (int i = 0; i < this->lines.size(); ++i) {
        Line line = this->lines[i];
        c = line.isInLine(point);
        c = !c;
    }
    return c;
//    if (this->countOfPoints == 1) {
//        return this->points[0].getX() == point.getX() && this->points[0].getY() == point.getY();
//    } else if (this->countOfPoints == 2) {
//        return this->lines[0].isInLine(point);
//    } else {
//        double sum = 0;
//        Point lastPoint = this->points.back();
//        double lastX = lastPoint.getX() - point.getX();
//        double lastY = lastPoint.getY() - point.getY();
//
//        for (const Point &target : this->points) {
//            double currentX = target.getX() - point.getX();
//            double currentY = target.getY() - point.getY();
//
//            double del = lastX * currentY - lastY * currentX;
//            double xy = currentX * lastX + currentY * lastY;
//
//            sum += atan((lastX * lastX + lastY * lastY - xy) / del) +
//                   atan((currentX * currentX + currentY * currentY) / del);
//
//            lastX = currentX;
//            lastY = currentY;
//        }
//
//        return fabs(sum) > eps;
//    }
}
