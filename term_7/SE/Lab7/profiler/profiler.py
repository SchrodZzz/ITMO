import collections
import importlib
import inspect

from profiler.utils import LINES_SEPARATOR
from profiler.decorator import profiler_decorator


class Profiler:

    def __init__(self, module_name):
        self.execution_time = collections.defaultdict(int)
        self.calls_count = collections.defaultdict(int)
        self.indent = [0]

        self.module = importlib.import_module(module_name)
        self.__decorate_package(self.module, profiler_decorator)

    def __decorate_package(self, module, decorator, prefix=''):
        for name in dir(module):
            try:
                package_item = getattr(module, name)
                if inspect.isfunction(package_item):
                    setattr(module, name,
                            decorator(package_item, self.execution_time, self.calls_count, self.indent, prefix))
                elif inspect.isclass(package_item) and not name.startswith("__"):
                    new_prefix = name if prefix == '' else prefix + '.' + name
                    self.__decorate_package(package_item, decorator, new_prefix)
            except AttributeError:
                pass

    def profile(self, function_package, profiling_function):
        print('🌳:')
        getattr(importlib.import_module(function_package), profiling_function)()
        print(LINES_SEPARATOR)

        print('⏰:')
        for func, duration in self.execution_time.items():
            print(f"{func}: {duration} s.")
        print(LINES_SEPARATOR)

        print('avg ⏰:')
        for func, duration in self.execution_time.items():
            print("{}: {} s.".format(func, duration / self.calls_count[func]))

        print('📞 cnt')
        for func, count in self.calls_count.items():
            print(f"{func}: {count} time(s)")
        print(LINES_SEPARATOR)
