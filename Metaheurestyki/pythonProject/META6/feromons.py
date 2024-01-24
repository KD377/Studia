class Feromons:
    def __init__(self, num_of_elements, evaporation_rate):
        self.num_of_elements = num_of_elements
        self.weights = [[1 / 10000.0] * num_of_elements for _ in range(num_of_elements)]
        self.evaporation_rate = evaporation_rate

    def get_feromon(self, id1, id2):
        return self.weights[id1][id2]

    def set_feromon(self, id1, id2, value):
        self.weights[id1][id2] = self.weights[id2][id1] = value

    def evaporate(self):
        for i in range(self.num_of_elements):
            for j in range(self.num_of_elements):
                self.weights[i][j] *= (1 - self.evaporation_rate)
