USE quiz_ai_system;

INSERT INTO questions (question_text, optiona, optionb, optionc, optiond, correct_answer, topic, difficulty) VALUES

-- ===================== PYTHON - EASY (15) =====================
('What is the output of print(type(1))?', '<class ''int''>', '<class ''float''>', '<class ''num''>', '<class ''number''>', 'A', 'python', 'Easy'),
('Which keyword is used to define a function in Python?', 'func', 'def', 'function', 'define', 'B', 'python', 'Easy'),
('What does len("hello") return?', '4', '5', '6', 'Error', 'B', 'python', 'Easy'),
('Which of these is a valid Python comment?', '// comment', '/* comment */', '# comment', '-- comment', 'C', 'python', 'Easy'),
('What is the result of 10 % 3?', '3', '1', '0', '2', 'B', 'python', 'Easy'),
('Which data type is used to store True or False?', 'int', 'str', 'bool', 'bit', 'C', 'python', 'Easy'),
('How do you create a list in Python?', 'list = (1,2,3)', 'list = {1,2,3}', 'list = [1,2,3]', 'list = <1,2,3>', 'C', 'python', 'Easy'),
('What keyword is used to create a loop in Python?', 'loop', 'repeat', 'for', 'iterate', 'C', 'python', 'Easy'),
('What is the output of print(2 ** 3)?', '6', '8', '9', '5', 'B', 'python', 'Easy'),
('Which function converts a string to an integer?', 'str()', 'float()', 'int()', 'num()', 'C', 'python', 'Easy'),
('What symbol is used for string concatenation in Python?', '*', '-', '+', '&', 'C', 'python', 'Easy'),
('How do you get input from the user in Python?', 'scan()', 'read()', 'input()', 'get()', 'C', 'python', 'Easy'),
('What is the index of the first element in a Python list?', '1', '-1', '0', 'None', 'C', 'python', 'Easy'),
('Which of these is NOT a Python data type?', 'list', 'tuple', 'array', 'dict', 'C', 'python', 'Easy'),
('What does print("Hello" * 2) output?', 'Hello Hello', 'HelloHello', 'Hello2', 'Error', 'B', 'python', 'Easy'),

-- ===================== PYTHON - MEDIUM (15) =====================
('What is the output of [x**2 for x in range(4)]?', '[1,4,9,16]', '[0,1,4,9]', '[0,2,4,6]', '[1,2,3,4]', 'B', 'python', 'Medium'),
('Which method removes and returns the last element of a list?', 'remove()', 'delete()', 'pop()', 'discard()', 'C', 'python', 'Medium'),
('What does the "pass" statement do in Python?', 'Exits the loop', 'Does nothing', 'Skips to next iteration', 'Returns None', 'B', 'python', 'Medium'),
('What is a lambda function?', 'A named function', 'An anonymous function', 'A recursive function', 'A built-in function', 'B', 'python', 'Medium'),
('What does dict.get("key", "default") do?', 'Raises KeyError if missing', 'Returns default if key not found', 'Deletes the key', 'Updates the key', 'B', 'python', 'Medium'),
('Which module is used for regular expressions in Python?', 'regex', 're', 'regexp', 'pattern', 'B', 'python', 'Medium'),
('What is the output of bool("") in Python?', 'True', 'None', 'False', 'Error', 'C', 'python', 'Medium'),
('What does *args allow in a function?', 'Keyword arguments', 'Variable positional arguments', 'Default arguments', 'Named arguments', 'B', 'python', 'Medium'),
('Which of these creates a set in Python?', '{1,2,3}', '[1,2,3]', '(1,2,3)', '<1,2,3>', 'A', 'python', 'Medium'),
('What is the difference between "is" and "==" in Python?', 'No difference', '"is" checks identity, "==" checks value', '"==" checks identity, "is" checks value', 'Both check identity', 'B', 'python', 'Medium'),
('What does enumerate() do?', 'Sorts a list', 'Returns index-value pairs', 'Filters elements', 'Maps a function', 'B', 'python', 'Medium'),
('What is the output of list(range(2, 10, 3))?', '[2,5,8]', '[2,4,6,8]', '[3,6,9]', '[2,3,4]', 'A', 'python', 'Medium'),
('Which exception is raised for division by zero?', 'ValueError', 'ArithmeticError', 'ZeroDivisionError', 'MathError', 'C', 'python', 'Medium'),
('What does the "with" statement do in Python?', 'Creates a loop', 'Manages context (e.g. file handling)', 'Defines a class', 'Imports a module', 'B', 'python', 'Medium'),
('What is the output of "python"[::-1]?', 'nohtyp', 'python', 'nohtyP', 'Error', 'A', 'python', 'Medium'),

-- ===================== PYTHON - HARD (15) =====================
('What is the output of: x = [1,2,3]; y = x; y.append(4); print(x)?', '[1,2,3]', '[1,2,3,4]', 'Error', '[4,3,2,1]', 'B', 'python', 'Hard'),
('What does @staticmethod decorator do?', 'Binds method to instance', 'Binds method to class', 'Defines method with no implicit first argument', 'Makes method private', 'C', 'python', 'Hard'),
('What is a generator in Python?', 'A function that returns a list', 'A function that yields values lazily', 'A class that generates objects', 'A built-in data structure', 'B', 'python', 'Hard'),
('What is the MRO (Method Resolution Order) used for?', 'Memory management', 'Determining method lookup order in inheritance', 'Sorting class methods', 'Resolving import conflicts', 'B', 'python', 'Hard'),
('What does __slots__ do in a Python class?', 'Defines class methods', 'Restricts instance attributes to save memory', 'Creates abstract methods', 'Enables multiple inheritance', 'B', 'python', 'Hard'),
('What is the output of: (lambda x, y: x if x > y else y)(3, 7)?', '3', '7', 'True', 'Error', 'B', 'python', 'Hard'),
('Which of these correctly implements a singleton pattern in Python?', 'Using __new__ to return existing instance', 'Using __init__ with a counter', 'Using global variables', 'Using class decorators only', 'A', 'python', 'Hard'),
('What does functools.lru_cache do?', 'Sorts function results', 'Caches function results for repeated calls', 'Limits function call frequency', 'Logs function calls', 'B', 'python', 'Hard'),
('What is the difference between deepcopy and copy?', 'No difference', 'deepcopy copies nested objects recursively', 'copy copies nested objects recursively', 'deepcopy is faster', 'B', 'python', 'Hard'),
('What does __enter__ and __exit__ enable?', 'Operator overloading', 'Context manager protocol', 'Iterator protocol', 'Descriptor protocol', 'B', 'python', 'Hard'),
('What is the output of: {**{"a":1}, **{"a":2, "b":3}}?', '{''a'':1,''b'':3}', '{''a'':2,''b'':3}', 'Error', '{''a'':[1,2],''b'':3}', 'B', 'python', 'Hard'),
('What does the GIL (Global Interpreter Lock) affect?', 'Memory allocation', 'True parallelism of threads in CPython', 'Garbage collection', 'Module imports', 'B', 'python', 'Hard'),
('What is a descriptor in Python?', 'A docstring', 'An object that defines __get__, __set__, or __delete__', 'A type annotation', 'A class decorator', 'B', 'python', 'Hard'),
('What does itertools.chain() do?', 'Sorts multiple iterables', 'Chains multiple iterables into one', 'Zips multiple iterables', 'Filters iterables', 'B', 'python', 'Hard'),
('What is the output of: next(x for x in range(10) if x > 5)?', '5', '6', '7', 'Error', 'B', 'python', 'Hard');
