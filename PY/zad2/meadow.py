import logging
import sheep
import wolf
import json
import csv


class Meadow:
    def __init__(self, number_of_sheep=15, number_of_rounds=50, bound=10.0, sheep_movement=0.5, wolf_movement=1.0):
        self.starting_bound = bound
        self.number_of_rounds = number_of_rounds
        self.number_of_sheep = number_of_sheep
        self.sheep_movement = sheep_movement
        self.wolf_movement = wolf_movement
        self.herd_of_sheep = []
        self.alive_sheep = []
        self.json_buffer = []
        self.csv_buffer = []
        for i in range(self.number_of_sheep):
            sheep_instance = sheep.Sheep(self.starting_bound, self.sheep_movement)
            self.herd_of_sheep.append(sheep_instance)
            self.alive_sheep.append(sheep_instance)

        self.wolf = wolf.Wolf(self.wolf_movement)

    def write_data_to_json(self, round_number):
        round_data = {
            'round_no': round_number,
            'wolf_pos': (self.wolf.x, self.wolf.y),
            'sheep_pos': [(sheep_instance.x, sheep_instance.y) if sheep_instance.alive
                          else None for sheep_instance in self.herd_of_sheep]
        }

        self.json_buffer.append(round_data)
        logging.debug("Data saved to pos.json")

    def write_data_to_csv(self, round_number, number_of_alive_sheep):
        self.csv_buffer.append({'round_number': round_number, 'number_of_alive_sheep': number_of_alive_sheep})
        logging.debug("Data saved to alive.csv")

    def simulation(self, wait=False):
        for sheep_instance in self.herd_of_sheep:
            logging.debug(f"Sheep number: {self.herd_of_sheep.index(sheep_instance) + 1}, Initial "
                          f"position: X:{round(sheep_instance.x, 3)} Y:{round(sheep_instance.y, 3)}")
        logging.info("Initial positions of all sheep are determined")
        for round_number in range(self.number_of_rounds):
            logging.info(f"Round number: {round_number + 1} started")
            for sheep_instance in self.herd_of_sheep:
                if sheep_instance.alive:
                    direction = sheep_instance.move()
                    logging.debug(f"Sheep number:  {self.herd_of_sheep.index(sheep_instance) + 1}, direction: {direction}")
                    logging.debug(f"Sheep number: {self.herd_of_sheep.index(sheep_instance) + 1}, Position: X:{round(sheep_instance.x, 3)} Y:{round(sheep_instance.y, 3)}")
            logging.info("All live sheep moved")
            wolf_info = self.wolf.move(self.herd_of_sheep)

            number_of_alive_sheep = 0
            for sheep_instance in self.herd_of_sheep:
                if sheep_instance.alive:
                    number_of_alive_sheep += 1

            self.write_data_to_json(round_number + 1)
            self.write_data_to_csv(round_number + 1, number_of_alive_sheep)
            if number_of_alive_sheep == 0:
                logging.info("End of simulation - all sheep have been eaten")
                print("All sheep have been eaten")
                break
            else:
                print("Round: " + str(round_number + 1))
                print(wolf_info)
                print("Wolf coordinates=(" + str(round(self.wolf.x, 3)) + ", " + str(round(self.wolf.y, 3)) + ")")
                print("Number of alive sheep: " + str(number_of_alive_sheep))
                logging.info(f"Number of alive sheep: {number_of_alive_sheep}")
            if round_number == self.number_of_rounds - 1:
                logging.info("End of simulation - a predetermined maximum number of rounds has been reached")
            if wait:
                input("Press Enter to continue to exit...")
        with open('pos.json', 'w') as file:
            json.dump(self.json_buffer, file, indent=2)

        headers = ['round_number', 'number_of_alive_sheep']
        with open('alive.csv', 'w') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=headers)
            writer.writeheader()
            writer.writerows(self.csv_buffer)



