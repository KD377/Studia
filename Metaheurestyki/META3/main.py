import random
import item
import genetic_alg
import matplotlib.pyplot as plt
import numpy as np

def run_multiple_trials_crossover_method(items, population_size, number_of_generations, mutation_rate, crossover_rate, selection_method, crossover_methods, num_trials):
    results = {method: [] for method in crossover_methods}

    for crossover_method in crossover_methods:
        avg_solution_values = []

        for _ in range(num_trials):
            genetic_alg_instance = genetic_alg.genetic_alg(6404180)
            best = genetic_alg_instance.run_algorithm(items, population_size, number_of_generations, mutation_rate, crossover_rate, selection_method, crossover_method)

            if len(best) != len(items):
                raise ValueError("Length mismatch between 'best' and 'items'.")

            solution = [items[i] for i in range(len(items)) if best[i] == 1]
            solution_value = sum(item.value for item in solution)
            avg_solution_values.append(solution_value)

        results[crossover_method] = avg_solution_values

    return results

def plot_results_crossover_method(crossover_methods, results, title):
    plt.boxplot(list(results.values()), labels=list(map(str, crossover_methods)))
    plt.title(title)
    plt.xlabel('Crossover Method')
    plt.ylabel('Average Solution Value')
    plt.show()

items_data = [
    (1, "Toporek", 32252, 68674),
    (2, "Moneta z brązu", 225790, 471010),
    (3, "Korona", 468164, 944620),
    (4, "Diamentowy posążek", 489494, 962094),
    (5, "Szmaragdowy pas", 35384, 78344),
    (6, "Skamieliny", 265590, 579152),
    (7, "Złota moneta", 497911, 902698),
    (8, "Hełm", 800493, 1686515),
    (9, "Tusz", 823576, 1688691),
    (10, "Szkatułka", 552202, 1056157),
    (11, "Nóż", 323618, 677562),
    (12, "Długi miecz", 382846, 833132),
    (13, "Maska", 44676, 99192),
    (14, "Naszyjnik", 169738, 376418),
    (15, "Opalowa zawieszka", 610876, 1253986),
    (16, "Perły", 854190, 1853562),
    (17, "Kołczan", 671123, 1320297),
    (18, "Rubinowy pierścień", 698180, 1301637),
    (19, "Srebrna bransoletka", 446517, 859835),
    (20, "Czasomierz", 909620, 1677534),
    (21, "Mundur", 904818, 1910501),
    (22, "Trucizna", 730061, 1528646),
    (23, "Wełniany szal", 931932, 1827477),
    (24, "Kusza", 952360, 2068204),
    (25, "Stara księga", 926023, 1746556),
    (26, "Puchar z cynku", 978724, 2100851),
]

items = [item.Item(*data) for data in items_data]
population_size = 100
number_of_generations = 100
mutation_rate = 0.1
crossover_rate = 0.8

num_trials = 40
crossover_methods = [1, 2]


results_crossover_method = run_multiple_trials_crossover_method(items, population_size, number_of_generations, mutation_rate, crossover_rate, 't', crossover_methods, num_trials)


plot_results_crossover_method(crossover_methods, results_crossover_method, 'Impact of Crossover Method on Genetic Algorithm')

