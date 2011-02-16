=============================
 Acceleo Operations Reference
=============================

:Authors:
  Laurent Goubet,
  Laurent Delaigue
:Contact:
	laurent.goubet@obeo.fr,
	laurent.delaigue@obeo.fr

Copyright |copy| 2009, 2010 Obeo\ |trade|.

.. |copy| unicode:: 0xA9 
.. |trade| unicode:: U+2122
.. |invalid| unicode:: U+00D8
.. |pipe| unicode:: U+007C
.. contents:: Contents

Acceleo standard operations reference
=====================================

Standard *String* Operations
----------------------------

first (Integer n) : String
__________________________
   Returns the first *n* characters of *self*, or *self* if its size is less than *n*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'first operation'.first(8)                                  | 'first op'                 |
   +-------------------------------------------------------------+----------------------------+
   | 'first operation'.first(-1)                                 | |invalid|                  |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

index (String r) : Integer
__________________________
   Returns the index of substring *r* in *self*, or -1 if *self* contains no occurrence of *r*.
   
   **Important:** String indexes start at 1. Consequently the last character's index in a string
   is equal to the string's length.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'index operation'.index('op')                               | 7                          |
   +-------------------------------------------------------------+----------------------------+
   | 'index operation'.index('i')                                | 1                          |
   +-------------------------------------------------------------+----------------------------+
   | 'index operation'.index('foo')                              | -1                         |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

isAlpha () : Boolean
____________________
   Returns **true** if *self* consists only of alphabetical characters, **false** otherwise.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+-----------------------------------------------+
   | Expression                                                  | Result                                        |
   +=============================================================+===============================================+
   | 'isAlpha'.isAlpha()                                         | true                                          |
   +-------------------------------------------------------------+-----------------------------------------------+
   | 'isAlpha operation'.isAlpha()                               | false (spaces are not alphabetical characters)|
   +-------------------------------------------------------------+-----------------------------------------------+
   | 'isAlpha11'.isAlpha()                                       | false (digits are not alphabetical characters)|
   +-------------------------------------------------------------+-----------------------------------------------+

back to Contents_

isAlphanum () : Boolean
_______________________
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

back to Contents_

last (Integer n) : String
___________________________________________________________________________
   Returns the last *n* characters of *self*, or *self* if its size is less than *n*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'first operation'.last(8)                                   | 'peration'                 |
   +-------------------------------------------------------------+----------------------------+
   | 'first operation'.last(40)                                  | 'first operation'          |
   +-------------------------------------------------------------+----------------------------+
   | 'first operation'.last(-1)                                  | |invalid|                  |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

strcmp (String s1) : Integer
___________________________________________________________________________
   Returns an integer that is either negative, zero or positive depending on whether *s1* is alphabetically less than,
   equal to or greater than *self*. Note that upper case letters come before lower case ones, so that 'AA' is closer to
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

back to Contents_

strstr (String r) : Boolean
___________________________________________________________________________
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

back to Contents_

strtok (String r, Integer n) : String
___________________________________________________________________________
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

back to Contents_

substitute (String r, String t ) : String
___________________________________________________________________________
   Substitutes substring *r* in *self* by substring *t* and returns the resulting string. Will return *self*
   if it contains no occurrence of the substring *r*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'substitute operation'.substitute('t', 'T')                 | 'subsTiTuTe operaTion'     |
   +-------------------------------------------------------------+----------------------------+
   | 'foobar foobar foobar'.substitute('t', 'T')                 | 'foobar foobar foobar'     |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

toLowerFirst () : String
___________________________________________________________________________
   Creates a copy of *self* with its first character converted to lower case and returns it.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'ToLowerFirst operation'.toLowerFirst()                     | 'toLowerFirst operation'   |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

toUpperFirst () : String
___________________________________________________________________________
   Creates a copy of *self* with its first character converted to upper case and returns it.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'toUpperFirst operation'.toUpperFirst()                     | 'ToUpperFirst operation'   |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

