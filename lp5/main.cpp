#include "C:\Users\Banayaki\CLionProjects\ssau\resources\MyClasses.h"
#include "Point.h"
#include "BrokenLine.h"
#include "Shape.h"
// Вариант 2

class Main {
private:
    Executor executor;
public:
    Main() {
        executor.getFout().open(OUTPUT_FILE);
        while (!executor.getFin().is_open()) {
            executor.openFile("input.txt");
        }
    }

    void constructionTest() {
        BrokenLine brokenLine1;
        BrokenLine brokenLine2(Point(20, 12));
        vector<Point> p = {Point(1, 1), Point(2, 2), Point(3, 3)};
        BrokenLine brokenLine3(p);
        BrokenLine brokenLine4(brokenLine2);

        Shape shape1;
        Shape shape2(Point(9, 3));
        Shape shape3(brokenLine4);
        Shape shape4(shape2);
        Shape shape5(p);

        executor.printAll(brokenLine1.toString());
        executor.printAll(brokenLine2.toString());
        executor.printAll(brokenLine3.toString());
        executor.printAll(brokenLine4.toString());

        executor.printAll(shape1.toString());
        executor.printAll(shape2.toString());
        executor.printAll(shape3.toString());
        executor.printAll(shape4.toString());
        executor.printAll(shape5.toString());
    }

    void streamsTest() {
        BrokenLine brokenLine;
        int count = 10;
        executor.printAll("Read " + to_string(count) + " points from file input.txt");
        for (int i = 0; i < count; ++i) {
            executor.getFin() >> brokenLine;
        }
        executor.getFout() << brokenLine;

        Shape shape;
        executor.printAll("Read " + to_string(count) + " points from file input.txt");
        for (int i = 0; i < count; ++i) {
            executor.getFin() >> shape;
        }
        executor.getFout() << shape;

        Shape copy(shape);
        executor.getFin() >> shape;

        executor.getFout() << copy;
        executor.getFout() << shape;
    }

    void compareTest() {
        BrokenLine brokenLine;
        Shape shape;
        int count;
        cout << "Enter count of points(brokenLine): " << endl;
        cin >> count;
        executor.printAll("Read " + to_string(count) + " points from file input.txt");
        for (int i = 0; i < count; ++i) {
            executor.getFin() >> brokenLine;
        }
        cout << "Enter count of points(shape): " << endl;
        cin >> count;
        executor.printAll("Read " + to_string(count) + " points from file input.txt");
        for (int i = 0; i < count; ++i) {
            executor.getFin() >> shape;
        }

        if (brokenLine > shape)
            executor.printAll("BrokenLine have more points than shape");
        else if (brokenLine < shape)
            executor.printAll("Shape have more points than BrokenLine");
        else if (brokenLine == shape)
            executor.printAll("Shape have as many points as BrokenLine");
    }

    void plusMinusTest() {
        BrokenLine brokenLine2(Point(20, 12));
        vector<Point> p = {Point(1, 1), Point(2, 2), Point(3, 3)};
        BrokenLine brokenLine3(p);
        BrokenLine brokenLine1(Point(2, 2));

        brokenLine2 += brokenLine3;
        executor.printAll(brokenLine2.toString());

        brokenLine2 -= brokenLine1;
        executor.printAll(brokenLine2.toString());

        executor.printAll("Now - SHAPES!!!");
        Shape shape2(Point(20, 12));
        Shape shape3(p);
        p.erase(p.end());
        Shape shape1(p);

        shape2 += shape3;
        executor.printAll(shape2.toString());

        shape2 -= shape1;
        executor.printAll(shape2.toString());
    }

    void inSpace() {
        vector<Point> p = {Point(1, 1), Point(4, 1), Point(1, 4), Point(4, 4)};
        vector<Point> p2 = {Point(3, 3), Point(3, 5), Point(5, 3), Point(5, 5)};
        vector<Point> p3 = {Point(7, 7), Point(9, 9), Point(6, 5)};
        Shape shape(p);
        Shape shape1(p2);
        Shape shape2(p3);
        Point first(3, 2);
        Point second(0, 0);

        executor.printAll(to_string(shape.isInPolygon(first, executor.getEps())));
        executor.printAll(to_string(shape.isInPolygon(second, executor.getEps())));

        executor.printAll(to_string(shape.haveIntersection(shape1)));
        executor.printAll(to_string(shape.haveIntersection(shape2)));
    }
};

int main() {
    Main tests;
//    tests.constructionTest();
//    tests.streamsTest();
//    tests.compareTest();
//    tests.plusMinusTest();
    tests.inSpace();
}
