//#region Setting reader packages
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
//#endregion
    
public class Main { //Handle settings input, generate threads, 

    private static void core_simulation_loop(simulation simulation_obj, logging log_obj)
    {   
        for (int i = 0; true; i++)
        {
            //Clear previous previus simulation frames
            simulation_obj.canvas.clear();

            simulation_obj.randomSpeed_gen();

            simulation_obj.position_upd();

            simulation_obj.collision_hand();

            simulation_obj.simulation_draw();

            simulation_obj.canvas.update();

            try {
                Thread.sleep(simulation_obj.step_delay);
            } catch (Exception e) {
                e.printStackTrace();
            }

            logging.iteration_log(1, i);
        }
    }

    public static void main(String[] args) {
        //Program starts here
        System.out.println("Blob simulation started");

        //Initialize simuation enviornment
        
        //#region Initialize variables
        int[] canvas_res = new int[2];
        int[] canvas_pos = new int[2];
        int[] max_a_thick = new int[2];
        int[] max_a_thin = new int[2];
        int[] radii = new int[2];
        int thickCount;
        int thinCount;
        int touching_delay;
        int touched_delay;
        int step_delay;
        //#endregion

        //#region Read from json
        try {
            JSONParser parser = new JSONParser();

            Object obj = parser.parse(new FileReader("settings.json"));
    
            // A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
            JSONObject settings_jo = (JSONObject) obj;    

            //set canvas res
            int canvas_res_x = (int) (long) settings_jo.get("canvas_res_x");
            int canvas_res_y = (int) (long) settings_jo.get("canvas_res_y");
            canvas_res = new int[] {canvas_res_x, canvas_res_y};

            //set canvas_pos
            int canvas_pos_x = (int) (long) settings_jo.get("canvas_pos_x");
            int canvas_pos_y = (int) (long) settings_jo.get("canvas_pos_y");
            canvas_pos = new int[] {canvas_pos_x, canvas_pos_y};

            //set max a thick
            int max_a_thick_x = (int) (long) settings_jo.get("max_a_thick_x");
            int max_a_thick_y = (int) (long) settings_jo.get("max_a_thick_y");
            max_a_thick = new int[] {max_a_thick_x, max_a_thick_y};

            //set max a thin
            int max_a_thin_x = (int) (long) settings_jo.get("max_a_thin_x");
            int max_a_thin_y = (int) (long) settings_jo.get("max_a_thin_y");
            max_a_thin = new int[] {max_a_thin_x, max_a_thin_y};

            //set radii
            int radius_thick = (int) (long) settings_jo.get("radius_thick");
            int radius_thin = (int) (long) settings_jo.get("radius_thin");
            radii = new int[] {radius_thick, radius_thin};

            //set the rest of variables
            thickCount = (int) (long) settings_jo.get("thickCount");
            thinCount = (int) (long) settings_jo.get("thinCount");
            touching_delay = (int) (long) settings_jo.get("touching_delay");
            touched_delay = (int) (long) settings_jo.get("touched_delay");
            step_delay = (int) (long) settings_jo.get("step_delay");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("EXITING....");
            return;
        }

        //#endregion

        simulation simulation = new simulation(
            1,
            canvas_res,
            canvas_pos,
            max_a_thick,
            max_a_thin,
            radii,
            thickCount,
            thinCount,
            touching_delay,
            touched_delay,
            step_delay
        ); //Initialize simulation object
        logging log = new logging(); //init logging object

        core_simulation_loop(simulation, log);


        }
    }
    //#endregion
  