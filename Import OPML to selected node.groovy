/*
Freeplane Script; Import OPML to currently selected node
V1.0 12th Jan 2017

Installation
1. Open Freeplane
2. In Freeplane 'Tools' menu select 'Open user directory'
3. copy this Freeplane script into the user directory
4. Restart Freeplane

To Use Script
1. create new node or select an existing node
2. In Freeplane 'Tools' menu select 'Scripts'
3. Choose the script 'Import OPML to selected node'
4. Choose the opml file when requested
5. OPML nodes and notes will be imported within the selected node

Note. Before running this script you can add an attribute to the selected note
called 'opml file'.

The 'opml file' attribute value should have a valid full path to the
opml file (eg /Users/adxsoft/myopmldata.opml or c:/opmlfiles/myopmldata.opml).

The script checks to see if the 'opml file' attribute is present and uses
that information to find the opml file.

If the 'opml file' attribute is not in the selected node the user will
be prompted to locate the opml file.

*/
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import static javax.xml.stream.XMLInputFactory.newInstance as staxFactory
import javax.xml.stream.XMLStreamReader as StaxReader
import java.io.File as File
import groovy.xml.dom.DOMCategory

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

import static java.nio.charset.StandardCharsets.*;

def validfile=false
if (node['opml file'].text != null) {
    def inputfile = new File(node['opml file'].text)
    if (inputfile.exists()) {
        opmlfiledata=inputfile.text
        validfile=true
    } else {
        c.statusInfo='file in attribute "opml file" > '+inputfile+' not found - script cancelled by user'
        return
    }
} else {
    chooser = new JFileChooser()
    chooser.setDialogTitle('Import OPML into selected node')
    FileNameExtensionFilter filter = new FileNameExtensionFilter("OPML files", "opml")
    chooser.setFileFilter(filter);
    returnVal = chooser.showOpenDialog();
    if (returnVal == JFileChooser.APPROVE_OPTION) {
        c.statusInfo='User has selected file '+chooser.selectedFile
        opmlfilename=chooser.selectedFile.toString()
        def inputfile = new File(opmlfilename)
        if (inputfile.exists()) {
            opmlfiledata = inputfile.text
            validfile = true
        } else {
            c.statusInfo='file in attribute "opml file" > '+inputfile+' not found - script cancelled by user'
            return
        }
    } else {
        c.statusInfo='"Import OPML into selected node" script cancelled by user'
        return
    }
}

if (!validfile) return

def outlines = []
def outline
def seenTag

opmlreader = new StringReader(opmlfiledata)

StaxReader.metaClass.attr = {s -> delegate.getAttributeValue(null, s)}
def reader = staxFactory().createXMLStreamReader(opmlreader)
def name=''
def depth=0
def lastoutlinedetails=[:]
def currentnodes=[:]
def lastdepth=0
def lastoutlinetitle='top'
lastoutlinedetails[depth]=['top',-1]
currentnodes[depth]=node
currentnode=node
while (reader.hasNext()) {
    if (reader.startElement) {
        name = reader.localName
        
        // found outline tag in opml file
        if (name == 'outline') {
            
            
            title = reader.attr('text')
            note = reader.attr('_note')

            outline = [text:reader.attr('text')]
            if (depth == lastdepth) {
                println '--'*depth+'add child '+title+' to parent '+lastoutlinedetails[depth][0]
                newnode=currentnode.createChild(title)
                if (note != null) {
                    def escapednote = note.replaceAll(/&lt;/, '<')
                             .replaceAll(/&gt;/, '>')
                             .replaceAll(/&quot;/, '"')
                             .replaceAll(/&apos;/, "'")
                             .replaceAll(/&amp;/, '&')
                    newnode.noteText=escapednote // handles utf-8 ok
                }
                currentnode=newnode
            } else {
                println '  - creating SIBLING '+title+' of node '+lastoutlinedetails[depth][0]
                newnode=currentnodes[depth].createChild(title)
                if (note != null) {
                    def escapednote = note.replaceAll(/&lt;/, '<')
                             .replaceAll(/&gt;/, '>')
                             .replaceAll(/&quot;/, '"')
                             .replaceAll(/&apos;/, "'")
                             .replaceAll(/&amp;/, '&')
                    newnode.noteText=escapednote // handles utf-8 ok
                }
                currentnode=newnode
            }
            depth+=1
            lastoutlinedetails[depth]=[title,note]
            currentnodes[depth]=currentnode
            lastdepth=depth
            lastoutlinetitle=title
        }
        else if (name in ['note','to']) seenTag=name
    } else if (reader.characters) {
        if (seenTag) outline[seenTag] = reader.text
    } else if (reader.endElement) {
        name = reader.localName
        if (name == 'outline') {
            outlines +=outline
            depth-=1
            title = lastoutlinedetails[depth][0]
            note = lastoutlinedetails[depth][1]
            seenTag = null
        }
    }
    reader.next()
}
c.statusInfo='Imported OPML data into node "'+node.text+'"'
