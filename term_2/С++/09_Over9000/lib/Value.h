#pragma once

#include <iostream>


class Value {
public:
    Value(unsigned trueValue = 0, unsigned fakeValue = 0);

    unsigned getTrueValue() const;
    unsigned getFakeValue() const;

    void setTrueValue(unsigned value);
    void setFakeValue(unsigned value);

    operator unsigned () const;

    friend Value operator+ (const Value& a, const Value& b);
    friend Value operator- (const Value& a, const Value& b);
    friend Value operator* (const Value& a, const Value& b);
    friend Value operator/ (const Value& a, const Value& b);
    friend Value operator% (const Value& a, const Value& b);

    friend Value operator^ (const Value& a, const Value& b);
    friend Value operator| (const Value& a, const Value& b);
    friend Value operator& (const Value& a, const Value& b);
    friend Value operator~ (const Value& a);

    friend Value operator== (const Value& a, const Value& b);
    friend Value operator!= (const Value& a, const Value& b);
    friend Value operator> (const Value& a, const Value& b);
    friend Value operator< (const Value& a, const Value& b);
    friend Value operator>= (const Value& a, const Value& b);
    friend Value operator<= (const Value& a, const Value& b);

    friend Value operator|| (const Value& a, const Value& b);
    friend Value operator&& (const Value& a, const Value& b);
    friend Value operator! (const Value& a);

    Value& operator<<= (const Value& a);
    Value& operator>>= (const Value& a);
    Value& operator+= (const Value& a);
    Value& operator-= (const Value& a);
    Value& operator*= (const Value& a);
    Value& operator/= (const Value& a);
    Value& operator%= (const Value& a);
    Value& operator&= (const Value& a);
    Value& operator|= (const Value& a);
    Value& operator^= (const Value& a);

    Value& operator++ ();
    Value operator++ (int);
    Value& operator-- ();
    Value operator-- (int);

    friend std::ostream& operator<<(std::ostream& out, const Value& a);
    friend std::istream& operator>>(std::istream& in, Value& a);

    unsigned operator[] (size_t a);

    Value operator() (unsigned a, unsigned b);

    Value operator, (Value a);

    Value& operator= (const Value& a);

    Value& operator*();

private:
    unsigned mTrueValue;
    unsigned mFakeValue;
};