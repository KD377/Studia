from algorithm import AntAlgorithm
from places import Place
import csv

def run_ant_algorithm(filename):
    places = load_places_from_csv(filename)
    num_of_ants = 10
    num_of_iterations = 100
    feromon_evaporation = 0.2
    feromon_weight = 1
    heuristic_weight = 1
    random_choice_chance = 0.2
    vehicle_capacity = 200

    aco = AntAlgorithm(places, num_of_ants, num_of_iterations, feromon_evaporation,
                       feromon_weight, heuristic_weight, random_choice_chance, vehicle_capacity)

    results = aco.start()

    # Display results
    print("Shortest route:", results["ShortestRoute"])
    print("Number of vehicles:", results["NumOfVehicles"])

def load_places_from_csv(filename):
    places = []

    with open(filename, newline='') as csvfile:
        csvreader = csv.reader(csvfile, delimiter=' ')
        next(csvreader)  # Skip header in the CSV file

        for row in csvreader:
            row = [elem for elem in row if elem]
            id, x, y, demand, ready_time, due_date, service_time = row
            place = Place(int(id), x, y, demand, ready_time, due_date, service_time)
            places.append(place)

    return places


filename = "R101.txt"
run_ant_algorithm(filename)
