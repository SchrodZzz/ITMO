#pragma once

#include "PlaneFigure.h"

class Rect : public PlaneFigure
{
public:
    Rect(double w, double h);

    ~Rect() {};

    double getArea();
    double getPerimeter();

private:
    double mWidth;
    double mHeight;
};
