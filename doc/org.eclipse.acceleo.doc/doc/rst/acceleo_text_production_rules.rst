==================================
 Acceleo Text Production Rules
==================================

:Authors:
	Laurent Goubet,
	Laurent Delaigue
:Contact:
	laurent.goubet@obeo.fr,
	laurent.delaigue@obeo.fr

Copyright |copy| 2008, 2010 Obeo\ |trade|.

.. |copy| unicode:: 0xA9 
.. |trade| unicode:: U+2122
.. |return| unicode:: U+00B6
.. |inter| unicode:: U+00B7
.. |tab| unicode:: U+21AA
.. contents:: Contents

Overview
===========================================
This is still a draft and is only provided as information. Though this document
is still missing some formatting and examples, it can be used to get a precise
idea as to how indentation and carriage returns are handled in Acceleo.

Definitions
===========================================
Text production rules will apply to all body elements.
Each body element can be either stand alone or embedded within other body
elements.
It will be easier to understand text production rules by splitting these body
elements in five main categories : expressions, static text, comments, template
invocation and blocks.

"Blocks" include template, for, if, let, protected area, file, trace and macro
blocks.
A block will be considered « stand-alone » if it is either one of:

- A single line block that is not surrounded with any other body element, except
  for white spaces, block tails and comments;
- A multi-line block that is not surrounded with any other body element, except
  for white spaces, block tails and comments, on the lines where the block head
  and tail occur.

Any block that does not fall into these categories will be considered an 
"embedded" block.

Examples
-------------------------------------------

[PENDING - formatting of the section]

Stand-alone single-line blocks
___________________________________________

::

  [for (Sequence{false, false, false})][self/][/for]

----

::

  [if (true)]output[/if]

----

::

      [for (Sequence{false, false, false})][self/][/for]

----

::

  [/if]		[if (true)]output[/if]

----

::

  [/if]	[comment .../]	[if (true)]output[/if]

Embedded single-line blocks
___________________________________________

::

  Some text[for (Sequence{false, false, false})]output[/for]

----

::

  [if (true)]output[/if] and some text

----

::

  	[for (Sequence{false, false, false})]	[self/][/for] and some text.

Stand-alone multi-line blocks
___________________________________________

::

  [for (Sequence{false, false, false})]
      [self/]
  [/for]

----

::

  [if (true)]output
  [/if]

----

::

  [if (true)]
      output[/if]

----

::

  [comment]Generate booleans [/comment][for (Sequence{false, false, false})]
      [self/][/for]

----

::

      [for (Sequence{false, false, false})]
      [self/]
      [/for]

Embedded multi-line blocks
___________________________________________

::

  text[for (Sequence{false, false, false})]
      [self/]
  [/for]

----

::

  [if (true)]
      output[/if] text


Identifying Body Element Boundaries
===========================================

Block Body
-----------------
 **Single-line block**
  Body starts after the closing bracket of the block head and ends before the
  starting bracket of the block tail.
  
 **Multi-line block**
  If the closing bracket of the block head is directly followed by a new line,
  the block's body starts at the beginning of the next line after the block head.
  Otherwise the block's body starts after the closing bracket of its head.
  The body ends before the starting bracket of the block tail, be it directly
  preceded by a new line or not.
  
 **Special handling of "Template" blocks**
  If the last characters preceding the starting bracket of the block tail is a
  new line followed by optional white spaces, the template body ends before the
  last new line character preceding its tail.


Static Text
-----------------
We need to define the concept of line relevance to properly identify these
boundaries. For this purpose, we will describe as "white spaces" the white
space characters contained in a static text, whatever their position in the
text.

A line is considered "relevant" if it contains anything else than white
spaces, block head, block tail and comment.
Note, however, that lines consisting of white spaces *only* are also considered
relevant.

- If a static text is entirely located on a non relevant line, it does not
  produce any text.
- If a static text starts on a non relevant line, all characters located on this
  line are ignored and the static text is considered to start with the character
  after its very first new line character.
- If a static text ends on a non relevant line, all characters located on this
  line are ignored and the static text is considered to end with the character
  before its very last new line character.
- *Special handling of static text following a « protected area » block:*
  all white spaces following a protected area tail are retained, including the
  new line character.
  The static text then starts right after the closing bracket of the protected
  area tail.

Rules
===========================================
Considering the boundaries outlined in section
`Identifying Body Element Boundaries`_ above, the text production rules stand
as follows:

- The text produced by the execution of expressions is output as is;
- The text produced by the execution of static text is output as is;
- Comments do not produce any text;
- Each line of the text produced by a template invocation will be indented to
  match the indentation of the line sporting the invocation;
- The text produced by the execution of embedded blocks, be they multi-line
  blocks or single line blocks, is output as is along with all text produced by
  the surrounding body elements.
