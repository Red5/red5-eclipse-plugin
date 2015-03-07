red5plugin (Forked from https://github.com/Red5/red5-eclipse-plugin )
==========

Red5 Integration for Eclipse


## Download & Build the plugin


1. Download the latest JEE eclipse (I use LUNA). 
2. Download the repository files as zip / clone the project to your machine.
3. Import the project to your eclipse : Import -> Existing projects to workspace -> Select root directory -> {browse locate the downloaded repository folder}
4. Clean project anf make sure you dont have any errors showing. Warnings are ok.


## Installing the plugin to your Eclipse IDE


![Exporting plugin files](/screenshots/screen1.jpg?raw=true "Export - Getting ready !!")

![Selecting export type](/screenshots/screen2.jpg?raw=true "Select export type")
* Select Plugin Development -> Deployable-plugins and fragments

![Selecting plugins and fragments](/screenshots/screen3.jpg?raw=true "Selecting plugins and fragments")
* Select "Install into host. Repository:"

![Plugin is installing](/screenshots/screen4.jpg?raw=true "Plugin is installing")

![Installation complete](/screenshots/screen5.jpg?raw=true "Installation complete")
* Restart eclipse once installation is complete


## Verifying installation

Once eclipse restarts verify that the plugin was successfully installed by going to:

1. File -> New -> Project -> Web -> Dynamic web project
2. Click "New Runtime" besides "Target Runtime"
3. Look for Infrared5 -> Red5 server runtime
4. If you you see it there then installation was successful

![Verifying](/screenshots/screen6.jpg?raw=true "Verifying")


## Starting Red5 Project

To get an idea of how to work with the red5 plugin and how to create a new red5 project follow the video:
https://www.youtube.com/watch?v=8Goj0yNYr08


[By Dominick Accattato]
More informational videos can be found in the channel.

