import json

standard_settings = {
    'canvas_resolution' : [720, 720],
    'max_a_thick' : [3, 3],
    'max_a_thin' : [3,3],
    'thickCount': 35,
    'thinCount' : 35,
    'radii' : [10, 5],
    'touching_delay' : 50,
    'touched_delay' : 100,
}

file = open("settings.json", "w")
file.write(json.dumps(standard_settings, indent = 4, sort_keys = False))
file.close()