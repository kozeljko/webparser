# webparser

### Additional pages

The selected pages are the following two:

https://www.goodreads.com/list/show/3.Best_Science_Fiction_Fantasy_Books

https://www.goodreads.com/list/show/425.Weirdest_Books_Ever

The saved pages are located /src/main/resources/pages/books*.html Might have to do a full save later.

### Required for running
* Apache Maven
* Java 8 JDK

### Build the artifact

* ``git clone https://github.com/kozeljko/webparser.git`` (or use the source from the .zip file in the 'implementation' folder)
* ``cd webparser`` (or ``cd implementation``, if using the source code in the )
* ``mvn clean package`` (be sure to be in the same directory as the pom.xml file)

This will result in two artifacts being built in the /target folder. Use the "webparser-1.0-SNAPSHOT-jar-with-dependencies.jar"
artifact. It's a fat jar and it includes all dependencies it needs to run.
### Run the artifact

The methods are run with the following command:

``
    java -jar ./target/webparser-1.0-SNAPSHOT-jar-with-dependencies.jar <method> <fileName|filePath>
``

Possible methods are:

* regex (Runs regex method, expects a fileName that exists in the .jar itself)
* xpath (Runs xpath method, expects a fileName that exists in the .jar itself)
* regex-via-path (Runs regex method, expects a file path to the location of the page file.)
* xpath-via-path (Runs xpath method, expects a file path to the location of the page file.)

Note that both fileName and filePath must respect the limitations of the program. Only pages of type books, jewelry and 
rtv are allowed. Check the /src/main/resources/pages folder for a list of available pages to be ran with a non-path method. 