Standard *Integer* operations
-----------------------------

toString () : String
___________________________________________________________________________
   Converts the integer *self* to a string.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 2009.toString()                                             | '2009'                     |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

Standard *Real* operations
--------------------------

toString () : String
___________________________________________________________________________
   Converts the real *self* to a string.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | (-5.3).toString()                                           | '-5.3'                     |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

Acceleo non standard operations reference
=========================================

Non-standard *String* operations
--------------------------------

contains (String substring) : Boolean
___________________________________________________________________________
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

back to Contents_

endsWith (String substring) : Boolean
___________________________________________________________________________
   Returns **true** if *self* ends with the substring *substring*, **false** otherwise.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'endsWith operation'.endsWith('ation')                      | true                       |
   +-------------------------------------------------------------+----------------------------+
   | 'endsWith operation'.endsWith('endsWith')                   | false                      |
   +-------------------------------------------------------------+----------------------------+
   | 'anything'.endsWith('')                                     | true                       |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

equalsIgnoreCase (String other) : Boolean
___________________________________________________________________________
   Returns **true** if *self* is equal to the string *other* ignoring case considerations, otherwise returns **false**.
   Two strings are considered equal ignoring case if they are of the same length and corresponding characters
   in the two strings are equal ignoring case. 

   examples:

   .. class:: exampletable

   +--------------------------------------------------------------+----------------------------+
   | Expression                                                   | Result                     |
   +==============================================================+============================+
   | 'lowercase'.equalsIgnoreCase('LOWERCASE')                    | true                       |
   +--------------------------------------------------------------+----------------------------+
   | 'lowercase'.equalsIgnoreCase('lowercase')                    | true                       |
   +--------------------------------------------------------------+----------------------------+
   | 'lowercase'.equalsIgnoreCase('lowerCase')                    | true                       |
   +--------------------------------------------------------------+----------------------------+
   | 'lowercase'.equalsIgnoreCase('uppercase')                    | false                      |
   +--------------------------------------------------------------+----------------------------+

back to Contents_

lastIndex (String r) : Integer
___________________________________________________________________________
   Returns the last index of substring *r* in *self*, or -1 if *self* contains no occurrence of *r*.
   
   **Important:** String indexes start at 1. Consequently the last character's index in a string
   is equal to the string's length.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'index operation'.lastIndex('op')                           | 7                          |
   +-------------------------------------------------------------+----------------------------+
   | 'index operation'.lastIndex('o')                            | 14                         |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

matches (String regex) : Boolean
___________________________________________________________________________
   Returns **true** if *self* matches the given regular expression pattern *regex*, **false** otherwise.
   The regex engine used is that of your runtime JDK. The given pattern is passed "as is" to the method *matches*
   of the java class *String*.
   For more about regular expressions, please refer to the JDK API documentation.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'characters and spaces'.matches('[\\w\\s]+')                | true                       |
   +-------------------------------------------------------------+----------------------------+
   | 'characters and 3 digits'.matches('[\\w\\s]+')              | false                      |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

replace (String substring, String replacement) : String
___________________________________________________________________________
   Substitutes the first occurrence of substring *substring* in *self* by substring *replacement* and returns the
   resulting string. Returns *self* if it contains no occurrence of *substring*. Note that both *substring* and
   *replacement* are treated as regular expressions.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'replace operation'.replace('p', 'P')                       | 'rePlace operation'        |
   +-------------------------------------------------------------+----------------------------+
   | 'repla ce operation'.replace('(\\\\w+)\\\\s*', '\\\\1')     | 'replace operation'        |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

replaceAll (String substring, String replacement) : String
___________________________________________________________________________
   Substitutes all substrings *substring* in *self* by substring *replacement* and returns the resulting string.
   Returns *self* if it contains no occurrence of *substring*. Note that both *substring* and *replacement* are
   treated as regular expressions.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------------------+
   | Expression                                                    | Result                     |
   +===============================================================+============================+
   | 'replaceAll operation'.replaceAll('p', 'P')                   | 'rePlaceAll oPeration'     |
   +---------------------------------------------------------------+----------------------------+
   | 'Repla ce All Operation'.replaceAll('(\\\\w+)\\\\s*', '\\\\1')| 'ReplaceAllOperation'      |
   +---------------------------------------------------------------+----------------------------+

