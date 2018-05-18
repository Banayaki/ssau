#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"
// Вариант 2

class ExecutorForLP5 : public Executor {
    template <typename T>

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

    Polygon(const Polygon &polygon) {
        this->countOfPoints = polygon.countOfPoints;
        this->points = polygon.points;
        this->lines = polygon.lines;

    }

    Polygon(const vector<Point> &points) {
        this->countOfPoints = points.size();
        this->points = points;

        for (int i = 0; i < countOfPoints - 1; ++i) {
            Point first = points[i];
            Point second = points[i + 1];
            lines.emplace_back(Line(first, second));
        }
    }

    ~Polygon() = default;

    //Будем возвращать тут точку
    const Point& operator[](const unsigned long &n) {
        return points[n];
    }

    istream& operator>>(istream &stream, Polygon &polygon) {
        double x,y;
        cout << WAITING_PAIR_XY << endl;
        cin >> x >> y;
        while (!cin || executor.seek(cin) != EOL) {
            cout << INCORRECT_INPUT << endl;
            executor.clearInputStream(cin);
            cin >> x >> y;
        }
        return stream;
    }

    ifstream& operator>>(ifstream &stream, Polygon &polygon) {
        double x,y;
        cout << WAITING_PAIR_XY << endl;
        cin >> x >> y;
        while (!cin || executor.seek(cin) != EOL) {
            cout << INCORRECT_INPUT << endl;
            executor.clearInputStream(cin);
            cin >> x >> y;
        }
        return stream;
    }

    ostream& operator<<(ostream &stream, Polygon &polygon) {

    }

    ofstream& operator<<(ofstream &stream, Polygon &polygon) {

    }

    Polygon &operator=(const Polygon &polygon) {
        if (this == &polygon) {
            return *this;
        }
        return Polygon(polygon);
    }

    const Polygon operator>(const Point &point) {

    }

    const Polygon operator<(const Point &point) {

    }

    const Polygon operator<=(const Point &point) {

    }

    const Polygon operator>=(const Point &point) {

    }

    const Polygon operator==(const Point &point) {

    }

    const Polygon operator!=(const Point &point) {

    }

    //TODO friend const?
    const Polygon operator+=(const Point &point) {

    }

    const Polygon operator-=(const Point &point) {

    }

    const Polygon operator/=(const Point &point) {

    }

    const Polygon operator*=(const Point &point) {

    }

    const Polygon operator+(const Point &point) {

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