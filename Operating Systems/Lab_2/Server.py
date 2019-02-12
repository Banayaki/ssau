import os
import random
import re
import subprocess
import sys


class Server:
    # subprocess.run(["konsoleprofile colors=WhiteOnBlack"], shell=True)
    listOfThemes = [
        "WhiteOnBlack",
        "BlackOnWhite",
        "Breeze",
        "Maia",
        "Oxygen",
        "RedOnBlack",
        "Solorized"
    ]

    atomicInteger = 0

    def __init__(self, clients_count: int):
        self.launcher = "konsole"
        self.prefix = "pipes/fifo"
        self.clients_count = clients_count
        self.id_list = []
        self.processes = []
        self.create_users()

    def create_users(self):
        for i in range(self.clients_count):
            user_id = Server.increment_and_get_id()
            p = subprocess.Popen([self.launcher, '-e', 'python', 'Client.py', str(user_id)])
            self.id_list.append(user_id)
            self.processes.append(p)
            print(p.pid)
            self.create_fifo(user_id)
        pass

    def print_list_of_clients(self):
        print(self.id_list)

    def change_client_color(self, user_id: int, theme: int):
        name = self.prefix + str(user_id)
        f = open(name, 'w')
        f.write("colors=" + self.listOfThemes[theme])
        f.close()
        pass

    def set_default_color(self, user_id: int):
        name = self.prefix + str(user_id)
        f = open(name, 'w')
        f.write("colors=Default")
        f.close()
        pass

    def create_fifo(self, user_id: int):
        name = self.prefix + str(user_id)
        os.mkfifo(name)

    def close_fifo(self, user_id: int):
        name = self.prefix + str(user_id)
        os.unlink(name)

    def shutdown(self):
        for p in self.processes:
            try:
                p.kill()
            except Exception as ex:
                print(str(ex))
        for client_id in self.id_list:
            server.close_fifo(client_id)

        print("Press any key to exit...")
        input()
        quit(1)

    @staticmethod
    def increment_and_get_id():
        Server.atomicInteger += 1
        return Server.atomicInteger


if __name__ == "__main__":
    count_of_clients = int(sys.argv[1])
    server = Server(count_of_clients)
    print("Server started. " + str(count_of_clients) + " clients generated. Print 'help' for more information")
    try:
        while True:
            string = input()
            if string == "quit":
                server.shutdown()

            if string == "help":
                f = open('about.txt', 'r')
                print(f.read())
                f.close()

            elif re.match(r'^color \d \d$', string):
                pid = int(string.split(' ')[1])
                theme = int(string.split(' ')[2])
                print("Sending message to " + str(pid))
                if pid in server.id_list:
                    server.change_client_color(pid, theme)
                    print("Color should be changed")
                else:
                    print("Unknown id: call 'client_list' command")

            elif re.match(r'^default \d*$', string):
                pid = int(string.split(' ')[1])
                print("Setting default color to " + str(pid))
                if pid in server.id_list:
                    server.set_default_color(pid)

            elif string == 'client_list':
                print(server.id_list)

            elif string == 'theme_list':
                print(server.listOfThemes)

            else:
                print("Unknown command. Use 'help'")

    except KeyboardInterrupt:
        server.shutdown()

    except Exception as ex:
        print(str(ex))
        server.shutdown()
    pass
