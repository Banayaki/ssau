#ifndef MYCLASSES_H
#define MYCLASSES_H

#include <queue>
#include <stack>
#include <fstream>
#include <iostream>
#include <sstream>
#include <algorithm>
#include <vector>
#include <stdlib.h>
#include <iomanip>
#include <time.h>
#include <cmath>
#include <string>

using namespace std;

const string WAITING_PAIR_XY = "Waiting for pair (x,y), where x, y is coordinates of a point";
const string ERROR = "Error";
const string FILE_FORMAT = ".txt";
const string OUT_OF_BOUNDS = "index: out of bounds.";
const string INCORRECT_INPUT = "Incorrect input, try again.";
const string ERROR_IFC = "ERROR. Incorrect file content.";
const string DOUBLE_OVERFLOW = "Overflow double";
const string INTEGER_OVERFLOW = "Overflow double";
const string OUTPUT_FILE = R"(C:\Users\Banayaki\Desktop\tests\output.txt)";
const char EOS = 0;
const char SPACE = ' ';
const char TAB = '\t';
const char CR = '\r';
const char EOL = '\n';
const char YES = 'y';
const char NO = 'n';

#include "../helpers/Executor.cpp"
#include "../helpers/MyVector.cpp"
#include "Root.h"
#include "PolynomResolver.h"
#include "Polynom.h"

#endif //MYCLASSES_H
