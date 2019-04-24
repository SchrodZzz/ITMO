#include <iostream>

struct Foo
{
    void say() const
    {
        std::cout << "Foo says: " << msg << "\n";
    }

protected:
    Foo(const char* msg) : msg(msg)
    {}

private:
    const char* msg;
};

struct Bar : Foo
{
    Bar(const char* msg) : Foo(msg)
    {}
};

void foo_says(const Foo& foo)
{
    foo.say();
}

Bar get_foo(const char* msg)
{
    return Bar(msg);
}

int main()
{
    foo_says(get_foo("If ours lose, ours will beat yours!"));
    return 0;
}