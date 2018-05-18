#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"

// Вариант 2
class Point;
class Polygon;

class ExecutorForLP5 : public Executor {
public:
    template<typename T>
    Polygon getPolygon(T &stream) {
        double x, y;
        cout << WAITING_PAIR_XY << endl;
        stream >> x >> y;
        while (!stream || executor.seek(stream) != EOL) {
            cout << INCORRECT_INPUT << endl;
            executor.clearInputStream(stream);
            stream >> x >> y;
        }
        Polygon p(Point(x,y));
        return p;
    }
};

ExecutorForLP5 executor;

class Point {
private:
    double x;
    double y;
    bool isInPolygon;
public:
    Point(const double &x, const double &y) {
        this->x = x;
        this->y = y;
        isInPolygon = false;
    }

    Point(const double &x, const double &y, const bool &isInPolygon) {
        this->x = x;
        this->y = y;
        this->isInPolygon = isInPolygon;
    }

    ~Point() = default;

    double getX() const {
        return x;
    }

    double getY() const {
        return y;
    }

    bool isIsInPolygon() const {
        return isInPolygon;
    }

    void setIsInPolygon(bool isInPolygon) {
        this->isInPolygon = isInPolygon;
    }
};

class Line {
private:
    //Ax + By + C = 0
    double A;
    double B;
    double C;
public:
    Line(const int &A, const int &B, const int &C) {
        this->A = A;
        this->B = B;
        this->C = C;
    }

    Line(const Point &first, const Point &second) {
        double x1 = first.getX();
        double x2 = second.getX();
        double y1 = first.getY();
        double y2 = second.getY();
        this->A = y1 - y2;
        this->B = x2 - x1;
        this->C = (x1 * y2) - (x2 * y1);
    }

    bool isInLine(const Point &point) {
        return (A * point.getX() + B * point.getY() + C) == 0;
    }

    string toString() {
        return to_string(A) + "x " + to_string(B) + "y " + to_string(C) + " = 0";
    }
};

class Polygon {
private:
    unsigned long countOfPoints;
    vector<Point> points;
    vector<Line> lines;

public:
    Polygon() {
        this->countOfPoints = 0;
    }

    Polygon(const Point &point) {
        this->countOfPoints = 1;
        points.push_back(point);
    }

    Polygon(const unsigned long &count, const vector<Point> p, const vector<Line> l) {
        this->countOfPoints = count;
        this->points.assign(p.begin(), p.end());
        this->lines.assign(l.begin(), l.end());
    }

    Polygon(const Polygon &polygon) {
        this->countOfPoints = polygon.countOfPoints;
        this->points.assign(polygon.points.begin(), polygon.points.end());
        this->lines.assign(polygon.lines.begin(), polygon.lines.end());

    }

    Polygon(const vector<Point> &points) {
        this->countOfPoints = points.size();
        this->points.assign(points.begin(), points.end());

        for (int i = 0; i < countOfPoints - 1; ++i) {
            Point first = points[i];
            Point second = points[i + 1];
            lines.emplace_back(Line(first, second));
        }
    }

    ~Polygon() = default;

    //Будем возвращать тут точку
    const Point &operator[](const unsigned long &n) {
        return points[n];
    }

    istream &operator>>(istream &stream, Polygon &polygon) {
        polygon += executor.getPolygon(stream);
        return stream;
    }

    ifstream &operator>>(ifstream &stream, Polygon &polygon) {
        polygon += executor.getPolygon(stream);
        return stream;
    }

    ostream &operator<<(ostream &stream, Polygon &polygon) {
        stream << polygon.toString();
        return stream;
    }

    ofstream &operator<<(ofstream &stream, Polygon &polygon) {
        stream << polygon.toString();
        return stream;
    }

    Polygon &operator=(const Polygon &polygon) {
        if (this != &polygon) {
            Polygon p(polygon);
            p.swap(*this);
        }
        return *this;
    }

    const bool operator>(const Polygon &polygon) {
        return this->countOfPoints > polygon.countOfPoints;
    }

    const bool operator<(const Polygon &polygon) {
        return this->countOfPoints < polygon.countOfPoints;
    }

    const bool operator>=(const Polygon &polygon) {
        return this->countOfPoints >= polygon.countOfPoints;
    }

    const bool operator<=(const Polygon &polygon) {
        return this->countOfPoints <= polygon.countOfPoints;
    }

    const bool operator==(const Polygon &polygon) {
        return this->countOfPoints == polygon.countOfPoints;
    }

    const bool operator!=(const Polygon &polygon) {
        return this->countOfPoints != polygon.countOfPoints;
    }

    Polygon &operator+=(Polygon &polygon) {
        return this->operator=(this->operator+(*this, polygon));
    }

    const Polygon operator-=(const Point &point) {

    }

    const Polygon operator/=(const Point &point) {

    }

    const Polygon operator*=(const Point &point) {

    }

    Polygon& operator+(const Polygon &first, const Polygon &second) {
        unsigned long countOfPoints = second.countOfPoints + first.countOfPoints;
        vector<Point> points(first.points, second.points);
        vector<Line> lines(first.lines, second.lines);
        Polygon p(countOfPoints, points, lines);
        return p;
    }

    const Polygon operator-(const Point &point) {

    }

    const Polygon operator/(const Point &point) {

    }

    const Polygon operator*(const Point &point) {

    }

    const Polygon operator++(const Point &point) {

    }

    const Polygon operator++(const Point &point, int) {

    }

    const Polygon operator--(const Point &point) {

    }

    const Polygon operator--(const Point &point, int) {

    }

    void swap(Polygon &polygon) {
        this->countOfPoints = polygon.countOfPoints;
        std::swap(this->points, polygon.points);
        std::swap(this->lines, polygon.lines);
    }

    string toString() {
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

    unsigned int getSize() {
        return countOfPoints;
    }

    void addPoint() {

    }

    void deletePoint() {

    }

    bool belongsToPolygon(const Point &point) {
        // (y1 - y2)x + (x2 - x1)y + (x1y2 - y2x1) = 0
    }

    bool isIntersection(const Polygon &polygon) {

    }
};


int main() {
    std::cout << "Hello, World!" << std::endl;
    return 0;
}