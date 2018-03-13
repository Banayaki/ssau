#include <Windows.h>


template<class T>
class MyVector {
private:
    T *vector;
    int vectorSize;
public:
    MyVector() {
        this->vectorSize = 0;
        this->vector = new T[this->vectorSize];
    }

    ~MyVector() = default;

    T &operator[](int i) const {
        return this->vector[i];
    }

    int size() {
        return this->vectorSize;
    }

    void push_back(T number) {
        T *result = new T[++this->vectorSize];
        for (int i = 0; i < this->vectorSize; i++) {
            if (i != this->vectorSize - 1) {
                result[i] = this->vector[i];
            } else {
                result[i] = number;
            }
        }
        delete[] vector;
        vector = result;
    }

    void clear() {
        if (vector != nullptr) {
            ZeroMemory(this->vector, this->vectorSize * sizeof(T));
            this->vectorSize = 0;
            this->vector = new T[this->vectorSize];
        }
    }

    bool empty() {
        return this->size() == 0;
    }

};