back to Contents_

startsWith (String substring) : Boolean
___________________________________________________________________________
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
   | 'anything'.startsWith('')                                   | true                       |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

substituteAll (String substring, String replacement) : String
___________________________________________________________________________
   Substitutes all substrings *substring* in self by substring *replacement* and returns the resulting string.
   Returns *self* if it contains no occurrence of *substring*. Unlike the **replaceAll** operation, neither
   *substring* nor *replacement* are considered as regular expressions.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'substituteAll operation'.substituteAll('t', 'T')           | 'subsTiTuTeAll operaTion'  |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

substring (Integer startIndex) : String
___________________________________________________________________________
   Returns a substring of *self*, starting at *startIndex* (inclusive), until the end of *self*.
   Returns |invalid| when the *startIndex* is either negative, zero, or greater than *self*'s length.
   
   **Important:** String indexes start at 1. Consequently the last character's index in a string
   is equal to the string's length.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | 'short term'.substring(7)                                   | 'term'                     |
   +-------------------------------------------------------------+----------------------------+
   | 'short term'.substring(-1)                                  | |invalid|                  |
   +-------------------------------------------------------------+----------------------------+
   | 'short term'.substring(0)                                   | |invalid|                  |
   +-------------------------------------------------------------+----------------------------+
   | 'short term'.substring(10)                                  | 'm'                        |
   +-------------------------------------------------------------+----------------------------+
   | 'short term'.substring(11)                                  | |invalid|                  |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

tokenize (String substring) : Sequence(String)
___________________________________________________________________________
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

back to Contents_

trim () : String
___________________________________________________________________________
   Removes all leading and trailing white space characters (tabulation, space, line feed, ...) of *self*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+----------------------------+
   | Expression                                                  | Result                     |
   +=============================================================+============================+
   | ' trim operation '.trim()                                   | 'trim operation'           |
   +-------------------------------------------------------------+----------------------------+

back to Contents_

Non-standard *EObject* operations
---------------------------------

 All of the examples from this section are set in the context of this model (with **root** being an instance of
 *Model* as per the UML metamodel) :
 
 .. image:: ../images/operation_reference/model_example.png

ancestors () : Sequence(EObject)
___________________________________________________________________________
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

back to Contents_

