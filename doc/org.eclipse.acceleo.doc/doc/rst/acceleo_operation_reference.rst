=============================
 Acceleo Operations Reference
=============================

:Authors: Laurent Goubet
:Contact: laurent.goubet@obeo.fr

Copyright |copy| 2009, Obeo\ |trade|.

.. |copy| unicode:: 0xA9 
.. |trade| unicode:: U+2122
.. |invalid| unicode:: U+00D8
.. |pipe| unicode:: U+007C
.. contents:: Contents



Acceleo standard operations reference
=====================================

Standard *String* Operations
----------------------------

 **substitute (String r, String t ) : String**
   Substitutes substring *r* in *self* by substring *t* and returns the resulting string. Will return *self*
   if it contains no occurrence of the substring *r*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'substitute operation'.substitute('t', 'T')                 | 'subsTiTuTe operaTion'     |
   +-------------------------------------------------------------+----------------------------+

 **index (String r) : Integer**
   Returns the index of substring *r* in *self*, or -1 if *self* contains no occurence of *r*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'index operation'.index('op')                               | 7                          |
   +-------------------------------------------------------------+----------------------------+

 **first (Integer n) : String**
   Returns the first *n* characters of *self*, or *self* if its size is less than *n*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'first operation'.first(8)                                  | 'first op'                 |
   +-------------------------------------------------------------+----------------------------+

 **last (Integer n) : String**
   Returns the last *n* characters of *self*, or *self* if its size is less than *n*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'first operation'.last(8)                                   | 'peration'                 |
   +-------------------------------------------------------------+----------------------------+

 **strstr (String r) : Boolean**
   Searches for string *r* in *self*. Returns **true** if found, **false** otherwise.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'strstr operation'.strstr('ope')                            | true                       |
   +-------------------------------------------------------------+----------------------------+
   | 'strstr operation'.strstr('false')                          | false                      |
   +-------------------------------------------------------------+----------------------------+

 **strtok (String r, Integer n) : String**
   Breaks *self* into a sequence of tokens, each of which delimited by any one of the characters in *s1*, and
   return the next element in this sequence. The parameter flag should be *0* when strtok is called for the
   first time and will reset the sequence, *1* subsequently so as to access the next element.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'strtok operation'.strtok('opz', 0)                         | 'strt'                     |
   +-------------------------------------------------------------+----------------------------+
   | 'strtok operation'.strtok('pn', 0)                          | 'strtok o'                 |
   +-------------------------------------------------------------+----------------------------+

 **strcmp (String s1) : Integer**
   Returns an integer that is either negative, zero or positive depending on whether *s1* is alphabetically less than,
   equal to or greater than *self*. Note that uppercase letters come before lower case ones, so that 'AA' is closer to
   'AC' than it is to 'Ab'.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'strcmp operation'.strstr('strcmp')                         | 10                         |
   +-------------------------------------------------------------+----------------------------+
   | 'strcmp operation'.strstr('strcmp operation')               | 0                          |
   +-------------------------------------------------------------+----------------------------+
   | 'strcmp operation'.strstr('strtok')                         | -17                        |
   +-------------------------------------------------------------+----------------------------+

 **isAlpha () : Boolean**
   Returns **true** if *self* consists only of alphabetical characters, **false** otherwise.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'isAlpha'.isAlpha()                                         | true                       |
   +-------------------------------------------------------------+----------------------------+
   | 'isAlpha operation'.isAlpha()                               | false                      |
   +-------------------------------------------------------------+----------------------------+
   | 'isAlpha11'.isAlpha()                                       | false                      |
   +-------------------------------------------------------------+----------------------------+

 **isAlphanum () : Boolean**
   Returns **true** if *self* consists only of alphanumeric characters, **false** otherwise.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'isAlphanum'.isAlphanum()                                   | true                       |
   +-------------------------------------------------------------+----------------------------+
   | 'isAlphanum operation'.isAlphanum()                         | false                      |
   +-------------------------------------------------------------+----------------------------+
   | 'isAlphanum11'.isAlphanum()                                 | true                       |
   +-------------------------------------------------------------+----------------------------+

 **toUpperFirst () : String**
   Creates a copy of *self* with its first character converted to uppercase and returns it.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'toUpperFirst operation'.toUpperFirst()                     | 'ToUpperFirst operation'   |
   +-------------------------------------------------------------+----------------------------+

 **toLowerFirst () : String**
   Creates a copy of *self* with its first character converted to lowercase and returns it.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'ToLowerFirst operation'.toLowerFirst()                     | 'toLowerFirst operation'   |
   +-------------------------------------------------------------+----------------------------+

