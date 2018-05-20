#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"
#include "Point.h"
#include "Line.h"
#include "BrokenLine.h"
#include "Shape.h"
// Вариант 2




int main() {
    Executor executor;
    executor.getFout().open(OUTPUT_FILE);
    while (!executor.getFin().is_open()) {
        executor.openFile();
    }

    vector<Point> p1, p2;
    double x, y;
    for (int i = 0; i < 3; ++i) {
        cin >> x >> y;
        p1.emplace_back(Point(x, y));
    }
    Shape polygon1(p1);
    executor.getFin() >> polygon1;
    for (int i = 0; i < 3; ++i) {
        cin >> x >> y;
        p2.emplace_back(Point(x, y));
    }
    Shape polygon2(p2);
    cout << polygon1 << polygon2;
    polygon1 += polygon2;
    cout << polygon1 << polygon2;
    polygon1 -= polygon2;
    cout << polygon1 << polygon2;
    polygon2 = polygon1;
    cout << polygon1 << polygon2;
}
