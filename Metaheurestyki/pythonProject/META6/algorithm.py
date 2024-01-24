from Ant import Ant
from distances import Distances
from feromons import Feromons


class AntAlgorithm:
    def __init__(self, places, num_of_ants, num_of_iterations, feromon_evaporation, feromon_weight,
                 heuristic_weight, random_choice_chance, vehicle_max_demand):
        self.num_of_places = len(places)
        self.distances = Distances(places)
        self.feromons = Feromons(self.num_of_places, feromon_evaporation)
        self.num_of_iterations = num_of_iterations
        self.feromon_weight = feromon_weight
        self.heuristic_weight = heuristic_weight
        self.random_choice_chance = random_choice_chance
        self.num_of_ants = num_of_ants
        self.places = places
        self.vehicle_max_demand = vehicle_max_demand
        self.ants = [Ant(self.distances, self.feromons, random_choice_chance, self.num_of_places,
                         heuristic_weight, feromon_weight, vehicle_max_demand) for _ in range(num_of_ants)]
        self.iterations = []
        self.avg_population_values = []
        self.min_population_values = []

    def start(self):
        shortest_route = float('inf')
        best_ant = None
        for iteration in range(self.num_of_iterations):
            self.iterations.append(iteration)
            for ant in self.ants:
                while ant.get_to_visit_size() > 1 or (ant.get_to_visit_size() == 1 and ant.get_to_visit()[0] != 0):
                    ant.move()
                ant.come_back_to_base()
            avg_route_in_this_iteration = 0
            for ant in self.ants:
                route = ant.calculate_whole_route()
                avg_route_in_this_iteration += route
                if route < shortest_route:
                    shortest_route = route
                    best_ant = ant
            self.avg_population_values.append(avg_route_in_this_iteration / len(self.ants))
            self.min_population_values.append(shortest_route)
            self.feromons.evaporate()
            for ant in self.ants:
                ant.add_feromons()
            self.ants = [Ant(self.distances, self.feromons, self.random_choice_chance, self.num_of_places,
                              self.heuristic_weight, self.feromon_weight, self.vehicle_max_demand)
                         for _ in range(self.num_of_ants)]
        results = {
            "ShortestRoute": shortest_route,
            "NumOfVehicles": best_ant.get_num_of_vehicles()
        }
        return results

    def get_iterations(self):
        return self.iterations

    def get_avg_population_values(self):
        return self.avg_population_values

    def get_min_population_values(self):
        return self.min_population_values