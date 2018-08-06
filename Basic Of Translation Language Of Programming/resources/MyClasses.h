#ifndef MYCLASSES_H
#define MYCLASSES_H

#include <queue>
#include <stack>
#include <fstream>
#include <iostream>
#include <sstream>
#include <Windows.h>
#include <algorithm>
#include <vector>
#include <stdlib.h>
#include <iomanip>
#include <time.h>
#include <cmath>

using namespace std;

const string WAITING_PAIR_XY = "Waiting for pair (x,y), where x, y is coordinates of a point";
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
const char BIG_EN_L_BEGIN = 65;
const char BIG_EN_L_END = 90;
const char SMALL_EN_L_BEGIN = 97;
const char SMALL_EN_L_END = 122;
const char NUMBERS_BEGIN = 48;
const char NUMBERS_END = 57;
const int CYRILLIC_BEGIN = 1040;
const int CYRILLIC_END = 1103;
const int BUFFERED_SIZE = 256;

#include "Executor.cpp"
//#include "MyVector.cpp"
//#include "LexicalAnalyzer.cpp"
//#include "WFStreamExecutor.cpp"
//#include "Test.cpp"
//#include "LinkedList.cpp"

#endif //SSAU_MYCLASSES_H
