import os

from SystemCalls import SystemCalls


# noinspection PyMethodMayBeStatic,PyShadowingNames
class CommandLineListener:

    def __init__(self, buffer_size=128):
        self.buffer_size = buffer_size
        self.system_calls = SystemCalls()
        self.prefix = self.get_prefix()

    def get_prefix(self):
        return f'[{os.getlogin()} {os.getcwd()}]$'

    def do(self, input_command):
        input_command = input_command.strip().split(' ')
        command = input_command[0]
        args = None
        if len(input_command) > 1:
            args = input_command[1:]

        try:
            f = getattr(CommandLineListener, command)
            f(self, args)

        except Exception as ex:
            print(ex)

    def help(self, args):
        with open('help.txt', 'r') as f:
            print(f.read())

    def cat(self, args):
        path = args[0]
        with open(path, 'r') as f:
            f.seek(0, os.SEEK_END)
            file_length = f.tell()
            f.seek(0, os.SEEK_SET)
            while f.tell() < file_length:
                content = f.read(self.buffer_size)
                print(content, end='')

    def tail(self, args):
        path = args[0]
        lines_count = args[1] if len(args) > 1 else 5
        self.system_calls.tail(path, lines_count)

    def head(self, args):
        path = args[0]
        lines_count = args[1] if len(args) > 1 else 5
        self.system_calls.head(path, lines_count)

    def stat(self, args):
        path = args[0] if args is not None else '.'
        self.system_calls.stat(path)

    def ls(self, args):
        path = args[0] if args is not None else '.'
        print(os.listdir(path))

    def mv(self, args):
        src = args[0]
        dst = args[1]
        os.replace(src, dst)

    def cp(self, args):
        src = args[0]
        dst = args[1]
        with open(src, 'r') as source:
            with open(dst, 'w') as destination:
                source.seek(0, os.SEEK_END)
                file_length = source.tell()
                source.seek(0, os.SEEK_SET)
                while source.tell() < file_length:
                    block = source.read(self.buffer_size)
                    destination.write(block)

    def rm(self, args):
        path = args[0]
        if os.path.isdir(path):
            os.rmdir(path)
        else:
            os.remove(path)

    def chmod(self, args):
        rights = int(args[0], 8)
        path = args[1]
        os.chmod(path, rights)

    def rename(self, args):
        self.mv(args)

    def cd(self, args):
        path = args[0]
        os.chdir(path)
        self.prefix = self.get_prefix()

    def mkdir(self, args):
        path = args[0]
        os.makedirs(path)

    def touch(self, args):
        name = args[0]
        open(name, 'w').close()

    def exit(self, args):
        print('Bye, bye!')
        exit(1)


if __name__ == '__main__':
    listener = CommandLineListener()
    print(listener.prefix, end=' ')
    print('Run "help" if you don\'t know what to do')
    while True:
        print(listener.prefix, end=' ')
        input_command = input()
        listener.do(input_command)
    pass