Standard *Integer* operations
-----------------------------

 **toString () : String**
   Converts the integer *self* to a string.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 2009.toString()                                             | '2009'                     |
   +-------------------------------------------------------------+----------------------------+

Standard *Real* operations
--------------------------

 **toString () : String**
   Converts the real *self* to a string.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | (-5.3).toString()                                           | '-5.3'                     |
   +-------------------------------------------------------------+----------------------------+

Acceleo non standard operations reference
=========================================

Non-standard *String* operations
--------------------------------

 **replace (String substring, String replacement) : String**
   Substitutes the first occurence of substring *substring* in *self* by substring *replacement* and returns the
   resulting string. Returns *self* if it contains no occurence of *substring*. Note that both *substring* and
   *replacement* are treated as regular expressions.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'replace operation'.replace('p', 'P')                       | 'rePlace operation'        |
   +-------------------------------------------------------------+----------------------------+

 **replaceAll (String substring, String replacement) : String**
   Substitutes all substrings *substring* in *self* by substring *replacement* and returns the resulting string.
   Returns *self* if it contains no occurence of *substring*. Note that both *substring* and *replacement* are
   treated as regular expressions.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'replaceAll operation'.replaceAll('p', 'P')                 | 'rePlaceAll oPeration'     |
   +-------------------------------------------------------------+----------------------------+

 **substituteAll (String substring, String replacement) : String**
   Substitutes all substrings *substring* in self by substring *replacement* and returns the resulting string.
   Returns *self* if it contains no occurence of *substring*. Unlike the **replaceAll** operation, neither
   *substring* nor *replacement* are considered as regular expressions.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'substituteAll operation'.substituteAll('t', 'T')           | 'subsTiTuTeAll operaTion'  |
   +-------------------------------------------------------------+----------------------------+

 **startsWith (String substring) : Boolean**
   Returns **true** if *self* starts with the substring *substring*, **false** otherwise.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'startsWith operation'.startsWith('star')                   | true                       |
   +-------------------------------------------------------------+----------------------------+
   | 'startsWith operation'.startsWith('ope')                    | false                      |
   +-------------------------------------------------------------+----------------------------+

 **endsWith (String substring) : Boolean**
   Returns **true** if *self* ends in the substring *substring*, **false** otherwise.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'endsWith operation'.endsWith('ation')                      | true                       |
   +-------------------------------------------------------------+----------------------------+
   | 'endsWith operation'.endsWith('endsWith')                   | false                      |
   +-------------------------------------------------------------+----------------------------+

 **trim () : String**
   Removes all leading and trailing white space characters (tabulation, space, line feed, ...) of *self*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | ' trim operation '.trim()                                   | 'trim operation'           |
   +-------------------------------------------------------------+----------------------------+

 **tokenize (String substring) : Sequence(String)**
   Returns a sequence containing all parts of self split around delimiters defined by the characters in
   String delim.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+-------------------------------------------+
   | Expression                                                  | Result                                    |
   +=============================================================+===========================================+
   | 'tokenize operation'.tokenize('e')                          | Sequence{'tok', 'niz', ' op', 'ration'}   |
   +-------------------------------------------------------------+-------------------------------------------+
   | 'tokenize operation'.tokenize('i')                          | Sequence{'token', 'ze operat', 'on'}      |
   +-------------------------------------------------------------+-------------------------------------------+

 **contains (String substring) : Boolean**
   Returns **true** if *self* contains the substring *substring*, **false** otherwise.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+--------------------+
   | Expression                                                  | Result             |
   +=============================================================+====================+
   | 'contains operation'.contains('ins op')                     | true               |
   +-------------------------------------------------------------+--------------------+
   | 'contains operation'.contains('2009')                       | false              |
   +-------------------------------------------------------------+--------------------+

