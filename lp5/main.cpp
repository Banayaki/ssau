#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"
#include "Point.h"
#include "Line.h"
#include "Polygon.h"
// Вариант 2


Executor executor;


int main() {
    vector<Point> p1, p2;
    double x, y;
    for (int i = 0; i < 3; ++i) {
        cin >> x >> y;
        p1.emplace_back(Point(x, y));
    }
    Polygon polygon1(p1);
    for (int i = 0; i < 3; ++i) {
        cin >> x >> y;
        p2.emplace_back(Point(x, y));
    }
    Polygon polygon2(p2);
    cout << polygon1 << polygon2;
    polygon1 += polygon2;
    cout << polygon1 << polygon2;
    polygon2 = polygon1;
    cout << polygon1 << polygon2;
}