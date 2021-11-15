import time
from profiler.utils import get_indention


def profiler_decorator(func, execution_time, calls_statistic, indent, class_name=''):
    def wrapper(*args, **kwargs):
        func_name = func.__name__ if class_name == '' else class_name + '.' + func.__name__
        print(f'{get_indention(indent[0])}{func_name}')
        indent[0] += 1

        start_time = time.time()
        function_result = func(*args, **kwargs)
        end_time = time.time()

        calls_statistic[func_name] += 1
        execution_time[func_name] += end_time - start_time
        indent[0] -= 2
        return function_result

    return wrapper
