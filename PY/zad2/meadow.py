import sheep
import wolf
import json
import csv


class Meadow:
    def __init__(self, number_of_sheeps, number_of_rounds, bound):
        self.starting_bound = bound
        self.number_of_rounds = number_of_rounds
        self.number_of_sheeps = number_of_sheeps
        self.herd_of_sheeps = []
        self.alive_sheeps = []
        self.json_buffer = []
        self.csv_buffer = []
        for i in range(self.number_of_sheeps):
            sheep_instance = sheep.Sheep(self.starting_bound)
            self.herd_of_sheeps.append(sheep_instance)
            self.alive_sheeps.append(sheep_instance)

        self.wolf = wolf.Wolf()

    def write_data_to_json(self, round_number):
        round_data = {
            'round_no': round_number,
            'wolf_pos': (self.wolf.x, self.wolf.y),
            'sheep_pos': [(sheep_instance.x, sheep_instance.y) if sheep_instance.alive
                          else None for sheep_instance in self.herd_of_sheeps]
        }

        self.json_buffer.append(round_data)

    def write_data_to_csv(self, round_number, number_of_alive_sheeps):
        self.csv_buffer.append({'round_number': round_number, 'number_of_alive_sheeps': number_of_alive_sheeps})

    def simulation(self):

        for round_number in range(self.number_of_rounds):
            for sheep_instance in self.herd_of_sheeps:
                if sheep_instance.alive:
                    sheep_instance.move()

            wolf_info = self.wolf.move(self.herd_of_sheeps)

            number_of_alive_sheeps = 0
            for sheep_instance in self.herd_of_sheeps:
                if sheep_instance.alive:
                    number_of_alive_sheeps += 1

            self.write_data_to_json(round_number + 1)
            self.write_data_to_csv(round_number + 1, number_of_alive_sheeps)
            if number_of_alive_sheeps == 0:
                print("All sheeps have been eaten")
                break
            else:
                print("Round: " + str(round_number + 1))
                print(wolf_info)
                print("Wolf coordinates=(" + str(round(self.wolf.x, 3)) + ", " + str(round(self.wolf.y, 3)) + ")")
                print("Number of alive sheeps: " + str(number_of_alive_sheeps))

        with open('pos.json', 'w') as file:
            json.dump(self.json_buffer, file, indent=2)

        headers = ['round_number', 'number_of_alive_sheeps']
        with open('alive.csv', 'w') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=headers)
            writer.writeheader()
            writer.writerows(self.csv_buffer)



