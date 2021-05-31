package sim;
import nano.*;
import java.lang.Math;
import java.awt.Color;

public class simulation {

    private blob[] thickBlob_array; //Init Thick blob array
    private blob[] thinBlob_array; //Init Thin blob array

    public Canvas canvas; //Init canvas object
    public Pen pen; //Init pen object

    //#region Init simulation variables
    int simID;
    int[] canvas_res;
    int[] max_a_thick;
    int[] max_a_thin;
    int[] radii;
    int touching_delay;
    int touched_delay;
    int step_delay;
    //#endregion

    //#region Colortable
    final Color thickColorNormal = Color.RED;
    final Color thinColorNormal = Color.BLUE;
    final Color thickColorTouching = Color.YELLOW;
    final Color thinColorTouching = Color.YELLOW;
    final Color thickColorTouched = Color.PINK;
    final Color thinColorTouched = Color.CYAN;
    //#endregion

    //#region INIT
    public simulation(
        int i_simID,
        int[] i_canvas_res,
        int[] i_canvas_pos,
        int[] i_max_a_thick,
        int[] i_max_a_thin,
        int[] i_radii,
        int i_thickCount,
        int i_thinCount,
        int i_touching_delay,
        int i_touched_delay,
        int i_step_delay
    )
    {
        //#region Graphical interface
        canvas = new Canvas(i_canvas_res[0], i_canvas_res[1], i_canvas_pos[0], i_canvas_pos[1]); //Configure window
        pen = new Pen(canvas); //Configure pen
        //#endregion

        //Set simulation variables
        simID = i_simID;
        canvas_res = i_canvas_res;
        max_a_thick = i_max_a_thick;
        max_a_thin = i_max_a_thin;
        radii = i_radii;
        touching_delay = i_touching_delay;
        touched_delay = i_touched_delay;
        step_delay = i_step_delay;

        //#region Generate blob arrays
        thickBlobArray_gen(i_thickCount, i_thinCount);
        thinBlobArray_gen(i_thinCount, i_thickCount);
        //#endregion

    } 
    //#endregion

    //#region Public

    public void randomSpeed_gen() //Generate random speeds
    {
        for (int j = 0; j < (thickBlob_array.length); j++)
        {
            thickBlob_array[j].dX = thickBlob_array[j].randomSpeed(max_a_thick)[0];
            thickBlob_array[j].dY = thickBlob_array[j].randomSpeed(max_a_thick)[1];
        }

        for (int j = 0; j < (thinBlob_array.length); j++)
        {
            thinBlob_array[j].dX = thinBlob_array[j].randomSpeed(max_a_thin)[0];
            thinBlob_array[j].dY = thinBlob_array[j].randomSpeed(max_a_thin)[1];
        }
    }

    public void position_upd() //Update blob positions
    {
        for (int i = 0; i < thickBlob_array.length; i++)
        {
            if (thickBlob_array[i].blobStatus != blob.status.TOUCHING)
            {
                thickBlob_array[i].posX = thickBlob_array[i].posX + thickBlob_array[i].dX;
                thickBlob_array[i].posY = thickBlob_array[i].posY + thickBlob_array[i].dY;

                outOfBounds_logic(thickBlob_array[i]);

                if (thickBlob_array[i].blobStatus == blob.status.TOUCHED)
                {
                    thickBlob_array[i].touchedCounter--;
                }

                if (thickBlob_array[i].touchedCounter == 0)
                {
                    thickBlob_array[i].blobStatus = blob.status.NORMAL;
                }
            }
            else
            {
                if (thickBlob_array[i].touchingCounter != 0)
                {
                    thickBlob_array[i].touchingCounter--;
                }
                else
                {
                    thickBlob_array[i].blobStatus = blob.status.TOUCHED;
                }
            }

        }

        for (int i = 0; i < thinBlob_array.length; i++)
        {
            if (thinBlob_array[i].blobStatus != blob.status.TOUCHING)
            {
                thinBlob_array[i].posX = thinBlob_array[i].posX + thinBlob_array[i].dX;
                thinBlob_array[i].posY = thinBlob_array[i].posY + thinBlob_array[i].dY;

                outOfBounds_logic(thinBlob_array[i]);

                if (thinBlob_array[i].blobStatus == blob.status.TOUCHED)
                {
                    thinBlob_array[i].touchedCounter--;
                }

                if (thinBlob_array[i].touchedCounter == 0)
                {
                    thinBlob_array[i].blobStatus = blob.status.NORMAL;
                }
            }
            else
            {
                if (thinBlob_array[i].touchingCounter != 0)
                {
                    thinBlob_array[i].touchingCounter--;
                }
                else
                {
                    thinBlob_array[i].blobStatus = blob.status.TOUCHED;
                }
            }
        }
    }

