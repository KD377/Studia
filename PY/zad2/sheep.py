import random


class Sheep:
    def __init__(self, starting_bound, movement_distance):
        self.movement_distance = movement_distance
        self.x = random.uniform(-starting_bound, starting_bound)
        self.y = random.uniform(-starting_bound, starting_bound)
        self.alive = True

    def move(self):
        direction = random.choice(['north', 'south', 'east', 'west'])

        if direction == 'north':
            self.y += self.movement_distance
        elif direction == 'south':
            self.y -= self.movement_distance
        elif direction == 'east':
            self.x += self.movement_distance
        elif direction == 'west':
            self.x -= self.movement_distance
        return direction
