import math


class Distances:
    def __init__(self, places):
        self.num_of_places = len(places)
        self.distances = [[0] * self.num_of_places for _ in range(self.num_of_places)]

        self.distances = [
            [
                math.sqrt((places[i].x - places[j].x) ** 2 + (places[i].y - places[j].y) ** 2)
                for j in range(self.num_of_places)
            ]
            for i in range(self.num_of_places)
        ]

        self.places = places

    def get_distance(self, id1, id2):
        return self.distances[id1][id2]

    def get_place(self, id):
        return self.places[id]
