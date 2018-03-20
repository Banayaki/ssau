#ifndef SSAU_MYCLASSES_H
#define SSAU_MYCLASSES_H

#include <fstream>
#include <iostream>
#include <sstream>
#include <Windows.h>
#include <vector>

using namespace std;

const string FILE_FORMAT = ".txt"; // NOLINT
const string INCORRECT_INPUT = "Incorrect input, try again."; //NOLINT
const string ERROR_IFC = "ERROR. Incorrect file content."; //NOLINT
const string DOUBLE_OVERFLOW = "Overflow double"; //NOLINT
const string INTEGER_OVERFLOW = "Overflow double";
const string OUTPUT_FILE = R"(C:\Users\Banayaki\Desktop\tests\output.txt)";
const char EOS = 0;
const char SPACE = ' ';
const char TAB = '\t';
const char CR = '\r';
const char EOL = '\n';
const char YES = 'y';
const char NO = 'n';
const char NUMBERS_BEGIN = 48;
const char NUMBERS_END = 57;
const int CYRILLIC_BEGIN = 1040;
const int CYRILLIC_END = 1103;
const int BUFFERED_SIZE = 256;

#include "MyVector.cpp"
#include "Executor.cpp"
#include "LexicalAnalyzer.cpp"

#endif //SSAU_MYCLASSES_H
