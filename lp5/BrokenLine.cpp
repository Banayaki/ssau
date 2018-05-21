#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"
#include <typeinfo>
#include "BrokenLine.h"

BrokenLine::BrokenLine() {

}

BrokenLine::BrokenLine(const Point &point) {
    this->countOfPoints = 1;
    points.push_back(point);
}

BrokenLine::BrokenLine(const BrokenLine &polygon) {
    this->countOfPoints = polygon.countOfPoints;
    this->points.assign(polygon.points.begin(), polygon.points.end());
    if (polygon.countOfPoints > 1) {
        this->lines.assign(polygon.lines.begin(), polygon.lines.end());
    }
}

BrokenLine::BrokenLine(const vector<Point> &points) {
    this->countOfPoints = points.size();
    this->points.assign(points.begin(), points.end());

    for (int i = 0; i < countOfPoints - 1; ++i) {
        Point first = points[i];
        Point second = points[i + 1];
        lines.emplace_back(Line(first, second));
    }
}

BrokenLine::~BrokenLine() = default;

//Будем возвращать тут точку
const Point &BrokenLine::operator[](const unsigned long &n) {
    return points[n];
}

ifstream &operator>>(ifstream &stream, BrokenLine &polygon) {
    polygon += getBrokenLine(stream);
    return stream;
}

ostream &operator<<(ostream &stream, BrokenLine &polygon) {
    stream << polygon.toString();
    return stream;
}

ofstream &operator<<(ofstream &stream, BrokenLine &polygon) {
    stream << polygon.toString();
    return stream;
}

BrokenLine &BrokenLine::operator=(const BrokenLine &polygon) {
    if (this != &polygon) {
        BrokenLine p(polygon);
        BrokenLine::swap(*this, p);
    }
    return *this;
}

const bool BrokenLine::operator>(const BrokenLine &polygon) {
    return this->countOfPoints > polygon.countOfPoints;
}

const bool BrokenLine::operator<(const BrokenLine &polygon) {
    return this->countOfPoints < polygon.countOfPoints;
}

const bool BrokenLine::operator>=(const BrokenLine &polygon) {
    return this->countOfPoints >= polygon.countOfPoints;
}

const bool BrokenLine::operator<=(const BrokenLine &polygon) {
    return this->countOfPoints <= polygon.countOfPoints;
}

const bool BrokenLine::operator==(const BrokenLine &polygon) {
    return this->countOfPoints == polygon.countOfPoints;
}

const bool BrokenLine::operator!=(const BrokenLine &polygon) {
    return this->countOfPoints != polygon.countOfPoints;
}

BrokenLine &BrokenLine::operator+=(const BrokenLine &polygon) {
    *this = (*this + polygon);
    return *this;
}

BrokenLine &BrokenLine::operator-=(const BrokenLine &polygon) {
    *this = (*this - polygon);
    return *this;
}

BrokenLine BrokenLine::operator+(const BrokenLine &polygon) {
    BrokenLine brokenLine(*this);
    const vector<Point> *points = &polygon.points;
    for (const Point &point : *points) {
        if (find(brokenLine.points, point) == -1) {
            if (brokenLine.countOfPoints > 0) {
                brokenLine.lines.emplace_back(Line(brokenLine.points.back(), point));
            }
            ++brokenLine.countOfPoints;
            brokenLine.points.push_back(point);
        }
    }
    return brokenLine;
}

BrokenLine BrokenLine::operator-(const BrokenLine &polygon) {
    BrokenLine brokenLine(*this);
    vector<Point> *points = &brokenLine.points;
    vector<Line> *lines = &brokenLine.lines;
    const vector<Point> *check = &polygon.points;
    if (this->countOfPoints == 0) {
        return brokenLine;
    } else if (this->countOfPoints >= polygon.countOfPoints) {
        for (const Point &point : *check) {
            long pos = find(*points, point);
            if (pos != -1) {
                points->erase(check->begin() + pos);
                lines->erase(lines->begin() + pos);
                brokenLine.countOfPoints -= 1;
                if (countOfPoints > 1 && pos < countOfPoints && pos > 0) {
                    if (pos == lines->size())
                        lines->at(pos - 1).refresh(points->at(pos - 1), points->at(0));
                    else
                        lines->at(pos - 1).refresh(points->at(pos - 1), points->at(pos));
                }
            }
        }
    } else {
        for (const Point &point : *points) {
            long pos = find(*check, point);
            long index = find(*points, point);
            if (pos != -1) {
                points->erase(points->begin() + index);
                lines->erase(lines->begin() + index);
                brokenLine.countOfPoints -= 1;
                if (countOfPoints > 1 && index < countOfPoints && index > 0) {
                    if (index == lines->size())
                        lines->at(index - 1).refresh(points->at(index - 1), points->at(0));
                    lines->at(index - 1).refresh(points->at(index - 1), points->at(index));
                }
            }
        }
    }
    return brokenLine;
}

BrokenLine &BrokenLine::operator-(const Point &point) {
    BrokenLine brokenLine = *this - BrokenLine(point);
    return brokenLine;
}

string BrokenLine::toString() {
    stringstream ss;
    ss << "Class: " << typeid(*this).name() << "\nCount of point: " << countOfPoints << '\n';
    ss << "Points: \n";
    int i = 1;
    for (const Point &point : points) {
        ss << i << " : (" << point.getX() << ";" << point.getY() << ")\n";
        ++i;
    }
    ss << "Equations of lines: (Ax + By + C = 0) \n";
    i = 1;
    for (Line line : lines) {
        ss << i << " -> " << i + 1 << " " << line.toString() << '\n';
        ++i;
    }
    string s = ss.str();
    return s;
}

unsigned long BrokenLine::getSize() const {
    return countOfPoints;
}

BrokenLine getBrokenLine(ifstream &stream) {
    double x, y;
    stream >> x >> y;
    while (!stream) {
        cout << INCORRECT_INPUT << endl;
        stream >> x >> y;
    }
    BrokenLine p(Point(x, y));
    return p;
}

long find(const vector<Point> &vec, const Point &val) {
    long size = vec.size();
    for (int i = 0; i < size; ++i) {
        if (vec[i].getX() == val.getX() && vec[i].getY() == val.getY()) {
            return i;
        }
    }
    return -1;
}

void BrokenLine::swap(BrokenLine &first, BrokenLine &second) {
    auto tmp = first.countOfPoints;
    first.countOfPoints = second.countOfPoints;
    second.countOfPoints = tmp;
    std::swap(first.points, second.points);
    std::swap(first.lines, second.lines);
}
