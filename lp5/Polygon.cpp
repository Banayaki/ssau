#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"
#include "Polygon.h"

Polygon::Polygon() {
    this->countOfPoints = 0;
}

Polygon::Polygon(const Point &point) {
    this->countOfPoints = 1;
    points.push_back(point);
}

Polygon::Polygon(const unsigned long &count, const vector<Point> p, const vector<Line> l) {
    this->countOfPoints = count;
    this->points.assign(p.begin(), p.end());
    this->lines.assign(l.begin(), l.end());
}

Polygon::Polygon(const Polygon &polygon) {
    this->countOfPoints = polygon.countOfPoints;
    this->points.assign(polygon.points.begin(), polygon.points.end());
    this->lines.assign(polygon.lines.begin(), polygon.lines.end());
}

Polygon::Polygon(const vector<Point> &points) {
    this->countOfPoints = points.size();
    this->points.assign(points.begin(), points.end());

    for (int i = 0; i < countOfPoints - 1; ++i) {
        Point first = points[i];
        Point second = points[i + 1];
        lines.emplace_back(Line(first, second));
    }
}

Polygon::~Polygon() = default;

//Будем возвращать тут точку
const Point &Polygon::operator[](const unsigned long &n) {
    return points[n];
}

friend istream &Polygon::operator>>(istream &stream, Polygon &polygon) {
    polygon += Polygon::getPolygon(stream);
    return stream;
}

friend ifstream &Polygon::operator>>(ifstream &stream, Polygon &polygon) {
    polygon += Polygon::getPolygon(stream);
    return stream;
}

friend ostream &Polygon::operator<<(ostream &stream, Polygon &polygon) {
    stream << polygon.toString();
    return stream;
}

friend ofstream &Polygon::operator<<(ofstream &stream, Polygon &polygon) {
    stream << polygon.toString();
    return stream;
}

Polygon &Polygon::operator=(const Polygon &polygon) {
    if (this != &polygon) {
        Polygon p(polygon);
        p.swap(*this);
    }
    return *this;
}

const bool Polygon::operator>(const Polygon &polygon) {
    return this->countOfPoints > polygon.countOfPoints;
}

const bool Polygon::operator<(const Polygon &polygon) {
    return this->countOfPoints < polygon.countOfPoints;
}

const bool Polygon::operator>=(const Polygon &polygon) {
    return this->countOfPoints >= polygon.countOfPoints;
}

const bool Polygon::operator<=(const Polygon &polygon) {
    return this->countOfPoints <= polygon.countOfPoints;
}

const bool Polygon::operator==(const Polygon &polygon) {
    return this->countOfPoints == polygon.countOfPoints;
}

const bool Polygon::operator!=(const Polygon &polygon) {
    return this->countOfPoints != polygon.countOfPoints;
}

Polygon &Polygon::operator+=(const Polygon &polygon) {
    this = *this + polygon;
    return *this;
}

Polygon &Polygon::operator-=(const Polygon &polygon) {
    this = *this - polygon;
    return *this;
}

Polygon &Polygon::operator+(const Polygon &polygon) {
    vector<Point> *check = &this->points;
    vector<Point> *points = &polygon.points;
    for (Point point : *points) {
        if (find(check->begin(), check->end(), point) == check->end()) {
            this->lines.emplace_back(Line(check->front(), point));
            ++this->countOfPoints;
            check->push_back(point);
        }
    }
    //delete check?
    return *this;
}

Polygon &Polygon::operator-(const Polygon &polygon) {
    vector<Point> *check = &this->points;
    vector<Line> *lines = &this->lines;
    vector<Point> *points = &polygon.points;
    for (Point point : *points) {
        long pos = find(*check, point);
        if (pos != -1) {
            check->erase(check->begin() + pos);
            lines->erase(lines->begin() + pos);
            lines->at(pos - 1).refresh(check->at(pos - 1), check->at(pos));
            --this->countOfPoints;
        }
    }
    //delete check?
    return *this;
}

void Polygon::swap(Polygon::Polygon &polygon) {
    this->countOfPoints = polygon.countOfPoints;
    std::swap(this->points, polygon.points);
    std::swap(this->lines, polygon.lines);
}

string Polygon::toString() {
    stringstream ss;
    ss << "It's a Polygon \n" << "Count of point: " << countOfPoints << '\n';
    ss << "Points: \n";
    int i = 1;
    for (Point point : points) {
        ss << i << " : (" << point.getX() << ";" << point.getY() << ")\n";
    }
    ss << "Equations of lines: (Ax + By + C = 0)";
    i = 1;
    for (Line line : lines) {
        ss << i << " -> " << i + 1 << " " << line.toString() << '\n';
    }
    return ss.str();
}

unsigned long Polygon::getSize() {
    return countOfPoints;
}

bool Polygon::belongsToPolygon(const Point &point) {
    // (y1 - y2)x + (x2 - x1)y + (x1y2 - y2x1) = 0
}

bool Polygon::isIntersection(const Polygon &polygon) {

}


static Polygon Polygon::getPolygon(ifstream &stream) {
    double x, y;
    stream >> x >> y;
    while (!stream) {
        cout << INCORRECT_INPUT << endl;
        stream >> x >> y;
    }
    Polygon p(Point(x, y));
    return p;
}


template <typename T>
long find(const vector<T> &vec, const T &val) {
    long size = vec.size();
    for (int i = 0; i < size; ++i) {
        if (vec[i] == val) {
            return i;
        }
    }
    return -1;
}
