#ifndef POINT_H
#define POINT_H

class Point {
private:
    double x;
    double y;
public:
    Point(const double &x, const double &y);

    ~Point();

    double getX() const;

    double getY() const;

};


#endif