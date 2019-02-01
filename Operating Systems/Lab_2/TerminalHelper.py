import random


class TerminalHelper:
    listOfThemes = [
        "WhiteOnBlack",
        "BlackOnWhite",
        "Breeze",
        "Maia",
        "Oxygen",
        "RedOnBlack"
    ]

    def __init__(self):
        pass

    def choose_one(self):
        return random.choice(self.listOfThemes)

    pass
