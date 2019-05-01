import subprocess


class SystemCalls:

    def __init__(self, buffer_size=128):
        self.buffer_size = buffer_size

    def head(self, path_to_file, count_of_lines=5):
        subprocess.call(['head', '-n', str(count_of_lines), path_to_file], bufsize=self.buffer_size)

    def tail(self, path_to_file, count_of_lines=5):
        subprocess.call(['tail', '-n', str(count_of_lines), path_to_file], bufsize=self.buffer_size)

    def stat(self, path):
        subprocess.call(['ls', path, '-alh'])
