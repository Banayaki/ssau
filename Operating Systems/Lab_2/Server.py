import os
import random
import subprocess


class Server:
    # subprocess.run(["konsoleprofile colors=WhiteOnBlack"], shell=True)
    listOfThemes = [
        "WhiteOnBlack",
        "BlackOnWhite",
        "Breeze",
        "Maia",
        "Oxygen",
        "RedOnBlack"
    ]

    def __init__(self, clients_count: int):
        self.launcher = "konsole"
        self.prefix = "pipes/fifo"
        self.clients_count = clients_count
        self.pid_list = []
        self.create_fifo()
        self.create_users()
        self.start()

    def create_users(self):
        for i in range(self.clients_count):
            p = subprocess.Popen([self.launcher, '-e', 'python', 'Client.py'])
            self.pid_list.append(p.pid)
            self.create_fifo(p.pid)
        pass

    def print_list_of_clients(self):
        print(self.pid_list)

    def change_client_color(self, pid: int):
        name = 'pipes/fifout' + str(pid)
        f = open(name, 'w')
        f.write("konsoleprofile colors=" + random.choice(self.listOfThemes))
        f.close()
        pass

    def set_default_color(self, pid: int):
        name = 'pipes/fifout' + str(pid)
        f = open(name, 'w')
        f.write("konsoleprofile colors=default")
        f.close()
        pass

    def create_fifo(self, pid: int):
        name = self.prefix + str(pid)
        os.mkfifo(name)

    def close_fifo(self, pid: int):
        name = self.prefix + str(pid)
        os.unlink(name)

    def start(self):
        pass

    pass


if __name__ == "__main__":
    pass
