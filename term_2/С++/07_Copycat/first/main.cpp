#include <iostream>
#include "Rect.h"

class Driver
{
public:
    void test(Rect r) const
    {
        printf("%lf\n", r.getArea());
        printf("%lf\n", r.getPerimeter());
    }
};

int main()
{
    Driver driver;
    Rect pf = Rect(2, 3);
    driver.test(pf);
    return 0;
}