- The text produced by the execution of stand-alone, single line blocks is
  output as is. White spaces located before and after the block are retained;
- The text produced by the execution of stand-alone, multi-line blocks will be
  output as is considering the aforementioned boundaries: if the very first
  character of the block body is a new line, it is ignored.
  Please note that if a stand-alone, multi-line block doesn't produce any text,
  not even a new line character will be present in the resulting text.

Examples
===========================================

[PENDING - formatting of the section]

In the following examples, invisible characters have been materialized:

+------------+--------------------------------------+
| |inter|    | indicates a space character          |
+------------+--------------------------------------+
| |tab|      | indicates a horizontal tab character |
+------------+--------------------------------------+
| |return|   | indicates a carriage return          |
+------------+--------------------------------------+

Blocks
-----------------

Embedded Blocks
______________________________________

The template code:

  ``Some`` |inter| ``text`` |inter| ``[for`` |inter| ``(Sequence{false,`` |inter|
  ``false,`` |inter| ``false})`` |inter| ``separator`` |inter| ``('`` |inter|
  ``-`` |inter| ``')]output[/for]``

produces the following result:

  ``Some`` |inter| ``text`` |inter| ``output`` |inter| ``-`` |inter| ``output``
  |inter| ``-`` |inter| ``output``

----

The template code:

  ``[if`` |inter| ``(true)]output[/if]`` |inter| ``and`` |inter| ``some``
  |inter| ``text``

produces:

  ``output`` |inter| ``and`` |inter| ``some`` |inter| ``text``

----

The template code:

  |tab| ``[for`` |inter| ``(Sequence{false,`` |inter| ``false})]`` |inter|
  ``[self/][/for]`` |inter| ``and`` |inter| ``some`` |inter| ``text``

produces:

  |tab| ``false`` |inter| ``false`` |inter| ``and`` |inter| ``some``
  |inter| ``text``

----

The template code:

  ``text[for`` |inter| ``(Sequence{false,`` |inter| ``false})]`` |return|

  |tab| ``[self/]`` |return|

  ``[/for]text``

produces:

  ``text`` |tab| ``false`` |return|

  |tab| ``false`` |return|

  ``text``

----

The template code:

  ``text[for`` |inter| ``(Sequence{false,`` |inter| ``false})]`` |return|

  |tab| ``output[/for]text``

produces:

  ``text`` |tab| ``output`` |tab| ``outputtext`` |return|


Stand-alone single-line blocks
___________________________________________

The template code:

  ``[for`` |inter| ``(Sequence{false,`` |inter| ``false,`` |inter|
  ``false})][self/][/for]``

produces:

  ``falsefalsefalse``

----

The template code:

  ``[if`` |inter| ``(true)]output[/if]``

produces:

  ``output``

----

The template code:

  |tab| ``[for`` |inter| ``(Sequence{false,`` |inter| ``false,`` |inter|
  ``false})][self/][/for]``

produces:

  |tab| ``falsefalsefalse``

----

The template code:

  ``[/if]`` |tab| |tab| ``[if`` |inter| ``(true)]output[/if]``

produces:

  |tab| |tab| ``output``

----

The template code:

  ``[/if]`` |inter| ``[comment .../]`` |tab| ``[if`` |inter| ``(true)]output[/if]``

produces:

  |tab| |tab| ``output``

Stand-alone multi-line blocks
___________________________________________

The template code:

  ``[for`` |inter| ``(Sequence{false,`` |inter| ``false,`` |inter| ``false})]`` |return|

  |tab| ``[self/]`` |return|
	
  ``[/for]``

produces:

  |tab| ``false`` |return|

  |tab| ``false`` |return|

  |tab| ``false`` |return|


----

The template code:

  ``[if`` |inter| ``(false)]output`` |return|

  ``[/if]``

produces an empty chain "".

----

The template code:

  ``[if`` |inter| ``(true)]`` |return|

  |tab| ``output[/if]``

produces:

  ``output``

----

The template code:

  ``[comment]for`` |inter| ``loop[/comment][for`` |inter|
  ``(Sequence{false,`` |inter| ``false})]`` |return|

  |tab| ``[if`` |inter| ``(self)]`` |return|
	
  |tab| |tab| ``[self/]`` |return|
		
  |tab| ``[/if]`` |return|
	
  ``[/for]``

produces an empty chain "".

----

The template code:

  |tab| ``[for`` |inter| ``(Sequence{false,`` |inter| ``false,`` |inter|
  ``false})]`` |return|

  ``[self/]`` |return|
	
  |tab| ``[/for]`` |return|


produces:

  ``false`` |return|

  ``false`` |return|

  ``false`` |return|

Complete Example
===========================================

[PENDING - write a complete and meaningful generation module highlighting the most frequently used rules]