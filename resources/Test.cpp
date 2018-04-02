double random(double left, double right) {
    return (double) (rand()) / RAND_MAX * (right - left) + left;
}

void createFiles() {
    srand((unsigned int) time(0));
    stringstream ss;
    ofstream oneThousandElements(R"(C:\Users\Banayaki\Desktop\tests\1_000_Elements.txt)");
    ofstream fiveThousandElements(R"(C:\Users\Banayaki\Desktop\tests\5_000_Elements.txt)");
    ofstream tenThousandElements(R"(C:\Users\Banayaki\Desktop\tests\10_000_Elements.txt)");
    for (int i = 0; i < 1000; ++i) {
        ss << random(0, 10000) << EOL;
    }
    oneThousandElements << ss.str();
    ss.clear();
    for (int i = 0; i < 4000; ++i) {
        ss << random(0, 10000) << EOL;
    }
    fiveThousandElements << ss.str();
    for (int i = 0; i < 5000; ++i) {
        ss << random(0, 10000) << EOL;
    }
    tenThousandElements << ss.str();
}

