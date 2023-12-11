import math
import logging


class Wolf:
    def __init__(self, movement_distance):
        self.movement_distance = movement_distance
        self.x = 0.0
        self.y = 0.0

    def move(self, herd_of_sheep):
        closest_sheep = min(
            (sheep for sheep in herd_of_sheep if sheep.alive),
            key=lambda sheep: math.sqrt((sheep.x - self.x) ** 2 + (sheep.y - self.y) ** 2)
        )
        sheep_number = herd_of_sheep.index(closest_sheep) + 1
        distance_to_closest_sheep = math.sqrt((closest_sheep.x - self.x) ** 2 + (closest_sheep.y - self.y) ** 2)

        if distance_to_closest_sheep <= self.movement_distance:
            closest_sheep.alive = False
            self.x = closest_sheep.x
            self.y = closest_sheep.y
            logging.debug(f"New wolf position X: {self.x} Y: {self.y}")
            logging.info("The wolf moved")
            logging.info(f"The wolf ate sheep no: {sheep_number}")
            return "The wolf ate sheep: " + str(sheep_number)
        else:
            direction_x = (closest_sheep.x - self.x) / distance_to_closest_sheep
            direction_y = (closest_sheep.y - self.y) / distance_to_closest_sheep

            if abs(direction_x) > abs(direction_y):
                self.x += math.copysign(self.movement_distance, direction_x)
            else:
                self.y += math.copysign(self.movement_distance, direction_y)
            logging.debug(f"New wolf position X: {self.x} Y: {self.y}")
            logging.info("The wolf moved")
            logging.debug(f"Closest sheep no: {sheep_number}, distance to closest sheep: {round(distance_to_closest_sheep, 3)}")
            logging.info(f"The wolf is chasing sheep no: {sheep_number}")
            return "The wolf is chasing sheep: " + str(sheep_number)

