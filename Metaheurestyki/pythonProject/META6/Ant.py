import random

class Ant:
    def __init__(self, distances, feromons, random_move_chance, places_to_visit,
                 heuristic_weight, feromon_weight, max_demand):
        self.current_position = 0
        self.visited = [self.current_position]
        self.distances = distances
        self.feromons = feromons
        self.random_move_chance = random_move_chance
        self.num_of_places_to_visit = places_to_visit - 1
        self.heuristic_weight = heuristic_weight
        self.feromon_weight = feromon_weight
        self.max_demand = max_demand
        self.to_visit = list(range(1, self.num_of_places_to_visit + 1))
        self.next_possible_position = 0
        self.route = -1
        self.current_demand = 0
        self.current_time = 0
        self.num_of_vehicles = 0

    def move(self):
        possible_to_visit = [i for i in self.to_visit if self.distances.get_place(i).due_time > self.current_time]
        self.choose_next_position(possible_to_visit)
        self.update_current_position(possible_to_visit)
        self.update_current_demand_time()

    def choose_next_position(self, possible_to_visit):
        if possible_to_visit:
            self.next_possible_position = self.make_probabilistic_choice(possible_to_visit)
        else:
            self.next_possible_position = 0

    def make_probabilistic_choice(self, possible_to_visit):
        if random.random() < self.random_move_chance:
            return random.choice(possible_to_visit)
        else:
            return self.roulette_wheel_selection(possible_to_visit)

    def roulette_wheel_selection(self, possible_to_visit):
        chances = [
            (i, (self.feromons.get_feromon(self.current_position, i) ** self.feromon_weight) *
             (1 / self.distances.get_distance(self.current_position, i)) ** self.heuristic_weight)
            for i in range(1, self.num_of_places_to_visit + 1) if i in possible_to_visit
        ]

        if not chances:
            return 0

        roulette_index = random.uniform(0, sum(chance[1] for chance in chances))
        cumulative_sum = 0
        for index, chance_value in chances:
            cumulative_sum += chance_value
            if roulette_index <= cumulative_sum:
                return index

        return 0

    def update_current_position(self, possible_to_visit):
        if self.should_reset_current_position(possible_to_visit):
            self.reset_current_position()
        self.update_visited_to_visit()

    def should_reset_current_position(self, possible_to_visit):
        return self.current_demand + self.distances.get_place(self.next_possible_position).demand > self.max_demand or not possible_to_visit

    def reset_current_position(self):
        self.current_demand = 0
        self.current_time = 0
        self.next_possible_position = 0

    def update_visited_to_visit(self):
        self.current_position = self.next_possible_position
        self.visited.append(self.current_position)
        if self.current_position in self.to_visit:
            self.to_visit.remove(self.current_position)

    def update_current_demand_time(self):
        p = self.distances.get_place(self.current_position)
        self.current_demand += p.demand
        self.update_current_time(p)

    def update_current_time(self, place):
        if place.ready_time > self.current_time:
            self.current_time = place.ready_time
        self.current_time += place.service_time
        self.update_vehicles_if_at_base()

    def update_vehicles_if_at_base(self):
        if self.current_position == 0:
            self.num_of_vehicles += 1

    def come_back_to_base(self):
        self.visited.append(0)

    def add_feromons(self):
        previous_index = -1
        for v in self.visited:
            if previous_index != -1:
                self.feromons.set_feromon(previous_index, v, self.feromons.get_feromon(previous_index, v) +
                                          (1 / self.calculate_whole_route()))
            previous_index = v

    def calculate_whole_route(self):
        if self.route != -1:
            return self.route
        route = 0
        last_index = -1
        for id in self.visited:
            if last_index != -1:
                route += self.distances.get_distance(last_index, id)
            last_index = id
        self.route = route
        return route

    def get_routes(self):
        routes = []
        last_base = 0
        for i in range(1, len(self.visited)):
            if self.visited[i] == 0:
                routes.append(self.visited[last_base:i])
                last_base = i
        return routes

    def check_if_could_be_swapped(self, route1, route2, index1, index2):
        copy_route1 = route1[:]
        copy_route2 = route2[:]
        copy_route1.append(0)
        copy_route2.append(0)
        start_distance = self.calculate_route(copy_route1) + self.calculate_route(copy_route2)
        place_in_1 = copy_route1[index1]
        copy_route1[index1] = copy_route2[index2]
        copy_route2[index2] = place_in_1
        if not self.check_if_time_ok(copy_route1) or not self.check_if_time_ok(copy_route2) \
                or not self.check_if_demand_ok(copy_route1) or not self.check_if_demand_ok(copy_route2):
            return False
        return (self.calculate_route(copy_route1) + self.calculate_route(copy_route2)) < start_distance

    def check_if_time_ok(self, route):
        for i in range(1, len(route)):
            p1 = self.distances.get_place(route[i - 1])
            p2 = self.distances.get_place(route[i])
            if p1.due_time + p1.service_time > p2.ready_time:
                return False
        return True

    def check_if_demand_ok(self, route):
        demand_sum = 0
        for loc_id in route:
            demand_sum += self.distances.get_place(loc_id).demand
        return demand_sum < self.max_demand

    def calculate_route(self, route):
        route_sum = 0
        for i in range(1, len(route)):
            route_sum += self.distances.get_distance(route[i - 1], route[i])
        return route_sum


    def get_visited(self):
        return self.visited

    def get_to_visit_size(self):
        return len(self.to_visit)

    def get_to_visit(self):
        return self.to_visit

    def get_num_of_vehicles(self):
        return self.num_of_vehicles
