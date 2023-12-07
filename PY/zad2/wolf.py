import math


class Wolf:
    def __init__(self, movement_distance):
        self.movement_distance = movement_distance
        self.x = 0.0
        self.y = 0.0

    def move(self, herd_of_sheeps):
        closest_sheep = min(
            (sheep for sheep in herd_of_sheeps if sheep.alive),
            key=lambda sheep: math.sqrt((sheep.x - self.x) ** 2 + (sheep.y - self.y) ** 2)
        )
        sheep_number = herd_of_sheeps.index(closest_sheep) + 1
        distance_to_closest_sheep = math.sqrt((closest_sheep.x - self.x) ** 2 + (closest_sheep.y - self.y) ** 2)

        if distance_to_closest_sheep <= self.movement_distance:
            closest_sheep.alive = False
            self.x = closest_sheep.x
            self.y = closest_sheep.y
            return "The wolf ate sheep: " + str(sheep_number)
        else:
            direction_x = (closest_sheep.x - self.x) / distance_to_closest_sheep
            direction_y = (closest_sheep.y - self.y) / distance_to_closest_sheep

            if abs(direction_x) > abs(direction_y):
                self.x += math.copysign(self.movement_distance, direction_x)
            else:
                self.y += math.copysign(self.movement_distance, direction_y)
            return "The wolf is chasing sheep: " + str(sheep_number)

