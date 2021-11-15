import time


class ProfilerTestObject:
    def method1(self):
        time.sleep(2)

    @staticmethod
    def method2():
        time.sleep(1)

    class Subclass:
        @staticmethod
        def method3():
            time.sleep(1)


def f():
    time.sleep(1)
    ProfilerTestObject.method2()


def g():
    time.sleep(2)


def h():
    time.sleep(3)


def test():
    f()
    g()
    h()
    f()
    profiler_test_object = ProfilerTestObject()
    profiler_test_object.method1()
    profiler_test_object.Subclass.method3()
