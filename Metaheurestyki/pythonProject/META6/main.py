from algorithm import AntAlgorithm
from places import Place
import csv

def run_ant_algorithm(filename, iteration):
    places = load_places_from_csv(filename)
    num_of_ants = 10
    vehicle_capacity = 1000
    num_of_iterations = 100
    feromon_evaporation = 0.2
    feromon_weight = 1
    heuristic_weight = 1
    random_choice_chance = 0.2

    if iteration == 1:
        num_of_iterations = 100
        feromon_evaporation = 0.2
        feromon_weight = 0.3
        heuristic_weight = 0.7
        random_choice_chance = 0.2
    elif iteration == 2:
        num_of_iterations = 100
        feromon_evaporation = 0.2
        feromon_weight = 0.5
        heuristic_weight = 1
        random_choice_chance = 0.2
    elif iteration == 3:
        num_of_iterations = 100
        feromon_evaporation = 0.2
        feromon_weight = 1
        heuristic_weight = 1
        random_choice_chance = 0.5
    elif iteration == 4:
        num_of_iterations = 100
        feromon_evaporation = 0.2
        feromon_weight = 0.7
        heuristic_weight = 0.3
        random_choice_chance = 0.5

    aco = AntAlgorithm(places, num_of_ants, num_of_iterations, feromon_evaporation,
                       feromon_weight, heuristic_weight, random_choice_chance, vehicle_capacity)

    results = aco.start()

    if(iteration == 1):
        print(f"{'Iteration':<10}{'Feromon Weight':<15}{'Heuristic Weight':<18}{'Random Choice Chance':<25}{'Vehicle Capacity':<20}{'Shortest Route':<20}{'Number of Vehicles':<20}")
    print(f"{iteration:<10}{feromon_weight:<15}{heuristic_weight:<18}{random_choice_chance:<25}{vehicle_capacity:<20}{round(results['ShortestRoute'],2):<20}{results['NumOfVehicles']:<20}")

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

paths = ["RC205.txt","RC206.txt","RC207.txt"]

for path in paths:
    print("Wyniki dla pliku: " + path)
    for iteration in range(1, 6):
        run_ant_algorithm(path, iteration)
