#include "Shape.h"

Shape::Shape() : BrokenLine() {
}

Shape::Shape(const Shape &shape) : BrokenLine(shape) {
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
}

Shape::Shape(const Point &point) : BrokenLine(point) {}

Shape::Shape(const unsigned long &count, const vector<Point> p, const vector<Line> l) : BrokenLine(count, p, l) {
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
}

Shape::Shape(const BrokenLine &polygon) : BrokenLine(polygon) {
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
}

Shape::Shape(const vector<Point> &points) : BrokenLine(points) {
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
}

Shape::~Shape() = default;

const Point &Shape::operator[](const unsigned long &n) {
    return BrokenLine::operator[](n);
}

ifstream &operator>>(ifstream &stream, Shape &shape) {
    Shape target(getBrokenLine(stream));
    shape += target;
    return stream;
}

ostream &operator<<(ostream &stream, Shape &shape) {
    stream << shape.toString();
    return stream;
}

ofstream &operator<<(ofstream &stream, Shape &shape) {
    stream << shape.toString();
    return stream;
}
//
//const bool Shape::operator>(const Shape &shape) {
//    return this->perimeter > shape.perimeter;
//}
//
//const bool Shape::operator<(const Shape &shape) {
//    return this->perimeter < shape.perimeter;
//}
//
//const bool Shape::operator>=(const Shape &shape) {
//    return this->perimeter >= shape.perimeter;
//}
//
//const bool Shape::operator<=(const Shape &shape) {
//    return this->perimeter <= shape.perimeter;
//}
//
//const bool Shape::operator==(const Shape &shape) {
//    return this->perimeter == shape.perimeter;
//}
//
//const bool Shape::operator!=(const Shape &shape) {
//    return BrokenLine::operator!=(shape);
//}

Shape &Shape::operator=(const Shape &shape) {
    if (this != &shape) {
        Shape shp(shape);
        Shape::swap(*this, shp);
    }
    return *this;
}

Shape &Shape::operator+=(Shape &shape) {
    *this = *this + shape;
    return *this;
}

Shape &Shape::operator-=(Shape &shape) {
    *this = *this - shape;
    return *this;
}

Shape &Shape::operator+(Shape &shape) {
    this->lines.erase(lines.end());
    BrokenLine *brokenLine;
    brokenLine = &*this;
    brokenLine->operator+(*dynamic_cast<BrokenLine *>(&shape));
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
    return *this;
}

Shape &Shape::operator-(Shape &shape) {
    this->lines.erase(lines.end());
    BrokenLine *brokenLine;
    brokenLine = &*this;
    brokenLine->operator-(*dynamic_cast<BrokenLine *>(&shape));
    if (this->countOfPoints > 1)
        this->lines.emplace_back(Line(this->points.front(), this->points.back()));
    return *this;
}

void Shape::swap(Shape &first, Shape &second) {
    BrokenLine::swap(
            *dynamic_cast<BrokenLine *>(&first),
            *dynamic_cast<BrokenLine *>(&second)
    );
}

bool habeInsertion() {

}
