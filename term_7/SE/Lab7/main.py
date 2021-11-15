import argparse

from profiler.profiler import Profiler


# to run tests:
# python3 main.py -m my_test.test1 -f test -p my_test.test1
# python3 main.py -m my_test.test2 -f test -p my_test.test2
def run_profiler():
    parser = argparse.ArgumentParser()
    parser.add_argument('-m', '--module')
    parser.add_argument('-f', '--function')
    parser.add_argument('-p', '--function-package')

    args = parser.parse_args()

    profiler = Profiler(args.module)
    profiler.profile(args.function_package, args.function)


if __name__ == '__main__':
    run_profiler()