    public void collision_hand() //Handle collisions
    {
        for (int i = 0; i < thickBlob_array.length; i++)
        {
            for (int j = 0; j < thinBlob_array.length; j++)
            {
                double d = distance(
                    thickBlob_array[i].posX,
                    thickBlob_array[i].posY,
                    thinBlob_array[j].posX,
                    thinBlob_array[j].posY
                );

                if (
                    (int) d <= (radii[0]+radii[1]) &&
                    thickBlob_array[i].blobStatus != blob.status.TOUCHING &&
                    thickBlob_array[i].blobStatus != blob.status.TOUCHED &&
                    thinBlob_array[j].blobStatus != blob.status.TOUCHING &&
                    thinBlob_array[j].blobStatus != blob.status.TOUCHED
                )
                {
                    //Set their status to touching
                    thickBlob_array[i].blobStatus = blob.status.TOUCHING;
                    thinBlob_array[j].blobStatus = blob.status.TOUCHING;

                    //Set their touching delay
                    thickBlob_array[i].touchingCounter = touching_delay;
                    thinBlob_array[j].touchingCounter = touching_delay;

                    //Set their touched delay
                    thickBlob_array[i].touchedCounter = touched_delay;
                    thinBlob_array[j].touchedCounter = touched_delay;
                }
            }
        }
    }

    public void simulation_draw()
    {
        for (int i = 0; i < thickBlob_array.length; i++)
        {
            switch(thickBlob_array[i].blobStatus) {
                case NORMAL:
                    pen.drawCircle(thickBlob_array[i].posX, thickBlob_array[i].posY, thickBlob_array[i].radius, thickColorNormal, true);
                    break;   
                case TOUCHING:
                    pen.drawCircle(thickBlob_array[i].posX, thickBlob_array[i].posY, thickBlob_array[i].radius, thickColorTouching, true);
                    break;
                case TOUCHED:
                    pen.drawCircle(thickBlob_array[i].posX, thickBlob_array[i].posY, thickBlob_array[i].radius, thickColorTouched, true);
                    break;
              }
        }

        for (int i = 0; i < thinBlob_array.length; i++)
        {
            switch(thinBlob_array[i].blobStatus) {
                case NORMAL:
                    pen.drawCircle(thinBlob_array[i].posX, thinBlob_array[i].posY, thinBlob_array[i].radius, thinColorNormal, true);
                    break;   
                case TOUCHING:
                    pen.drawCircle(thinBlob_array[i].posX, thinBlob_array[i].posY, thinBlob_array[i].radius, thinColorTouching, true);
                    break;
                case TOUCHED:
                    pen.drawCircle(thinBlob_array[i].posX, thinBlob_array[i].posY, thinBlob_array[i].radius, thinColorTouched, true);
                    break;
              }
        }
    }

    public void sleep()
    {
        try {
            Thread.sleep(step_delay);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //#endregion

    //#region Private
    private static double distance(int _pos1X, int  _pos1Y, int _pos2X, int _pos2Y)
    {
        return Math.sqrt(((_pos1X-_pos2X)*(_pos1X-_pos2X))+((_pos1Y - _pos2Y)*(_pos1Y-_pos2Y)));
    }

    //#region Generate Blob arrays
    private void thickBlobArray_gen(int i_size, int i_partnerCount)
    {
        thickBlob_array = new blob[i_size];

        for (int i = 0; i < i_size; i++)
        {
            blob blob = new blob(i, i_partnerCount, true, radii); //Init new blob
            blob.masterID = i + 1; //Assign id
            blob.radius = radii[0]; //Determine blob radius
            thickBlob_array[i] = blob; //Put in array

            //assign random starting positions to every blob
            blob.posX = blob.randomPosition(canvas_res)[0];
            blob.posY = blob.randomPosition(canvas_res)[1];
        }
    }

    private void thinBlobArray_gen(int i_size, int i_partnerCount)
    {
        thinBlob_array = new blob[i_size];

        for (int i = 0; i < i_size; i++)
        {
            blob blob = new blob(i, i_partnerCount, false, radii); //Init new blob
            blob.masterID = -i - 1; //Assign id
            blob.radius = radii[1]; //Determine blob radius
            thinBlob_array[i] = blob; //Put in array

            //assign random starting positions to every blob
            blob.posX = blob.randomPosition(canvas_res)[0];
            blob.posY = blob.randomPosition(canvas_res)[1];
        }        
    }
    //#endregion


    //#region Out of bouds loic
    private void outOfBounds_logic(blob i_blob)
    {
        outOfBounds_horizontal(i_blob);
        outOfBounds_vertical(i_blob);
    }

    private void outOfBounds_horizontal(blob i_blob)
    {
        if (i_blob.posX < 0)
        {
            i_blob.posX = canvas_res[0] + i_blob.posX;
        }
        else if (i_blob.posX > canvas_res[0])
        {
            i_blob.posX = i_blob.posX - canvas_res[0];
        }
    }

    private void outOfBounds_vertical(blob i_blob)
    {
        if (i_blob.posY < 0)
        {
            i_blob.posY = canvas_res[1] + i_blob.posY;
        }
        else if (i_blob.posY > canvas_res[1])
        {
            i_blob.posY = i_blob.posY - canvas_res[1];
        }
    }
    //#endregion
    
    //#endregion
    
}
