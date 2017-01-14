# freeplaneOPMLimport
A Freeplane script developed in Groovy to importan OPML file to Freeplane. 

OPML outline tags with nodes and notes are imported to Freeplane Nodes and Notes.

The script operates from the Freeplane Tools/Scripts menu and also works a Freeplane node script.

## Background
I previously developed a Python script to import OPML files to Freeplane (https://github.com/adxsoft/OPMLtoMM)
which runs outside of Freeplane. I wanted a script that would run easily inside Freeplane so I re-wrote the 
python script in Groovy and produced this groovy script.

## Pre-requisites
This script only requires that you have Freeplane 1.3.5 or higher.

## Installation
1. Open Freeplane
2. In Freeplane 'Tools' menu select 'Open user directory'
3. copy attached Freeplane script 'Import OPML to selected node.groovy' into the user directory (Tools/Open user directory)
4. Restart Freeplane

## Using the script

### Running the script from the Tools/Scripts menu
1. create new node or select an existing node in a map
2. In Freeplane 'Tools' menu select 'Scripts'
3. Choose the script 'Import OPML to selected node'
4. Choose the opml file when requested
5. OPML nodes and notes will be imported within the selected node

### Running the script from a node script
1. create new node or select an existing node in a map
2. add the following attribute to the node 'opml file' with the **full path** to the opml file e.g. /Users/adxsoft/myopmldata.opml or c:/opmlfiles/myopmldata.opml).
3. Make sure the node is selected
4. In Freeplane 'Tools' menu select 'Execute selected node scripts'
5. The script checks to see if the 'opml file' attribute is present and uses that information to find the opml file.
6. If the 'opml file' attribute is not in the selected node the user will be prompted to locate the opml file.

