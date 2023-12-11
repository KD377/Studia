import argparse
import configparser
import meadow


def load_config(file_path):
    config = configparser.ConfigParser()
    config.read(file_path)
    return config


def main(config_file=None):
    if config_file:
        config = load_config(config_file)

        sheep_init_pos_limit = float(config['Sheep']['InitPosLimit'])
        sheep_move_dist = float(config['Sheep']['MoveDist'])

        wolf_move_dist = float(config['Wolf']['MoveDist'])

        if sheep_init_pos_limit <= 0 or sheep_move_dist <= 0 or wolf_move_dist <= 0:
            raise ValueError("All values must be positive numbers")

        # Use values from the config file
        meadow_instance = meadow.Meadow(15, 50, sheep_init_pos_limit, sheep_move_dist, wolf_move_dist)
        meadow_instance.simulation()
    else:
        meadow_instance = meadow.Meadow(15, 50)
        meadow_instance.simulation()


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Simulation of wolf chasing sheeps')
    parser.add_argument('-c', '--config', help='Path to the configuration file', required=False)
    args = parser.parse_args()

    main(args.config)

