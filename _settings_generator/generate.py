import json

standard_settings = {
    'canvas_res_x' : 720,
    'canvas_res_y' : 720,
    'canvas_pos_x' : 75,
    'canvas_pos_y' : 0,
    'max_a_thick_x' : 3,
    'max_a_thick_y' : 3,
    'max_a_thin_x' : 3,
    'max_a_thin_y' : 3,
    'thickCount': 35,
    'thinCount': 35,
    'radius_thick' : 10,
    'radius_thin': 5,
    'touching_delay': 100,
    'touched_delay': 50,
    'step_delay': 15
}

file = open("settings.json", "w")
file.write(json.dumps(standard_settings, indent = 4, sort_keys = False))
file.close()