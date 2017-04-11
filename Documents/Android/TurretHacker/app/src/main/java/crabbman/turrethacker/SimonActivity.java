package crabbman.turrethacker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.R.attr.button;
import static android.R.attr.visible;

/**
 * Created by crabbydavis on 3/28/17.
 */

public class SimonActivity extends Activity {

    // This enumerated type will be to control the flash sequence
    public enum flash_sequence_st { INIT, WAIT, VISIBLE, FINISHED }
    private flash_sequence_st currentState = flash_sequence_st.INIT;
    private int counter;
    private boolean flashDoneFlag;
    private boolean timerRunning = false;

    // These are the four buttons that wil be used in the game
    private Button smallRedButton;
    private Button smallBlueButton;
    private Button smallYellowButton;
    private Button smallGreenButton;

    private Button largeRedButton;
    private Button largeBlueButton;
    private Button largeYellowButton;
    private Button largeGreenButton;

    private Button startSequenceButton;

    private ArrayList<Button> largeButtons;
    private ArrayList<Button> buttonPushes;
    private ArrayList<Button> sequence;

    private boolean winner = false;
    private int sequenceLength = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon);

        // This will hold the buttons that we show and make disappear
        sequence = new ArrayList<>();
        buttonPushes = new ArrayList<>();

        getButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Activity simonActivity = this;
        startSequenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSequence()){
                    startActivity(new Intent(simonActivity, PlayerFrequencyActivity.class));
                }
                else{
                    startActivity(new Intent(simonActivity, MainActivity.class));
                }
            }
        });
        //try { TimeUnit.MILLISECONDS.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        /*Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        largeRedButton.setVisibility(View.VISIBLE);
                        while(true);
                    }
                });
            }
        }, 4000);*/
        /*while (currentState != flash_sequence_st.FINISHED) {
            flashSequence();
        }*/
        flashing();
        playGame();
    }

    private void flashing() {

        // Get a sequence of 5 random numbers that will coordinate to colors
        for (int i = 0; i < sequenceLength; i++) {
            Random random = new Random();
            int randomButton = random.nextInt(largeButtons.size());
            sequence.add(largeButtons.get(randomButton));
        }

        Timer flashTimer1 = new Timer();
        flashTimer1.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(0).setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 2000);

        Timer flashTimer2 = new Timer();
        flashTimer2.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(0).setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 2500);

        Timer flashTimer3 = new Timer();
        flashTimer3.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(1).setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 3000);

        Timer flashTimer4 = new Timer();
        flashTimer4.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(1).setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 3500);

        Timer flashTimer5 = new Timer();
        flashTimer5.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(2).setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 4000);

        Timer flashTimer6 = new Timer();
        flashTimer6.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(2).setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 4500);

        Timer flashTimer7 = new Timer();
        flashTimer7.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(3).setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 5000);

        Timer flashTimer8 = new Timer();
        flashTimer8.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(3).setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 5500);

        Timer flashTimer9 = new Timer();
        flashTimer9.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(4).setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 6000);

        Timer flashTimer10 = new Timer();
        flashTimer10.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        sequence.get(4).setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 6500);
    }

    private void getButtons(){

        smallRedButton = (Button) findViewById(R.id.smallRedButton);
        smallBlueButton = (Button) findViewById(R.id.smallBlueButton);
        smallYellowButton = (Button) findViewById(R.id.smallYellowButton);
        smallGreenButton = (Button) findViewById(R.id.smallGreenButton);

        largeRedButton = (Button) findViewById(R.id.largeRedButton);
        largeBlueButton = (Button) findViewById(R.id.largeBlueButton);
        largeYellowButton = (Button) findViewById(R.id.largeYellowButton);
        largeGreenButton = (Button) findViewById(R.id.largeGreenButton);

        startSequenceButton = (Button) findViewById(R.id.startSequenceButton);

        // Add the large buttons to the arrayList
        largeButtons = new ArrayList<>();
        largeButtons.add(largeRedButton);
        largeButtons.add(largeBlueButton);
        largeButtons.add(largeYellowButton);
        largeButtons.add(largeGreenButton);
    }

    private void flashSequence() {

        // State Actions
        switch(currentState){
            case INIT:
                // Initialize the counter to 0
                counter = 0;
                flashDoneFlag = false;
                // Get a sequence of 5 random numbers that will coordinate to colors
                for(int i = 0; i < sequenceLength; i++){
                    Random random = new Random();
                    int randomButton = random.nextInt(largeButtons.size());
                    sequence.add(largeButtons.get(randomButton));
                }
                break;
            case VISIBLE:
                // Set the current button to be visible
                sequence.get(counter).setVisibility(View.VISIBLE);
                //largeRedButton.setVisibility(View.VISIBLE);
                break;
            case WAIT:
                // Wait for 1/2 second before making the big button invisible
                if(!timerRunning) {
                    timerRunning = true;
                    try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                    flashDoneFlag = true;
                    timerRunning = false;
                    /*Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    flashDoneFlag = true;
                                    timerRunning = false;
                                }
                            });
                        }
                    }, 500);*/
                }
                break;
            case FINISHED:
                break;
        }

        // State Updates
        switch(currentState){
            case INIT:
                currentState = flash_sequence_st.VISIBLE;
                break;
            case VISIBLE:
                currentState = flash_sequence_st.WAIT;
                break;
            case WAIT:
                // If the flash is done, increment the counter and go to the visible state
                if(flashDoneFlag){
                    sequence.get(counter).setVisibility(View.INVISIBLE);
                    counter++;
                    if(counter == sequenceLength){
                        currentState = flash_sequence_st.FINISHED;
                    }
                    else {
                        currentState = flash_sequence_st.VISIBLE;
                    }
                }
                break;
            case FINISHED:
                break;
        }
                 /*Timer buttonTimer = new Timer();
            buttonTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            tempButton.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }, 500);*/
            //try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

            //Try TimeUnit.MILLISECONDS.sleep(500
    }

    private void playGame(){


        smallRedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPushes.add(smallRedButton);
                largeRedButton.setVisibility(View.VISIBLE);
                smallRedButton.setEnabled(false);
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                largeRedButton.setVisibility(View.INVISIBLE);
                                smallRedButton.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });
        smallBlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPushes.add(smallBlueButton);
                largeBlueButton.setVisibility(View.VISIBLE);
                smallBlueButton.setEnabled(false);

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                largeBlueButton.setVisibility(View.INVISIBLE);
                                smallBlueButton.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });
        smallYellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPushes.add(smallYellowButton);
                largeYellowButton.setVisibility(View.VISIBLE);
                smallYellowButton.setEnabled(false);

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                largeYellowButton.setVisibility(View.INVISIBLE);
                                smallYellowButton.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });
        smallGreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPushes.add(smallGreenButton);
                largeGreenButton.setVisibility(View.VISIBLE);
                smallGreenButton.setEnabled(false);

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                largeGreenButton.setVisibility(View.INVISIBLE);
                                smallGreenButton.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });
    }

    private boolean checkSequence() {

        for(int i = 0; i < sequenceLength; i++){
            if(!sequence.get(i).equals(buttonPushes.get(i))){
                return false;
            }
        }
        return true;
    }
    /*private void initialize_display(){

        // Get the screen width of height of current screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        // Initialize the button sizes. This number can be used for the width and height
        int smallButtonSize = width / 8;

        // Get 1/4 of the width and height for the center of the buttons
        int quarterWidth = width / 4;
        int quarterHeight = height / 4;
        // Set the initial coordinates for the small red button
        redButton.setLeft(quarterWidth - (smallButtonSize / 2));
        redButton.setTop(quarterHeight - (smallButtonSize / 2));
        redButton.setX(quarterWidth - (smallButtonSize / 2));
        redButton.setY(quarterHeight - (smallButtonSize / 2));
        redButton.setBackgroundColor(getResources().getColor(R.color.red));
        RelativeLayout.LayoutParams redButtonLayoutParams = //redButton.getLayoutParams();
        redButtonLayoutParams.height = smallButtonSize;
        redButtonLayoutParams.width = smallButtonSize;
        redButton.setLayoutParams(redButtonLayoutParams);

        // Set the initial coordinates for the small blue button
        blueButton.setLeft(quarterWidth - (smallButtonSize / 2));
        blueButton.setTop(quarterHeight - (smallButtonSize / 2));
        blueButton.setBackgroundColor(getResources().getColor(R.color.blue));

        // Set the initial coordinates for the small yellow button
        yellowButton.setLeft(quarterWidth - (smallButtonSize / 2));
        yellowButton.setTop(quarterHeight - (smallButtonSize / 2));
        yellowButton.setBackgroundColor(getResources().getColor(R.color.yellow));

        // Set the initial coordinates for the small green button
        greenButton.setLeft(quarterWidth - (smallButtonSize / 2));
        greenButton.setTop(quarterHeight - (smallButtonSize / 2));
        greenButton.setBackgroundColor(getResources().getColor(R.color.green));

    }*/
}
