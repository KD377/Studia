import random
import item

class genetic_alg:

    def __init__(self, max_weight):
        self.max_weight = max_weight

    def initialize_population(self, population_size, items):
        population = []
        for _ in range(population_size):
            chromosome = [random.randint(0, 1) for _ in range(len(items))]
            population.append(chromosome)
        return population


    def calculate_fitness(self,chromosome, items):
        total_weight = 0
        total_value = 0

        for i in range(len(chromosome)):
            if chromosome[i] == 1:
                total_weight += items[i].weight
                total_value += items[i].value

        if total_weight > self.max_weight:
            return 0
        else:
            return total_value

    def roulette_selection(self, population, items):
        fitness_values = [self.calculate_fitness(chromosome, items) for chromosome in population]
        total_fitness = sum(fitness_values)

        probability = [fitness / total_fitness if fitness != 0 else 0.1 for fitness in fitness_values]

        return random.choices(population, probability, k=1)[0]

    def tournament_selection(self,population,items):
        parents = random.choices(population, k=5)
        parents = sorted(parents, key=lambda x: self.calculate_fitness(x, items), reverse=True)
        return parents[0], parents[1]

    def reverse_roulette_selection(self, population, items):
        fitness_values = [self.calculate_fitness(chromosome, items) for chromosome in population]
        total_fitness = sum(fitness_values)

        probability = [fitness / total_fitness if fitness != 0 else 0.1 for fitness in fitness_values]
        reverse_probability = [1 / prob if prob != 0 else 0 for prob in probability]
        return random.choices(population, reverse_probability)[0]


    def crossover(self, parent1,parent2):
        crossover_point = random.randint(1, len(parent1) - 1)
        child1 = parent1[:crossover_point] + parent2[crossover_point:]
        child2 = parent2[:crossover_point] + parent1[crossover_point:]
        return child1, child2

    def crossover_2(self, parent1, parent2):
        crossover_point1 = random.randint(1, len(parent1) - 1)
        crossover_point2 = random.randint(crossover_point1, len(parent1) - 1)

        child1 = parent1[:crossover_point1] + parent2[crossover_point1:crossover_point2] + parent1[crossover_point2:]
        child2 = parent2[:crossover_point1] + parent1[crossover_point1:crossover_point2] + parent2[crossover_point2:]

        return child1, child2

    def mutate(self, chromosome):
        mutated_chromosome = chromosome[:]
        rand_index = random.randint(0, 25)
        if mutated_chromosome[rand_index] == 1:
            mutated_chromosome[rand_index] = 0
        else:
            mutated_chromosome[rand_index] = 1
        return mutated_chromosome

    def run_algorithm(self, items, population_size, number_of_generations, mutation_rate, crossover_rate, selection_method, crossover_method):
        population = self.initialize_population(population_size, items)

        for generation in range(number_of_generations):
            if selection_method == "t":
                parent1, parent2 = self.tournament_selection(population,items)
            elif selection_method == "r":
                parent1 = self.roulette_selection(population, items)
                parent2 = self.roulette_selection(population, items)
            else:
                raise ValueError("Wrong selection_method")

            if random.uniform(0, 1) < crossover_rate:
                if crossover_method == 1:
                    child1, child2 = self.crossover(parent1, parent2)
                elif crossover_method == 2:
                    child1, child2 = self.crossover_2(parent1, parent2)
                else:
                    raise ValueError("Wrong parameter- Crossover_method")
            else:
                child1, child2 = parent1, parent2

            if random.uniform(0, 1) < mutation_rate:
                child1 = self.mutate(child1)
            if random.uniform(0, 1) < mutation_rate:
                child2 = self.mutate(child2)

            population.remove(self.reverse_roulette_selection(population, items))
            population.remove(self.reverse_roulette_selection(population, items))
            population.append(child1)
            population.append(child2)

        best = max(population, key=lambda x: self.calculate_fitness(x, items))
        return best





