import json

standard_settings = {
    'canvas_res_x' : 720,
    'canvas_res_y' : 720,
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
    'step_delay': 15,
    'enable_chance': True,
    'bothWin' : True,
    'p_thick_grab' : 10.0,
    'p_thick_go' : 1.0,
    'p_thin_grab' : 10.0,
    'p_thin_go' : 1.0 
}

file = open("settings.json", "w")
file.write(json.dumps(standard_settings, indent = 4, sort_keys = False))
file.close()