Non-standard *OclAny* operations
--------------------------------

 All of the examples from this section are set in the context of this model (with **root** being an instance of
 *Model* as per the UML metamodel) :
 
 .. image:: ../images/model_example.png
 
 **A note on properties** : properties can be accessed only if they've been added through the API. For this
 purpose, a number of facilities is provided. You can either override the generated launcher's *addProperties*
 method and add new pathes to properties files there, call manually one of the methods
 **AcceleoService#addPropertiesFile()** or manually add key/value pairs through **AcceleoService#addProperties()**.
 Take note that the key/value pairs manually added will *always* take precedence over the properties taken from
 *.properties* files; and the *first* added property file will always take precedence over subsequently added
 files.
 
 The example on all four *getProperty* variants will take into account the following setup : we provided the
 environment with a properties file *a.properties* containing the key/value pair :
 
 ::
 
   a.b.c = This is a parameterized property : {0}
   
 Then we provided it with a file *b.properties* containing the pairs :
 
 ::
 
   a.b.c.d = This is a standard property
   a.b.c = Parameterized property with a name conflict : {0}

 **eAllContents () : Sequence(OclAny)**
   Returns the whole content tree of the receiver as a Sequence.

   examples:

   .. class:: exampletable

   +-----------------------------+-------------------------------------------------------------------------------------+
   | Expression                  | Result                                                                              |
   +=============================+=====================================================================================+
   | root.eAllContents()         | Sequence{package1, package11, Class11, Class1a, Class1b, package2, Class2, aClas2}  |
   +-----------------------------+-------------------------------------------------------------------------------------+
   | package1.eAllContents()     | Sequence{package11, Class11, Class1a, Class1b}                                      |
   +-----------------------------+-------------------------------------------------------------------------------------+

 **eAllContents (OclType oclType) : Sequence(OclAny)**
   Returns the elements of the given type from the whole content tree of the receiver as a Sequence.

   examples:

   .. class:: exampletable

   +-------------------------------+----------------------------------------------+
   | Expression                    | Result                                       |
   +===============================+==============================================+
   | root.eAllContents(Class)      | Sequence{Class11, Class1a, Class1b, Class2}  |
   +-------------------------------+----------------------------------------------+
   | package1.eAllContents(Class)  | Sequence{Class11, Class1a, Class1b}          |
   +-------------------------------+----------------------------------------------+

 **ancestors () : Sequence(OclAny)**
   Returns a Sequence containing the full set of the receiver's ancestors.

   examples:

   .. class:: exampletable

   +-----------------------------+--------------------------------------+
   | Expression                  | Result                               |
   +=============================+======================================+
   | Class11.ancestors()         | Sequence{package11, package1, root}  |
   +-----------------------------+--------------------------------------+
   | package11.ancestors()       | Sequence{package1, root}             |
   +-----------------------------+--------------------------------------+

 **ancestors (OclType oclType) : Sequence(OclAny)**
   Returns the elements of the given type from the set of the receiver's ancestors as a Sequence.

   examples:

   .. class:: exampletable

   +------------------------------+--------------------------------------+
   | Expression                   | Result                               |
   +==============================+======================================+
   | Class11.ancestors(Package)   | Sequence{package11, package1}        |
   +------------------------------+--------------------------------------+
   | package11.ancestors(Package) | Sequence{package1}                   |
   +------------------------------+--------------------------------------+

 **siblings () : Sequence(OclAny)**
   Returns a Sequence containing the full set of the receiver's siblings.

   examples:

   .. class:: exampletable

   +-----------------------------+--------------------------------------+
   | Expression                  | Result                               |
   +=============================+======================================+
   | Class11.siblings()          | Sequence{}                           |
   +-----------------------------+--------------------------------------+
   | Class1a.siblings()          | Sequence{package11, Class1b}         |
   +-----------------------------+--------------------------------------+

 **siblings (OclType oclType) : Sequence(OclAny)**
   Returns the elements of the given type from the set of the receiver's siblings as a Sequence.

   examples:

   .. class:: exampletable

   +----------------------------------------------+---------------------+
   | Expression                                   | Result              |
   +==============================================+=====================+
   | Class11.siblings(Class)                      | Sequence{}          |
   +----------------------------------------------+---------------------+
   | Class1a.siblings(Class)                      | Sequence{Class1b}   |
   +----------------------------------------------+---------------------+

 **eInverse () : Sequence(OclAny)**
   Returns the set of all objects referencing *self*.

   examples:

   .. class:: exampletable

   +----------------------------------+-------------------------------------+
   | Expression                       | Result                              |
   +==================================+=====================================+
   | Class2.eInverse()                | Sequence{aClass2}                   |
   +----------------------------------+-------------------------------------+
   | package11.eInverse()             | Sequence{}                          |
   +----------------------------------+-------------------------------------+

 **eInverse (OclType oclType) : Sequence(OclAny)**
   Returns the elements of the given type from the set of the inverse references of *self*.

   examples:

   .. class:: exampletable

   +----------------------------------------------+---------------------------------+
   | Expression                                   | Result                          |
   +==============================================+=================================+
   | Class2.eInverse(Property)                    | Sequence{aClass2}               |
   +----------------------------------------------+---------------------------------+
   | Class2.eInverse(Package)                     | Sequence{}                      |
   +----------------------------------------------+---------------------------------+

 **toString () : String**
   Returns the String representation of the receiver.

   examples depend on the "toString()" implementation of *self*. Let's assume it has been changed to return the
   object's name:

   .. class:: exampletable

   +--------------------------------------------------+---------------------+
   | Expression                                       | Result              |
   +==================================================+=====================+
   | Class11.toString()                               | 'Class11'           |
   +--------------------------------------------------+---------------------+

 **invoke (String class, String method, Sequence(OclAny) arguments ) : OclAny**
   Invokes the method *method* of class *class* with the given arguments. This will return OclInvalid if the method
   cannot be called in any way (bad arguments, mispelled name, mispelled signature, encapsulation errors, ...).

   examples:

   .. class:: exampletable

   +------------------------------------------------------------------+---------------------+
   | Expression                                                       | Result              |
   +==================================================================+=====================+
   | invoke('java.lang.String', 'toUpperCase()', Sequence{root.name}) | ROOT                |
   +------------------------------------------------------------------+---------------------+

 **current (Integer index) : OclAny**
   Returns the value of the context *index* ranks above the current context.

   The following example is explained line by line in the "result" column.

   .. list-table::
      :class: exampletable
      :header-rows: 1

      * - Expression
        - Result
      * - | [for (p : Package |pipe| root.packagedElement)]
          |     [for (c : Class |pipe| p.packagedElement)]
          |         [current(0)/]
          |         [current(1)/]
          |         [current(2)/]
          |     [/for]
          | [/for]
        - | Iterates over all packages of the Model *root*
          | Iterates over all classes of the current package
          | allows access to the current class (equivalent to *c*)
          | allows access to the current package (equivalent to *p*)
          | allows access to *self* as it was before the first **for** loop

 **current (OclType filter) : OclAny**
   This will have the same effect as current(Integer) except that is will return the first context (*self* variable) of
   the given type, at or above the current one.

   The following example is explained line by line in the "result" column.

   .. list-table::
      :class: exampletable
      :header-rows: 1

      * - Expression
        - Result
      * - | [for (p : Package |pipe| root.packagedElement)]
          |     [for (c : Class |pipe| p.packagedElement)]
          |         [current(Class)/]
          |         [current(Package)/]
          |         [current(Model)/]
          |     [/for]
          | [/for]
        - | Iterates over all packages of the Model *root*
          | Iterates over all classes of the current package
          | allows access to the current class (equivalent to *c*)
          | allows access to the current package (equivalent to *p*)
          | allows access to the the *root* **Model**

 **getProperty (String key) : OclAny**
   Returns the value of the property corresponding to the given *key*. Note that parameterized properties will be
   returned as is with this.

   examples:

   .. class:: exampletable

   +--------------------------------------------------+------------------------------------------+
   | Expression                                       | Result                                   |
   +==================================================+==========================================+
   | getProperty('a.b.c')                             | 'This is a parameterized property : {0}' |
   +--------------------------------------------------+------------------------------------------+
   | getProperty('a.b.c.d')                           | 'This is a standard property'            |
   +--------------------------------------------------+------------------------------------------+

 **getProperty (String key, Sequence(OclAny) parameters) : OclAny**
   Returns the value of the property corresponding to the given key, with its parameters substituted with the given
   values if any.

   examples:

   .. class:: exampletable

   +--------------------------------------------------+---------------------------------------------------+
   | Expression                                       | Result                                            |
   +==================================================+===================================================+
   | getProperty('a.b.c', Sequence{'substitution'})   | 'This is a parameterized property : substitution' |
   +--------------------------------------------------+---------------------------------------------------+
   | getProperty('a.b.c', Sequence{})                 | 'This is a parameterized property : {0}'          |
   +--------------------------------------------------+---------------------------------------------------+
   | getProperty('a.b.c.d', Sequence{'substitution'}) | 'This is a standard property'                     |
   +--------------------------------------------------+---------------------------------------------------+

 **getProperty (String name, String key) : OclAny**
   Returns the value of the property corresponding to the given *key* from a properties file corresponding to the
   given *name*. Note that parameterized properties will be returned as is with this.

   examples:

   .. class:: exampletable

   +--------------------------------------------------+-----------------------------------------------------+
   | Expression                                       | Result                                              |
   +==================================================+=====================================================+
   | getProperty('b.properties', 'a.b.c')             | 'Parameterized property with a name conflict : {0}' |
   +--------------------------------------------------+-----------------------------------------------------+
   | getProperty('a.properties', 'a.b.c.d')           | |invalid|                                           |
   +--------------------------------------------------+-----------------------------------------------------+

 **getProperty (String name, String key, Sequence(OclAny) parameters) : OclAny**
   Returns the value of the property corresponding to the given *key* from a properties file corresponding to the
   given *name*, with its parameters substituted with the given values if any.

   examples:

   .. class:: exampletable

   +------------------------------------------------------------------+--------------------------------------------------------------+
   | Expression                                                       | Result                                                       |
   +==================================================================+==============================================================+
   | getProperty('b.properties', 'a.b.c', Sequence{'substitution'})   | 'Parameterized property with a name conflict : substitution' |
   +------------------------------------------------------------------+--------------------------------------------------------------+
   | getProperty('b.properties', 'a.b.c', Sequence{})                 | 'Parameterized property with a name conflict : {0}'          |
   +------------------------------------------------------------------+--------------------------------------------------------------+
   | getProperty('a.properties', 'a.b.c.d', Sequence{'substitution'}) | |invalid|                                                    |
   +------------------------------------------------------------------+--------------------------------------------------------------+

Non-standard *Collection* operations
------------------------------------

 **sep (String separator) : Sequence(OclAny)**
   Returns all elements from the source collection separated by an element composed of the String *separator*.

   examples:

   .. class:: exampletable

   +---------------------------------------+-------------------------------------------------------------+
   | Expression                            | Result                                                      |
   +=======================================+=============================================================+
   | package1.eContents().name.sep('2009') | Sequence{'Package11', '2009', 'Class1a', '2009', 'Class1b'} |
   +---------------------------------------+-------------------------------------------------------------+

Glossary
========

 **invalid**
   *invalid* is the singleton instance of the OCLInvalid type. It is returned whenever an evaluation fails,
   whatever the cause. Referred to as |invalid| in this guide.

 **Standard / Non-standard**
   We refer as *standard* everything that directly comes or has been inferred from the OMG MOFM2T specification. As
   such, Standard operations are operations that were defined in the MTL standard library. Likewise, *non-standard*
   features are deviations from the specification.

 |invalid|
   See **invalid**.