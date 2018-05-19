#ifndef POINT_H
#define POINT_H

class Point {
private:
    double x;
    double y;
    bool isInPolygon;
public:
    Point(const double &x, const double &y);

    Point(const double &x, const double &y, const bool &isInPolygon);

    ~Point();

    double getX() const;

    double getY() const;

    bool isIsInPolygon() const;

    void setIsInPolygon(bool isInPolygon);
};


#endif