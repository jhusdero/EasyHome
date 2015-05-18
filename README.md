# EasyHome
This is an android app that plays lecture/tutorial videos/audio of Field Theory onto and HD TV.
The videos/audio are stored on the raspberry pi that connects to the TV via an HDMI cable.

The app has a three buttons on the Main Activity where the user can hoose either to watch videos or play audio or turn on/off a lamp.
Once the user has decided on what video/ audio to play, once he clicks o the video/audio, an HTTP request is sent to the server. The pi 
can then pull the request and play the required video/audio on the TV. 

The user can also use the app to turn on and off a lamp that is connected to the pi via Bluetooth (BLE).

The other cool thing about the system is that u can use NFC tech to do all the above. NFC makes it easy for the user to perform
the tasks.
 
All the user does is to bring the phone in contact with the respective NFC tag and the HTTP request is sent to the server to play 
the required vide/audio.

