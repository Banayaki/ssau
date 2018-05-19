#ifndef POLYGON_H
#define POLYGON_H

#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"
#include "Point.h"
#include "Line.h"

class Polygon {
private:
    unsigned long countOfPoints;
    vector<Point> points;
    vector<Line> lines;
public:
    Polygon();
    Polygon(const Point &point);
    Polygon(const unsigned long &count, const vector<Point> p, const vector<Line> l);
    Polygon(const Polygon &polygon);
    Polygon(const vector<Point> &points);
    ~Polygon();

    const Point &operator[](const unsigned long &n);
    friend istream &operator>>(istream &stream, Polygon &polygon);
    friend ifstream &operator>>(ifstream &stream, Polygon &polygon);
    friend ostream &operator<<(ostream &stream, Polygon &polygon);
    friend ofstream &operator<<(ofstream &stream, Polygon &polygon);

    const bool operator>(const Polygon &polygon);
    const bool operator<(const Polygon &polygon);
    const bool operator>=(const Polygon &polygon);
    const bool operator<=(const Polygon &polygon);
    const bool operator==(const Polygon &polygon);
    const bool operator!=(const Polygon &polygon);

    Polygon &operator=(const Polygon &polygon);

    Polygon &operator+=(const Polygon &polygon);
    Polygon &operator-=(const Polygon &polygon);

    Polygon &operator+(const Polygon &polygon);
    Polygon &operator-(const Polygon &polygon);

    void swap(Polygon &polygon);
    string toString();
    unsigned long getSize();
    bool belongsToPolygon(const Point &point);
    bool isIntersection(const Polygon &polygon);
    static Polygon getPolygon(ifstream &stream);
};

template <typename T>
long find(const vector<T> &vec, const T &val);



#endif