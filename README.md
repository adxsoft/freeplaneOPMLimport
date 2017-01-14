# freeplaneOPMLimport

A Freeplane script developed in Groovy to import an OPML file to Freeplane. 

OPML is a popular export format for mind maps and OPML has nodes recorded in outline tags. 

Within each outline tag is 
 - a text attribute which is the node title
 - a _note attribute which is the note related to the node. This may or may not be present

This script will import the nodes and their related notes into Freemind nodes and notes.

The script can be copied into the Freeplane user directory and/or can be added to a Freeplanenode as a node script.

## Background
I previously developed a Python script to import OPML files to Freeplane (https://github.com/adxsoft/OPMLtoMM)
which runs outside of Freeplane. I wanted a script that would run easily inside Freeplane so I re-wrote the 
python script in Groovy and produced this groovy script.

## Pre-requisites
This script only requires that you have Freeplane 1.3.5 or higher.

## Installation
Open Freeplane

### Install in the general scripts menu
1. In Freeplane 'Tools' menu select 'Open user directory'
2. copy attached Freeplane script 'Import OPML to selected node.groovy' into the user directory (Tools/Open user directory)
3. Restart Freeplane

### Install into a specific node as a node script
1. Copy the script contents to the clipboard (Ctrl C Windows, Cmd C Mac)
2. Select the node you wish to install the script into
3. Choose menu 'Tools/Edit Sscript' to edit the scripts specific to this node
4. Choose Action/New script
5. Paste the script from the clipboard  (Ctrl V Windows, Cmd V Mac)
6. Choose Action/Save and Exit
7. Restart Freeplane

## Using the script

### Running the script from the Tools/Scripts menu
1. create new node or select an existing node in a map
2. In Freeplane 'Tools' menu select 'Scripts'
3. Choose the script 'Import_OPML_to_selected node'
4. Choose the opml file when requested
   Note. 
       if the selected node has an attribute called 'opml file' with 
       the value set to the full path of the opml file that file will
       be imported.
       if the selected node has no attribute 'opml file' you will be prompted for the files location
5. OPML nodes and notes will be imported within the selected node

### Running the script from a node script
1. create new node or select an existing node in a map
2. add the following attribute to the node 'opml file' with the **full path** to the opml file e.g. /Users/adxsoft/myopmldata.opml or c:/opmlfiles/myopmldata.opml).
3. Make sure the node is selected
4. In Freeplane 'Tools' menu select 'Execute selected node scripts'
5. The script checks to see if the 'opml file' attribute is present and uses that information to find the opml file.
6. If the 'opml file' attribute is not in the selected node the user will be prompted to locate the opml file.

I hope you find this script useful.
