import os
import re
import subprocess
import sys


def exit():
    print("Press any key to exit...")
    input()
    quit(1)


if __name__ == "__main__":
    try:
        path = '/home/banayaki/PreFire/ssau/Operating Systems/Lab_2'
        os.chdir(path)

        user_id = sys.argv[1]

        print("Your id = " + str(user_id))
        pipe_name = 'pipes/fifo' + str(user_id)
        pipe = open(pipe_name, 'r')

        if not pipe.readable():
            print("Something wrongs with pipe. Exiting...")
            exit()

        print("Pipe is opened now.")

        while True:
            line = pipe.read()
            if line != "":
                if line == 'quit':
                    print("Bye")
                    exit()

                elif re.match(r'^colors=\w*$', line):
                    print("change color")
                    color = line.split('=')[1]
                    subprocess.run(["konsoleprofile colors=" + color], shell=True)

    except Exception as e:
        f = open("out.txt", 'w')
        f.write(str(e))
        f.close()
        print(str(e))
        exit()
