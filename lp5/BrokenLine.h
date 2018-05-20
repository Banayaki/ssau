#ifndef POLYGON_H
#define POLYGON_H

#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"
#include "Point.h"
#include "Line.h"

class BrokenLine {
protected:
    unsigned long countOfPoints;
    vector<Point> points;
    vector<Line> lines;
public:
    BrokenLine();

    BrokenLine(const Point &point);

    BrokenLine(const unsigned long &count, const vector<Point> p, const vector<Line> l);

    BrokenLine(const BrokenLine &polygon);

    BrokenLine(const vector<Point> &points);

    virtual ~BrokenLine();

    virtual const Point &operator[](const unsigned long &n);

    virtual const bool operator>(const BrokenLine &polygon);

    virtual const bool operator<(const BrokenLine &polygon);

    virtual const bool operator>=(const BrokenLine &polygon);

    virtual const bool operator<=(const BrokenLine &polygon);

    virtual const bool operator==(const BrokenLine &polygon);

    virtual const bool operator!=(const BrokenLine &polygon);

    BrokenLine &operator=(const BrokenLine &polygon);

    virtual BrokenLine &operator+=(const BrokenLine &polygon);

    virtual BrokenLine &operator-=(const BrokenLine &polygon);

    virtual BrokenLine &operator+(const BrokenLine &polygon);

    virtual BrokenLine &operator-(const BrokenLine &polygon);

    virtual BrokenLine &operator-(const Point &point);

    virtual string toString();

    static void swap(BrokenLine &first, BrokenLine &second);

    virtual unsigned long getSize() const;
};


BrokenLine getBrokenLine(ifstream &stream);

ifstream &operator>>(ifstream &stream, BrokenLine &polygon);

ostream &operator<<(ostream &stream, BrokenLine &polygon);

ofstream &operator<<(ofstream &stream, BrokenLine &polygon);

long find(const vector<Point> &vec, const Point &val);

#endif