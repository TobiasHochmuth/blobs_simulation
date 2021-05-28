    import nano.*;  
    import java.awt.Color;
    
    public class Main {
    public static void main(String[] args) {
        //Program starts here
        System.out.println("Blob simulation started");
        
        //Initialize simuation enviornment

        //Import setup from setup.json
        final int xSize = 720;
        final int ySize = 720;
        final int[] canvas_res = {xSize, ySize};
        final int[] max_a = {3, 3};
        final int thickCount = 20;
        final int thinCount = 20;
        final int thicR = 10;
        final int thinR = 5;


        //Start window
        Canvas simulation_canvas = new Canvas(xSize, ySize, 75, 0);
        //Start pen
        Pen pen = new Pen(simulation_canvas);


        //Initialize all the blobs
        //2 different arrays for diffent types of blobs; 1 large major array.
        blob[] thickBlob_array = new blob[thickCount];
        blob[] thinBlob_array = new blob[thinCount];
        blob[] generalBlob_array = new blob[(thickCount+thinCount)];

        //generate the blob arrays and polulate them respectivley
        //thick
        for (int i = 0; i < thickCount; i++)
        {
            blob blob = new blob(i, thinCount, true); //new blob
            blob.masterID = i+1; //assign id
            blob.radius = thicR; //determine radius
            thickBlob_array[i] = blob; //put in thickarray

            //assign random starting positions to every blob
            blob.posX = blob.randomPosition(canvas_res)[0];
            blob.posY = blob.randomPosition(canvas_res)[1];
            //assign random starting speeds to every blob
            blob.dX = blob.randomSpeed(max_a)[0];
            blob.dY = blob.randomSpeed(max_a)[1];

            generalBlob_array[i] = blob; //put in general array
        }
        //thin
        for (int i = 0; i < thinCount; i++)
        {
            blob blob = new blob(i, thickCount, true); //new blob
            blob.masterID = -i-1; //assign id
            blob.radius = thinR; //determine radius
            thinBlob_array[i] = blob; //put in thinarray

            //assign random starting positions to every blob
            blob.posX = blob.randomPosition(canvas_res)[0];
            blob.posY = blob.randomPosition(canvas_res)[1];
            //assign random starting speeds to every blob
            blob.dX = blob.randomSpeed(max_a)[0];
            blob.dY = blob.randomSpeed(max_a)[1];
            
            generalBlob_array[i+thickCount-1] = blob; //put in general array
        }


        
        //Main simulation loop
        for (int i = 0; true; i++)
        {
            //Clear previous frame
            simulation_canvas.clear();

            //Generate new speeds
            //thick
            for (int j = 0; j < (thickBlob_array.length); j++)
            {
                thickBlob_array[j].dX = thickBlob_array[j].randomSpeed(max_a)[0];
                thickBlob_array[j].dY = thickBlob_array[j].randomSpeed(max_a)[1];
            }
            //thin
            for (int j = 0; j < (thinBlob_array.length); j++)
            {
                thinBlob_array[j].dX = thinBlob_array[j].randomSpeed(max_a)[0];
                thinBlob_array[j].dY = thinBlob_array[j].randomSpeed(max_a)[1];
            }


            //Update positions
            //thick
            for (int j = 0; j < (thickBlob_array.length); j++)
            {
                thickBlob_array[j].posX = thickBlob_array[j].posX + thickBlob_array[j].dX;
                
                //chech for out of bounds
                if (thickBlob_array[j].posX < 0)
                {
                    thickBlob_array[j].posX = canvas_res[0] + thickBlob_array[j].posX;
                } 
                else if (thickBlob_array[j].posX > canvas_res[0])
                {
                    thickBlob_array[j].posX = thickBlob_array[j].posX - canvas_res[0];
                }
                
                thickBlob_array[j].posY = thickBlob_array[j].posY + thickBlob_array[j].dY;
                
                //chech for out of bounds
                if (thickBlob_array[j].posY < 0)
                {
                    thickBlob_array[j].posY = canvas_res[1] + thickBlob_array[j].posY;
                } 
                else if (thickBlob_array[j].posY > canvas_res[1])
                {
                    thickBlob_array[j].posY = thickBlob_array[j].posY - canvas_res[1];
                }
            }
            //thin
            for (int j = 0; j < (thinBlob_array.length); j++)
            {
                thinBlob_array[j].posX = thinBlob_array[j].posX + thinBlob_array[j].dX;
                
                //chech for out of bounds
                if (thinBlob_array[j].posX < 0)
                {
                    thinBlob_array[j].posX = canvas_res[0] + thinBlob_array[j].posX;
                } 
                else if (thinBlob_array[j].posX > canvas_res[0])
                {
                    thinBlob_array[j].posX = thinBlob_array[j].posX - canvas_res[0];
                }
                
                thinBlob_array[j].posY = thinBlob_array[j].posY + thinBlob_array[j].dY;

                //chech for out of bounds
                if (thinBlob_array[j].posY < 0)
                {
                    thinBlob_array[j].posY = canvas_res[1] + thinBlob_array[j].posY;
                } 
                else if (thinBlob_array[j].posY > canvas_res[1])
                {
                    thinBlob_array[j].posY = thinBlob_array[j].posY - canvas_res[1];
                }
            }


            //Iteration counter
            System.out.printf("Iteration %s%n", i);

            //Draw simulation
            for (int j = 0; j < thickCount; j++)
            {
                pen.drawCircle(thickBlob_array[j].posX, thickBlob_array[j].posY, thickBlob_array[j].radius, Color.RED, true);
            }
            for (int j = 0; j < thinCount; j++)
            {
                pen.drawCircle(thinBlob_array[j].posX, thinBlob_array[j].posY, thinBlob_array[j].radius, Color.BLUE, true);
            }

            //Update simulation canvas after every loop
            simulation_canvas.update();

            try {
                Thread.sleep(10);
            } catch (Exception InterrupException) {
            }
        }
    }
  }
  