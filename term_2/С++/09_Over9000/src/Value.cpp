#include "../lib/Value.h"

Value::Value(unsigned trueValue, unsigned fakeValue)
{
    this->mTrueValue = trueValue;
    this->mFakeValue = fakeValue;
}


Value::operator unsigned() const
{
    return this->mTrueValue;
}


unsigned Value::getTrueValue() const
{
    return mTrueValue;
}

unsigned Value::getFakeValue() const
{
    return mFakeValue;
}

void Value::setTrueValue(unsigned value)
{
    this->mTrueValue = value;
}

void Value::setFakeValue(unsigned value)
{
    this->mFakeValue = value;
}


Value operator+(const Value& a, const Value& b)
{
    return Value(a.mTrueValue + b.mTrueValue, a.mFakeValue + b.mFakeValue);
}

Value operator-(const Value& a, const Value& b)
{
    return Value(a.mTrueValue - b.mTrueValue, a.mFakeValue - b.mFakeValue);
}

Value operator*(const Value& a, const Value& b)
{
    return Value(a.mTrueValue * b.mTrueValue, a.mFakeValue * b.mFakeValue);
}

Value operator/(const Value& a, const Value& b)
{
    return Value(a.mTrueValue / (b.mTrueValue > 0 ? b.mTrueValue : 1),
                 a.mFakeValue / (b.mFakeValue > 0 ? b.mFakeValue : 1));
}

Value operator%(const Value& a, const Value& b)
{
    return Value(a.mTrueValue % (b.mTrueValue > 0 ? b.mTrueValue : 1),
                 a.mFakeValue % (b.mFakeValue > 0 ? b.mFakeValue : 1));
}


Value operator^(const Value& a, const Value& b)
{
    return Value(a.mTrueValue ^ b.mTrueValue, a.mFakeValue ^ b.mFakeValue);
}

Value operator|(const Value& a, const Value& b)
{
    return Value(a.mTrueValue | b.mTrueValue, a.mFakeValue | b.mFakeValue);
}

Value operator&(const Value& a, const Value& b)
{
    return Value(a.mTrueValue & b.mTrueValue, a.mFakeValue & b.mFakeValue);
}

Value operator~(const Value& a)
{
    return Value(~a.mTrueValue, ~a.mFakeValue);
}


Value operator==(const Value& a, const Value& b)
{
    return Value((a.mTrueValue == b.mTrueValue), (a.mFakeValue == b.mFakeValue));
}

Value operator!=(const Value& a, const Value& b)
{
    return Value((a.mTrueValue != b.mTrueValue), (a.mFakeValue != b.mFakeValue));
}

Value operator>(const Value& a, const Value& b)
{
    return Value((a.mTrueValue > b.mTrueValue), (a.mFakeValue > b.mFakeValue));
}

Value operator<(const Value& a, const Value& b)
{
    return Value((a.mTrueValue < b.mTrueValue), (a.mFakeValue < b.mFakeValue));
}

Value operator>=(const Value& a, const Value& b)
{
    return Value((a.mTrueValue >= b.mTrueValue), (a.mFakeValue >= b.mFakeValue));
}

Value operator<=(const Value& a, const Value& b)
{
    return Value((a.mTrueValue <= b.mTrueValue), (a.mFakeValue <= b.mFakeValue));
}


Value operator||(const Value& a, const Value& b)
{
    return Value(a.mTrueValue || b.mTrueValue, a.mFakeValue || b.mFakeValue);
}

Value operator&&(const Value& a, const Value& b)
{
    return Value(a.mTrueValue && b.mTrueValue, a.mFakeValue && b.mFakeValue);
}

Value operator!(const Value& a)
{
    return Value(!a.mTrueValue, !a.mFakeValue);
}


Value& Value::operator<<=(const Value& a)
{
    this->mTrueValue <<= a.mTrueValue;
    this->mFakeValue <<= a.mFakeValue;
    return *this;
}

Value& Value::operator>>=(const Value& a)
{
    this->mTrueValue >>= a.mTrueValue;
    this->mFakeValue >>= a.mFakeValue;
    return *this;
}

Value& Value::operator+=(const Value& a)
{
    this->mTrueValue += a.mTrueValue;
    this->mFakeValue += a.mFakeValue;
    return *this;
}

Value& Value::operator-=(const Value& a)
{
    this->mTrueValue -= a.mTrueValue;
    this->mFakeValue -= a.mFakeValue;
    return *this;
}

Value& Value::operator*=(const Value& a)
{
    this->mTrueValue <<= a.mTrueValue;
    this->mFakeValue <<= a.mFakeValue;
    return *this;
}

Value& Value::operator/=(const Value& a)
{
    this->mTrueValue /= (a.mTrueValue > 0 ? a.mTrueValue : 1);
    this->mFakeValue /= (a.mFakeValue > 0 ? a.mFakeValue : 1);
    return *this;
}

Value& Value::operator%=(const Value& a)
{
    this->mTrueValue %= (a.mTrueValue > 0 ? a.mTrueValue : 1);
    this->mFakeValue %= (a.mFakeValue > 0 ? a.mFakeValue : 1);
    return *this;
}

Value& Value::operator&=(const Value& a)
{
    this->mTrueValue &= a.mTrueValue;
    this->mFakeValue &= a.mFakeValue;
    return *this;
}

Value& Value::operator|=(const Value& a)
{
    this->mTrueValue |= a.mTrueValue;
    this->mFakeValue |= a.mFakeValue;
    return *this;
}

Value& Value::operator^=(const Value& a)
{
    this->mTrueValue ^= a.mTrueValue;
    this->mFakeValue ^= a.mFakeValue;
    return *this;
}


Value& Value::operator++()
{
    ++this->mTrueValue;
    ++this->mFakeValue;
    return *this;
}

Value Value::operator++(int)
{
    Value tmp(*this);
    ++(*this);
    return tmp;
}

Value& Value::operator--()
{
    --this->mTrueValue;
    --this->mFakeValue;
    return *this;
}

Value Value::operator--(int)
{
    Value tmp(*this);
    --(*this);
    return tmp;
}


std::ostream& operator<<(std::ostream& out, const Value& a)
{
    out << "(" << a.getTrueValue() << ", " << a.getFakeValue() << ")";
    return out;
}

std::istream& operator>>(std::istream& in, Value& a)
{
    unsigned trueVal, fakeVal;
    in.ignore(1);
    in >> trueVal;
    a.setTrueValue(trueVal);
    in.ignore(2);
    in >> fakeVal;
    a.setFakeValue(fakeVal);
    in.ignore(1);
    return in;
}


unsigned Value::operator[](size_t a)
{
    return a ? mFakeValue : mTrueValue;
}

Value Value::operator()(unsigned a, unsigned b)
{
    this->mTrueValue = a;
    this->mFakeValue = b;
    return *this;
}

Value& Value::operator*()
{
    return *this;
}

Value Value::operator,(Value a)
{
    this->mTrueValue = a.mTrueValue;
    this->mFakeValue = a.mFakeValue;
    std::cout << *this;
    return *this;
}

Value& Value::operator=(const Value& a) = default;