ancestors (OclType oclType) : Sequence(oclType)
___________________________________________________________________________

   Returns the elements of the given type from the set of the receiver's ancestors as a Sequence.
   The returned sequence's elements are typed with the expected type
   (so there's no need to invoke ``oclAsType(oclType)`` on the sequence or its elements).

   examples:

   .. class:: exampletable

   +------------------------------+--------------------------------------+
   | Expression                   | Result                               |
   +==============================+======================================+
   | Class11.ancestors(Package)   | Sequence{package11, package1}        |
   +------------------------------+--------------------------------------+
   | package11.ancestors(Package) | Sequence{package1}                   |
   +------------------------------+--------------------------------------+

back to Contents_

eAllContents () : Sequence(EObject)
___________________________________________________________________________
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

back to Contents_

eAllContents (OclType oclType) : Sequence(oclType)
___________________________________________________________________________
   Returns the elements of the given type from the whole content tree of the receiver as a Sequence.
   The returned sequence's elements are typed with the expected type
   (so there's no need to invoke ``oclAsType(oclType)`` on the sequence or its elements).

   examples:

   .. class:: exampletable

   +-------------------------------+----------------------------------------------+
   | Expression                    | Result                                       |
   +===============================+==============================================+
   | root.eAllContents(Class)      | Sequence{Class11, Class1a, Class1b, Class2}  |
   +-------------------------------+----------------------------------------------+
   | package1.eAllContents(Class)  | Sequence{Class11, Class1a, Class1b}          |
   +-------------------------------+----------------------------------------------+

back to Contents_

eContainer (OclType oclType) : oclType
___________________________________________________________________________
   Returns the first ancestor of the given type, i.e. the first ancestor for which
   ``oclIsKindOf(oclType)`` evaluates to **true**.
   The returned element is typed with the expected type (so there's no need to invoke ``oclAsType(oclType)`` on it).
   
   **Important:** users of Acceleo 2.x should note that, contrary to what took place in acceleo 2.x,
   this operation **never** returns *self* even when ``self.oclIsKindOf(oclType)`` is true.

   examples:

   .. class:: exampletable

   +------------------------------+------------------+
   | Expression                   | Result           |
   +==============================+==================+
   | Class11.eContainer(Package)  | package11        |
   +------------------------------+------------------+
   | package11.eContainer(Package)| package1         |
   +------------------------------+------------------+
   | aClass2.eContainer(Package)  | package11        |
   +------------------------------+------------------+

back to Contents_

eContents (OclType oclType) : Sequence(oclType)
___________________________________________________________________________
   Returns a sequence of the direct children of *self* that are of the given type, i.e. the direct children for which
   ``oclIsKindOf(oclType)`` evaluates to **true**.
   The returned sequence's elements are typed with the expected type
   (so there's no need to invoke ``oclAsType(oclType)`` on the sequence or its elements).

   examples:

   .. class:: exampletable

   +------------------------------+------------------------------+
   | Expression                   | Result                       |
   +==============================+==============================+
   | package1.eContents(Class)    | Sequence{Class1b, Class 1a}  |
   +------------------------------+------------------------------+

back to Contents_

eGet (String featureName) : EJavaObject
___________________________________________________________________________
   This will fetch the value of the feature named *featureName* on the current Object. Return type
   can as well be a collection as a single value.

   examples:

   .. class:: exampletable

   +-------------------------------------------------+---------------------------------------+
   | Expression                                      | Result                                |
   +=================================================+=======================================+
   | package1.eGet('packagedElement')                | Sequence{Class1b, Class1a, package11} |
   +-------------------------------------------------+---------------------------------------+
   | package1.eGet('name')                           | 'package1'                            |
   +-------------------------------------------------+---------------------------------------+

back to Contents_

eInverse () : Sequence(EObject)
___________________________________________________________________________
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

back to Contents_

eInverse (OclType oclType) : Sequence(oclType)
___________________________________________________________________________
   Returns the elements of the given type from the set of the inverse references of *self*.
   The returned sequence's elements are typed with the expected type
   (so there's no need to invoke ``oclAsType(oclType)`` on the sequence or its elements).

   examples:

   .. class:: exampletable

   +----------------------------------------------+---------------------------------+
   | Expression                                   | Result                          |
   +==============================================+=================================+
   | Class2.eInverse(Property)                    | Sequence{aClass2}               |
   +----------------------------------------------+---------------------------------+
   | Class2.eInverse(Package)                     | Sequence{}                      |
   +----------------------------------------------+---------------------------------+

back to Contents_

followingSiblings () : Sequence(EObject)
___________________________________________________________________________
   Returns a Sequence containing the full set of the receiver's following siblings.

   examples:

   .. class:: exampletable

   +-----------------------------+--------------------------------------+
   | Expression                  | Result                               |
   +=============================+======================================+
   | Class11.followingSiblings() | Sequence{}                           |
   +-----------------------------+--------------------------------------+
   | Class1b.followingSiblings() | Sequence{Class1a, package11}         |
   +-----------------------------+--------------------------------------+

back to Contents_

followingSiblings (OclType oclType) : Sequence(oclType)
___________________________________________________________________________
   Returns the elements of the given type from the set of the receiver's following siblings as a Sequence.
   The returned sequence's elements are typed with the expected type
   (so there's no need to invoke ``oclAsType(oclType)`` on the sequence or its elements).

   examples:

   .. class:: exampletable

   +------------------------------------+--------------------------------+
   | Expression                         |            Result              |
   +====================================+================================+
   | Class1b.followingSiblings(Package) | Sequence{package11}            |
   +------------------------------------+--------------------------------+
   | Class1b.followingSiblings(Class)   | Sequence{Class1a}              |
   +------------------------------------+--------------------------------+
   | Class1a.followingSiblings(Class)   | Sequence{}                     |
   +------------------------------------+--------------------------------+

back to Contents_

precedingSiblings () : Sequence(EObject)
___________________________________________________________________________
   Returns a Sequence containing the full set of the receiver's preceding siblings.

   examples:

   .. class:: exampletable

   +------------------------------+--------------------------------------+
   | Expression                   | Result                               |
   +==============================+======================================+
   | package11.precedingSiblings()| Sequence{Class1b, Class1a}           |
   +------------------------------+--------------------------------------+
   | Class11.precedingSiblings()  | Sequence{}                           |
   +------------------------------+--------------------------------------+
   | Class1a.precedingSiblings()  | Sequence{Class1b}                    |
   +------------------------------+--------------------------------------+

back to Contents_

precedingSiblings (OclType oclType) : Sequence(oclType)
___________________________________________________________________________
   Returns the elements of the given type from the set of the receiver's preceding siblings as a Sequence.
   The returned sequence's elements are typed with the expected type
   (so there's no need to invoke ``oclAsType(oclType)`` on the sequence or its elements).

   examples:

   .. class:: exampletable

   +------------------------------------+--------------------------------+
   | Expression                         |            Result              |
   +====================================+================================+
   | Class1a.precedingSiblings(Package) | Sequence{}                     |
   +------------------------------------+--------------------------------+
   | Class1a.precedingSiblings(Class)   | Sequence{Class1b}              |
   +------------------------------------+--------------------------------+

back to Contents_

siblings () : Sequence(EObject)
___________________________________________________________________________
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

back to Contents_

siblings (OclType oclType) : Sequence(oclType)
___________________________________________________________________________
   Returns the elements of the given type from the set of the receiver's siblings as a Sequence.
   The returned sequence's elements are typed with the expected type
   (so there's no need to invoke ``oclAsType(oclType)`` on the sequence or its elements).

   examples:

   .. class:: exampletable

   +----------------------------------------------+---------------------+
   | Expression                                   | Result              |
   +==============================================+=====================+
   | Class11.siblings(Class)                      | Sequence{}          |
   +----------------------------------------------+---------------------+
   | Class1a.siblings(Class)                      | Sequence{Class1b}   |
   +----------------------------------------------+---------------------+

back to Contents_

Non-standard *OclAny* operations
--------------------------------
 
 **A note on properties**: properties can be accessed only if they've been added through the API. For this
 purpose, a number of facilities is provided. You can either override the generated launcher's *addProperties*
 method and add new paths to properties files there, call manually one of the methods
 **AcceleoService#addPropertiesFile()** or manually add key/value pairs through **AcceleoService#addProperties()**.
 Take note that the key/value pairs manually added will *always* take precedence over the properties taken from
 *.properties* files; and the *first* added property file will always take precedence over subsequently added
 files.
 
 The example on all four *getProperty* variants will take into account the following setup: we provided the
 environment with a properties file *a.properties* containing the key/value pair:
 
 ::
 
   a.b.c = This is a parameterized property: {0}
   
 Then we provided it with a file *b.properties* containing the pairs:
 
 ::
 
   a.b.c.d = This is a standard property
   a.b.c = Parameterized property with a name conflict: {0}

current (Integer index) : OclAny
___________________________________________________________________________

   Returns the value of the context *index* ranks above the current context.

   The following example is explained line by line in the "result" column.

   .. list-table::
      :class: exampletable
      :header-rows: 1

      * - Expression
        - Result
      * - | [for (p: Package |pipe| root.packagedElement)]
          |     [for (c: Class |pipe| p.packagedElement)]
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

back to Contents_

current (OclType filter) : OclAny
___________________________________________________________________________

   This will have the same effect as current(Integer) except that is will return the first context (*self* variable) of
   the given type, at or above the current one.

   The following example is explained line by line in the "result" column.

   .. list-table::
      :class: exampletable
      :header-rows: 1

      * - Expression
        - Result
      * - | [for (p: Package |pipe| root.packagedElement)]
          |     [for (c: Class |pipe| p.packagedElement)]
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

back to Contents_

getProperty (String key) : OclAny
_________________________________
   Returns the value of the property corresponding to the given *key*. Note that parameterized properties will be
   returned "as is" by this operation (parameters are not processed).

   examples:

   .. class:: exampletable

   +--------------------------------------------------+------------------------------------------+
   | Expression                                       | Result                                   |
   +==================================================+==========================================+
   | getProperty('a.b.c')                             | 'This is a parameterized property: {0}'  |
   +--------------------------------------------------+------------------------------------------+
   | getProperty('a.b.c.d')                           | 'This is a standard property'            |
   +--------------------------------------------------+------------------------------------------+
   | getProperty('a.b.c.d.e')                         | null                                     |
   +--------------------------------------------------+------------------------------------------+

back to Contents_

getProperty (String key, Sequence(OclAny) parameters) : OclAny
___________________________________________________________________________
   Returns the value of the property corresponding to the given key, with its parameters substituted with the given
   values if any.

   examples:

   .. class:: exampletable

   +--------------------------------------------------+---------------------------------------------------+
   | Expression                                       | Result                                            |
   +==================================================+===================================================+
   | getProperty('a.b.c', Sequence{'substitution'})   | 'This is a parameterized property: substitution'  |
   +--------------------------------------------------+---------------------------------------------------+
   | getProperty('a.b.c', Sequence{})                 | 'This is a parameterized property: {0}'           |
   +--------------------------------------------------+---------------------------------------------------+
   | getProperty('a.b.c.d', Sequence{'substitution'}) | 'This is a standard property'                     |
   +--------------------------------------------------+---------------------------------------------------+

back to Contents_

getProperty (String name, String key) : OclAny
___________________________________________________________________________
   Returns the value of the property corresponding to the given *key* from a properties file corresponding to the
   given *name*. Note that parameterized properties will be returned as is with this.

   examples:

   .. class:: exampletable

   +--------------------------------------------------+-----------------------------------------------------+
   | Expression                                       | Result                                              |
   +==================================================+=====================================================+
   | getProperty('b.properties', 'a.b.c')             | 'Parameterized property with a name conflict: {0}'  |
   +--------------------------------------------------+-----------------------------------------------------+
   | getProperty('a.properties', 'a.b.c.d')           | |invalid|                                           |
   +--------------------------------------------------+-----------------------------------------------------+

back to Contents_

getProperty (String name, String key, Sequence(OclAny) parameters) : OclAny
___________________________________________________________________________
   Returns the value of the property corresponding to the given *key* from a properties file corresponding to the
   given *name*, with its parameters substituted with the given values if any.

   examples:

   .. class:: exampletable

   +------------------------------------------------------------------+--------------------------------------------------------------+
   | Expression                                                       | Result                                                       |
   +==================================================================+==============================================================+
   | getProperty('b.properties', 'a.b.c', Sequence{'substitution'})   | 'Parameterized property with a name conflict: substitution'  |
   +------------------------------------------------------------------+--------------------------------------------------------------+
   | getProperty('b.properties', 'a.b.c', Sequence{})                 | 'Parameterized property with a name conflict: {0}'           |
   +------------------------------------------------------------------+--------------------------------------------------------------+
   | getProperty('a.properties', 'a.b.c.d', Sequence{'substitution'}) | |invalid|                                                    |
   +------------------------------------------------------------------+--------------------------------------------------------------+

back to Contents_

invoke (String class, String method, Sequence(OclAny) arguments ) : OclAny
__________________________________________________________________________
   Invokes the Java method *method* of class *class* with the given arguments. This will return OclInvalid if the method
   cannot be called in any way (bad arguments, mispelled name, mispelled signature, encapsulation errors, ...). This is
   only intended to be used to call Java methods for now.

   examples:

   .. class:: exampletable

   +------------------------------------------------------------------+---------------------+
   | Expression                                                       | Result              |
   +==================================================================+=====================+
   | invoke('java.lang.String', 'toUpperCase()', Sequence{root.name}) | ROOT                |
   +------------------------------------------------------------------+---------------------+

back to Contents_

toString () : String
___________________________________________________________________________
   Returns the String representation of the receiver.

   examples depend on the "toString()" implementation of *self*. Let's assume it has been changed to return the
   object's name:

   .. class:: exampletable

   +--------------------------------------------------+---------------------+
   | Expression                                       | Result              |
   +==================================================+=====================+
   | Class11.toString()                               | 'Class11'           |
   +--------------------------------------------------+---------------------+

back to Contents_

Non-standard *Collection* operations
------------------------------------

sep (String separator) : Sequence(OclAny)
___________________________________________________________________________
   Returns all elements from the source collection separated by an element composed of the String *separator*.

   examples:

   .. class:: exampletable

   +---------------------------------------+-------------------------------------------------------------+
   | Expression                            | Result                                                      |
   +=======================================+=============================================================+
   | package1.eContents().name.sep('2009') | Sequence{'Package11', '2009', 'Class1a', '2009', 'Class1b'} |
   +---------------------------------------+-------------------------------------------------------------+
   | package1.eContents().sep('2009')      | Sequence{Package11, '2009', Class1a, '2009', Class1b}       |
   +---------------------------------------+-------------------------------------------------------------+

back to Contents_

filter (OclType type) : Collection(OclType)
___________________________________________________________________________
   Filters out of the collection all elements that are not instances
   of the given type or any of its subtypes.
   The returned collection is typed according to *type*.
   Makes it easier to write ``select(e | e.oclIsKindOf(type)).oclAsType(type)``.
   
   examples:

   .. class:: exampletable

   +---------------------------------------+------------------------------------+
   | Expression                            | Result                             |
   +=======================================+====================================+
   | package1.eContents().filter(Class)    | Sequence{Class1b, Class1a}         |
   +---------------------------------------+------------------------------------+

back to Contents_

reverse () (Only on ordered collections)
___________________________________________________________________________
   Reverses the order of the collection: the last element becomes the first and
   vice-versa.
   Only available on **ordered collections** (Sequence and OrderedSet).
   
   examples:

   .. class:: exampletable

   +---------------------------------------+-------------------------------------------------------------+
   | Expression                            | Result                                                      |
   +=======================================+=============================================================+
   | OrderedSet {1, 2, 3}                  | OrderedSet {3, 2, 1}                                        |
   +---------------------------------------+-------------------------------------------------------------+
   | Sequence {1, 2, 3}                    | Sequence {3, 2, 1}                                          |
   +---------------------------------------+-------------------------------------------------------------+

back to Contents_

lastIndexOf (T elt) : Integer (Only on ordered collections)
___________________________________________________________________________
   Returns the position of the given element in the collection it is applied to.
   Only available on **ordered collections** (Sequence and OrderedSet).
   
   examples:

   .. class:: exampletable

   +---------------------------------------+------------------------------+
   | Expression                            | Result                       |
   +=======================================+==============================+
   | OrderedSet {1, 2, 1}->lastIndexOf(1)  | 3                            |
   +---------------------------------------+------------------------------+
   | Sequence {1, 2, 3}->lastIndexOf(4)    | -1                           |
   +---------------------------------------+------------------------------+
   | Sequence {1, null}->lastIndexOf(null) | 2                            |
   +---------------------------------------+------------------------------+
   | Sequence {1, 2, 3}->lastIndexOf(null) | -1                           |
   +---------------------------------------+------------------------------+

back to Contents_

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