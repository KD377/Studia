import numpy as np
import matplotlib.pyplot as plt
import os
import time


def calculate_distance(point1, point2):
    return np.sqrt((point1[0] - point2[0])**2 + (point1[1] - point2[1])**2)


def generate_distance_matrix(attractions):
    n = len(attractions)
    distance_matrix = np.zeros((n, n))
    for i in range(n):
        for j in range(i, n):
            distance_matrix[i, j] = distance_matrix[j, i] = calculate_distance(attractions[i][:], attractions[j][:])
    return distance_matrix


def ant_colony(attractions, num_ants, alpha, beta, evaporation_rate, iterations, random_factor):
    num_attractions = len(attractions)
    distance_matrix = generate_distance_matrix(attractions)
    pheromones = np.ones((num_attractions, num_attractions))
    best_path = None
    best_path_length = np.inf

    for iteration in range(iterations):
        ant_paths = []
        for ant in range(num_ants):
            current_path = []
            current_city = np.random.randint(num_attractions)
            remaining_cities = set(range(num_attractions))
            remaining_cities.remove(current_city)

            for step in range(num_attractions - 1):
                if np.random.rand() < random_factor:
                    next_city = np.random.choice(list(remaining_cities))
                else:
                    probabilities = (
                        pheromones[current_city, city] ** alpha *
                        (1.0 / (distance_matrix[current_city, city] + 1e-10)) ** beta
                        for city in remaining_cities
                    )
                    probabilities = list(probabilities)
                    probabilities /= np.sum(probabilities)
                    next_city = np.random.choice(list(remaining_cities), p=probabilities)

                current_path.append((current_city, next_city))
                current_city = next_city
                remaining_cities.remove(current_city)

            ant_paths.append(current_path)

        pheromones *= (1.0 - evaporation_rate)

        for path in ant_paths:
            path_length = sum(distance_matrix[path[i][0], path[i][1]] for i in range(num_attractions - 1))
            pheromone_deposit = 1.0 / path_length
            for move in path:
                pheromones[move[0], move[1]] += pheromone_deposit
                pheromones[move[1], move[0]] += pheromone_deposit

            if path_length < best_path_length:
                best_path_length = path_length
                best_path = path

    return best_path, best_path_length


def plot_path(attractions, path, file_name, plot_description):
    x = [attractions[i][0] for i, _ in path]
    y = [attractions[i][1] for i, _ in path]

    plt.figure(figsize=(8, 8))
    plt.plot(x, y, marker='o', linestyle='-')

    # Mark the first and last attractions with red dots
    plt.plot(x[0], y[0], marker='o', color='red', markersize=8, label='Start')
    plt.plot(x[-1], y[-1], marker='o', color='red', markersize=8, label="End")

    for i, attraction in enumerate(attractions):
        plt.text(attraction[0], attraction[1], str(i), fontsize=12, ha='center', va='bottom')

    plt.text(0.5, -0.1, plot_description, transform=plt.gca().transAxes, fontsize=10, ha='center')
    plt.title("Best Path for " + file_name)
    plt.xlabel("X-coordinate")
    plt.ylabel("Y-coordinate")
    plt.grid(True)
    plt.legend()
    plt.show()


directory_path = 'attractions'
files = os.listdir(directory_path)
txt_files = [file for file in files if file.endswith('.txt')]


num_ants_values = [10, 30, 50]
alpha_values = [1, 2]
beta_values = [1, 3]
evaporation_rate_values = [0.1, 0.5]
iterations = 1000
random_factor = 0.3

for file_name in txt_files:
    file_path = os.path.join(directory_path, file_name)

    with open(file_path, 'r') as file:
        attractions = [list(map(float, line.split()[1:])) for line in file]
    print("Performing route optimization for file: "+ file_name)
    for number in num_ants_values:
        for i in range(2):
            start_time = time.time()
            best_path, best_path_length = ant_colony(attractions, number, alpha_values[i], beta_values[i],
                                                     evaporation_rate_values[i], iterations, random_factor)
            end_time = time.time()
            elapsed_time = end_time - start_time

            if elapsed_time > 60:
                minutes = int(elapsed_time // 60)
                seconds = elapsed_time % 60
                time_str = f" Time taken: {minutes} m and {seconds:.2f} s"
            else:
                time_str = f" Time taken: {elapsed_time:.2f} s"
            plot_description = "Path length: " + str(round(best_path_length, 3)) + " Number of ants: " + str(number) + " alpha: " \
                               + str(alpha_values[i]) + \
                               " beta: " + str(beta_values[i]) + " evaporation: " + str(evaporation_rate_values[i]) \
                               + time_str
            plot_path(attractions, best_path, file_name, plot_description)
            print(plot_description)
            print("Best Path:", best_path)
            print("Best Path Length:", best_path_length)


