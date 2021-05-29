import nano.*;  
import java.awt.Color;
import java.lang.Math;
    
public class Main {

    public static double distance(int _pos1X, int  _pos1Y, int _pos2X, int _pos2Y)
    {
        return Math.sqrt(((_pos1X-_pos2X)*(_pos1X-_pos2X))+((_pos1Y - _pos2Y)*(_pos1Y-_pos2Y)));
    }

    public static void main(String[] args) {
        //Program starts here
        System.out.println("Blob simulation started");

        //Initialize simuation enviornment

        //#regionImport setup from setup.json
        final int xSize = 720;
        final int ySize = 720;
        final int[] canvas_res = {xSize, ySize};
        final int[] max_a_thick = {3, 3};
        final int[] max_a_thin = {3, 3};
        final int thickCount = 35;
        final int thinCount = 35;
        final int thicR = 10;
        final int thinR = 5;
        final int[] radii = {thicR, thinR};
        final int touching_delay = 50;
        final int touched_delay = 100;

        //#region Colortable
        final Color thickColorNormal = Color.RED;
        final Color thinColorNormal = Color.BLUE;
        final Color thickColorTouching = Color.YELLOW;
        final Color thinColorTouching = Color.YELLOW;
        final Color thickColorTouched = Color.PINK;
        final Color thinColorTouched = Color.CYAN;
        //#endregion
        //#endregion

        Canvas simulation_canvas = new Canvas(xSize, ySize, 75, 0); //Start window
        Pen pen = new Pen(simulation_canvas); //Start pen

        //#regionInitialize all the blobs
        //2 different arrays for diffent types of blobs; 1 large major array.
        blob[] thickBlob_array = new blob[thickCount];
        blob[] thinBlob_array = new blob[thinCount];
        blob[] generalBlob_array = new blob[(thickCount+thinCount)];
        //#endregion

        //#region Generate the blob arrays and polulate them respectivley
        //#region Thick
        for (int i = 0; i < thickCount; i++)
        {
            blob blob = new blob(i, thinCount, true, radii); //new blob
            blob.masterID = i+1; //assign id
            blob.radius = thicR; //determine radius
            thickBlob_array[i] = blob; //put in thickarray

            //assign random starting positions to every blob
            blob.posX = blob.randomPosition(canvas_res)[0];
            blob.posY = blob.randomPosition(canvas_res)[1];
            //assign random starting speeds to every blob
            blob.dX = blob.randomSpeed(max_a_thick)[0];
            blob.dY = blob.randomSpeed(max_a_thick)[1];

            generalBlob_array[i] = blob; //put in general array
        }
        //#endregion
        //#region Thin
        for (int i = 0; i < thinCount; i++)
        {
            blob blob = new blob(i, thinCount, false, radii); //new blob
            blob.masterID = -i-1; //assign id
            blob.radius = thinR; //determine radius
            thinBlob_array[i] = blob; //put in thinarray

            //assign random starting positions to every blob
            blob.posX = blob.randomPosition(canvas_res)[0];
            blob.posY = blob.randomPosition(canvas_res)[1];
            //assign random starting speeds to every blob
            blob.dX = blob.randomSpeed(max_a_thin)[0];
            blob.dY = blob.randomSpeed(max_a_thin)[1];
            
            generalBlob_array[i+thickCount-1] = blob; //put in general array
        }
        //#endregion
        //#endregion

        //#region Main simulation loop
        for (int i = 0; true; i++)
        {
            //Clear previous frame
            simulation_canvas.clear();

            //#region Generate new speeds
            //thick
            for (int j = 0; j < (thickBlob_array.length); j++)
            {
                thickBlob_array[j].dX = thickBlob_array[j].randomSpeed(max_a_thick)[0];
                thickBlob_array[j].dY = thickBlob_array[j].randomSpeed(max_a_thick)[1];
            }
            //thin
            for (int j = 0; j < (thinBlob_array.length); j++)
            {
                thinBlob_array[j].dX = thinBlob_array[j].randomSpeed(max_a_thin)[0];
                thinBlob_array[j].dY = thinBlob_array[j].randomSpeed(max_a_thin)[1];
            }
            //#endregion

            //Update positions
            //thick
            for (int j = 0; j < (thickBlob_array.length); j++)
            {
                if (thickBlob_array[j].blobStatus != blob.status.TOUCHING) //if blob is not touching
                    {
                    thickBlob_array[j].posX = thickBlob_array[j].posX + thickBlob_array[j].dX; //update position horizontal
                
                    //#region Thick blob out of bounds detection horizontal
                    if (thickBlob_array[j].posX < 0)
                    {
                        thickBlob_array[j].posX = canvas_res[0] + thickBlob_array[j].posX;
                    } 
                    else if (thickBlob_array[j].posX > canvas_res[0])
                    {
                        thickBlob_array[j].posX = thickBlob_array[j].posX - canvas_res[0];
                    }
                    //#endregion

                    thickBlob_array[j].posY = thickBlob_array[j].posY + thickBlob_array[j].dY; //update position verical
                    
                    //#region Thick blob out of bounds detection vertical
                    if (thickBlob_array[j].posY < 0)
                    {
                        thickBlob_array[j].posY = canvas_res[1] + thickBlob_array[j].posY;
                    } 
                    else if (thickBlob_array[j].posY > canvas_res[1])
                    {
                        thickBlob_array[j].posY = thickBlob_array[j].posY - canvas_res[1];
                    }
                    //#endregion

                    if (thickBlob_array[j].blobStatus == blob.status.TOUCHED) //Decrement touched counter
                    {
                        thickBlob_array[j].touchedCounter = thickBlob_array[j].touchedCounter - 1;
                    }

                    if (thickBlob_array[j].touchedCounter == 0) //Set Blob to normal after touched
                    {
                    thickBlob_array[j].blobStatus = blob.status.NORMAL;
                    }
                } 
                else
                {
                    if(thickBlob_array[j].touchingCounter != 0)
                    {
                        thickBlob_array[j].touchingCounter = thickBlob_array[j].touchingCounter - 1;                    } 
                    else 
                    {
                        thickBlob_array[j].blobStatus = blob.status.TOUCHED;
                    }
                }
            }
            //thin
            for (int j = 0; j < (thinBlob_array.length); j++)
            {
                if (thinBlob_array[j].blobStatus != blob.status.TOUCHING)
                {
                    thinBlob_array[j].posX = thinBlob_array[j].posX + thinBlob_array[j].dX; //Update position Horizontal
                    
                    //#region Thin blob out of bounds detection horizontal
                    if (thinBlob_array[j].posX < 0)
                    {
                        thinBlob_array[j].posX = canvas_res[0] + thinBlob_array[j].posX;
                    } 
                    else if (thinBlob_array[j].posX > canvas_res[0])
                    {
                        thinBlob_array[j].posX = thinBlob_array[j].posX - canvas_res[0];
                    }
                    //#endregion
                    
                    thinBlob_array[j].posY = thinBlob_array[j].posY + thinBlob_array[j].dY; //Update position vertical

                    //#region Thin blob out of bounds detection verical
                    if (thinBlob_array[j].posY < 0)
                    {
                        thinBlob_array[j].posY = canvas_res[1] + thinBlob_array[j].posY;
                    } 
                    else if (thinBlob_array[j].posY > canvas_res[1])
                    {
                        thinBlob_array[j].posY = thinBlob_array[j].posY - canvas_res[1];
                    }
                    //#endregion

                    if (thinBlob_array[j].blobStatus == blob.status.TOUCHED) //Decrement touched counter
                    {
                        thinBlob_array[j].touchedCounter = thinBlob_array[j].touchedCounter - 1;
                    }

                    if (thinBlob_array[j].touchedCounter == 0) //Set blob status to normal after touched
                    {
                        thinBlob_array[j].blobStatus = blob.status.NORMAL;
                    }
                }
                else
                {
                    if(thinBlob_array[j].touchingCounter != 0)
                    {
                        thinBlob_array[j].touchingCounter = thinBlob_array[j].touchingCounter - 1;
                    }
                    else
                    {
                        thinBlob_array[j].blobStatus = blob.status.TOUCHED;
                    }
                }
            }

            //#region Calculate collisions
            for (int j = 0; j < thickBlob_array.length; j++) //for every thick blob
            {
                for(int k = 0; k < thinBlob_array.length; k++) //find the properties in relation to every thin blob
                {
                    double d = distance(
                    thickBlob_array[j].posX,
                    thickBlob_array[j].posY,
                    thinBlob_array[k].posX,
                    thinBlob_array[k].posY);

                    thickBlob_array[j].distance_array[k] = d;
                    thinBlob_array[k].distance_array[j] = d;

                    if (
                    ((int) d <= (radii[0]+radii[1])) &&
                    (thickBlob_array[j].blobStatus != blob.status.TOUCHING) &&
                    (thinBlob_array[k].blobStatus != blob.status.TOUCHING) &&
                    (thickBlob_array[j].blobStatus != blob.status.TOUCHED) &&
                    (thinBlob_array[k].blobStatus != blob.status.TOUCHED)) //If the two blobs are touching and available
                    {
                        //Set their status to touching
                        thickBlob_array[j].blobStatus = blob.status.TOUCHING;
                        thinBlob_array[k].blobStatus = blob.status.TOUCHING;

                        //Set their touching delay
                        thickBlob_array[j].touchingCounter = touching_delay;
                        thinBlob_array[k].touchingCounter = touching_delay;

                        //Set their touched delay
                        thickBlob_array[j].touchedCounter = touched_delay;
                        thinBlob_array[k].touchedCounter = touched_delay;
                    }
                }
            }

            //Draw simulation
            for (int j = 0; j < thickCount; j++) //Draw thick blobs
            {
                switch(thickBlob_array[j].blobStatus) {
                    case NORMAL:
                        pen.drawCircle(thickBlob_array[j].posX, thickBlob_array[j].posY, thickBlob_array[j].radius, thickColorNormal, true);
                        break;   
                    case TOUCHING:
                        pen.drawCircle(thickBlob_array[j].posX, thickBlob_array[j].posY, thickBlob_array[j].radius, thickColorTouching, true);
                        break;
                    case TOUCHED:
                        pen.drawCircle(thickBlob_array[j].posX, thickBlob_array[j].posY, thickBlob_array[j].radius, thickColorTouched, true);
                        break;
                  }
            }   
            for (int j = 0; j < thinCount; j++) //Draw thin blobs
            {
                switch(thinBlob_array[j].blobStatus) {
                    case NORMAL:
                        pen.drawCircle(thinBlob_array[j].posX, thinBlob_array[j].posY, thinBlob_array[j].radius, thinColorNormal, true);
                        break;   
                    case TOUCHING:
                        pen.drawCircle(thinBlob_array[j].posX, thinBlob_array[j].posY, thinBlob_array[j].radius, thinColorTouching, true);
                        break;
                    case TOUCHED:
                        pen.drawCircle(thinBlob_array[j].posX, thinBlob_array[j].posY, thinBlob_array[j].radius, thinColorTouched, true);
                    break;
                }
            }

            //Update simulation canvas after every loop
            simulation_canvas.update();

            try {
                Thread.sleep(15);
            } catch (Exception InterruptException) {
            }

            //Iteration counter
            System.out.printf("Iteration %s%n", i);

        }
    }
    //#endregion
}
  