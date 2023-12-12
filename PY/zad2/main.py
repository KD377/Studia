import argparse
import configparser
import meadow
import logging

LOG_LEVELS = {
    'DEBUG': logging.DEBUG,
    'INFO': logging.INFO,
    'WARNING': logging.WARNING,
    'ERROR': logging.ERROR,
    'CRITICAL': logging.CRITICAL
}


def load_config(file_path):
    config = configparser.ConfigParser()
    config.read(file_path)
    return config


def main(config_file=None, log_level=None, rounds=50, number_of_sheep=15, wait=False):
    if log_level:
        numeric_log_level = LOG_LEVELS.get(log_level.upper())
        logging.basicConfig(filename='chase.log', filemode='w', level=numeric_log_level,
                            format='%(levelname)s - %(message)s')

    if config_file:
        config = load_config(config_file)

        sheep_init_pos_limit = float(config['Sheep']['InitPosLimit'])
        sheep_move_dist = float(config['Sheep']['MoveDist'])

        wolf_move_dist = float(config['Wolf']['MoveDist'])

        if sheep_init_pos_limit <= 0 or sheep_move_dist <= 0 or wolf_move_dist <= 0 or rounds <= 0 or number_of_sheep <= 0:
            raise ValueError("All values must be positive numbers")
        logging.debug(
            f"Values loaded from configuration file: Sheep InitPosLimit = {sheep_init_pos_limit}, Sheep MoveDist = {sheep_move_dist}, Wolf MoveDist = {wolf_move_dist}")

        meadow_instance = meadow.Meadow(number_of_sheep, rounds, sheep_init_pos_limit, sheep_move_dist, wolf_move_dist)
        meadow_instance.simulation(wait)
    else:
        if rounds <= 0 or number_of_sheep <= 0:
            raise ValueError("All values must be positive numbers")
        meadow_instance = meadow.Meadow(number_of_sheep, rounds)
        meadow_instance.simulation(wait)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Simulation of wolf chasing sheep')
    parser.add_argument('-c', '--config', help='Path to the configuration file', required=False)
    parser.add_argument('-l', '--log', help='Log level', choices=['DEBUG', 'INFO', 'WARNING', 'ERROR', 'CRITICAL'],
                        default=None)
    parser.add_argument('-r', '--rounds', help='Specifies the maximum number of rounds', type=int, default=50,
                        required=False)
    parser.add_argument('-s', '--sheep', help='Specifies the number of sheep', type=int, default=15, required=False)
    parser.add_argument('-w', '--wait', help='Pauses simulation at the end of each round until user input',
                        action='store_true', required=False)
    args = parser.parse_args()

    main(args.config, args.log, args.rounds, args.sheep, args.wait)
