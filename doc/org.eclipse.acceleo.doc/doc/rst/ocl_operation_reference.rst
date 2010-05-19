========================
OCL operations reference
========================

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
.. contents:: Contents

Ocl operations for type *Classifier*
====================================

allInstances () : Set{T}
-----------------------------------------------------------------
   Returns a Set containing all of the existing instances of the current classifier (along with instances of all its
   inherited classifiers).

   examples:

   .. class:: exampletable

   +----------------------------------------------------------------------------------+----------------+
   | Expression                                                                       | Result         |
   +==================================================================================+================+
   | let a : String = 'a', b : String = 'b', c : Integer = 2 in String.allInstances() | Set{'a','b'}   |
   +----------------------------------------------------------------------------------+----------------+

Back to Contents_

Ocl operations for type *OclAny*
====================================

oclAsType ( typespec : Classifier ) : T
-----------------------------------------------------------------
   Returns *self* statically typed as typespec if it is an instance of this type. *Note* that this does not alter the
   runtime value of *self*, it only enables access to subtype operations. This operation allows users to cast *self*
   to another type.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+-----------------------------+
   | Expression                                                  | Result                      |
   +=============================================================+=============================+
   | aPerson.oclAsType(Employee)                                 | an object of Employee type  |
   +-------------------------------------------------------------+-----------------------------+

Back to Contents_

oclIsInvalid () : Boolean
-----------------------------------------------------------------
   Returns **true** if *self* is equal to *invalid*.

   examples:

   .. class:: exampletable

   +--------------------------------------------------------------+--------+
   | Expression                                                   | Result |
   +==============================================================+========+
   | let anObject : String = null in anObject.oclIsUndefined()    | false  |
   +--------------------------------------------------------------+--------+
   | let anObject : String = invalid in anObject.oclIsUndefined() | true   |
   +--------------------------------------------------------------+--------+
   | let anObject : String = 'null' in anObject.oclIsUndefined()  | false  |
   +--------------------------------------------------------------+--------+

Back to Contents_

oclIsKindOf( Classifier typespec ) : Boolean
-----------------------------------------------------------------
   Returns **true** if the type of *self* corresponds to the type or supertype of typespec, **false** otherwise. This
   operation allows users to check the class hierarchy of *self* much like would an *instanceof* Java.

   examples:

   .. class:: exampletable

   +----------------------------------+--------+
   | Expression                       | Result |
   +==================================+========+
   | anEmployee.oclIsKindOf(Employee) | true   |
   +----------------------------------+--------+
   | anEmployee.oclIsKindOf(Person)   | true   |
   +----------------------------------+--------+
   | aCat.oclIsKindOf(Person)         | false  |
   +----------------------------------+--------+

Back to Contents_

oclIsTypeOf( typespec : Classifier ) : Boolean
-----------------------------------------------------------------
   Returns **true** if the type of *self* is the same as typespec, or **false** otherwise. This operation allows users
   to check the exact class type of *self*.
   
   examples:

   .. class:: exampletable

   +----------------------------------+--------+
   | Expression                       | Result |
   +==================================+========+
   | anEmployee.oclIsTypeOf(Employee) | true   |
   +----------------------------------+--------+
   | anEmployee.oclIsTypeOf(Person)   | false  |
   +----------------------------------+--------+
   | aCat.oclIsTypeOf(Person)         | false  |
   +----------------------------------+--------+

Back to Contents_

oclIsUndefined () : Boolean
-----------------------------------------------------------------
   Returns **true** if *self* is equal to *invalid* or *null*.

   examples:

   .. class:: exampletable

   +--------------------------------------------------------------+--------+
   | Expression                                                   | Result |
   +==============================================================+========+
   | let anObject : String = null in anObject.oclIsUndefined()    | true   |
   +--------------------------------------------------------------+--------+
   | let anObject : String = invalid in anObject.oclIsUndefined() | true   |
   +--------------------------------------------------------------+--------+
   | let anObject : String = 'null' in anObject.oclIsUndefined()  | false  |
   +--------------------------------------------------------------+--------+

Back to Contents_

<> ( object : OclAny ) : Boolean
-----------------------------------------------------------------
   Returns **true** if *self* is a different object from *object*.

   examples:

   .. class:: exampletable

   +--------------------------------------------------+--------+
   | Expression                                       | Result |
   +==================================================+========+
   | let a : String = 'a', b : String = 'a' in a <> b | false  |
   +--------------------------------------------------+--------+
   | let a : Integer = 2, b : Real = 2.0 in a <> b    | false  |
   +--------------------------------------------------+--------+
   | let a : Integer = -2, b : Integer = 2 in a <> b  | true   |
   +--------------------------------------------------+--------+

Back to Contents_

= ( object : OclAny) : Boolean
-----------------------------------------------------------------
   Returns **true** if *self* is equal to *object*.

   examples:

   .. class:: exampletable

   +--------------------------------------------------+--------+
   | Expression                                       | Result |
   +==================================================+========+
   | let a : String = 'a', b : String = 'a' in a = b  | true   |
   +--------------------------------------------------+--------+
   | let a : Integer = 2, b : Real = 2.0 in a = b     | true   |
   +--------------------------------------------------+--------+
   | let a : Integer = -2, b : Integer = 2 in a = b   | false  |
   +--------------------------------------------------+--------+

Back to Contents_

< ( object : T ) : Boolean
-----------------------------------------------------------------
   Returns **true** if *self* is comparable to *object* and less than *object*.

   examples:

   .. class:: exampletable

   +--------------------------------------------------------------+--------+
   | Expression                                                   | Result |
   +==============================================================+========+
   | let a : Integer = 1, b : Integer = 2 in a < b                | true   |
   +--------------------------------------------------------------+--------+
   | let a : Real = 1.5, b : Integer = 2 in a < b                 | true   |
   +--------------------------------------------------------------+--------+
   |let a : String = 'Anteater', b : String = 'Aardvark' in a < b | false  |
   +--------------------------------------------------------------+--------+

Back to Contents_

> ( object : T ) : Boolean
-----------------------------------------------------------------
   Returns **true** if *self* is comparable to *object* and greater than *object*.

   examples:

   .. class:: exampletable

   +--------------------------------------------------------------+--------+
   | Expression                                                   | Result |
   +==============================================================+========+
   | let a : Integer = 1, b : Integer = 2 in a > b                | false  |
   +--------------------------------------------------------------+--------+
   | let a : Real = 1.5, b : Integer = 2 in a > b                 | false  |
   +--------------------------------------------------------------+--------+
   |let a : String = 'Anteater', b : String = 'Aardvark' in a > b | true   |
   +--------------------------------------------------------------+--------+

Back to Contents_

<= ( object : T ) : Boolean
-----------------------------------------------------------------
   Returns **true** if *self* is comparable to *object* and less than or equal to *object*.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+--------+
   | Expression                                                    | Result |
   +===============================================================+========+
   | let a : Integer = 1, b : Integer = 2 in a <= b                | true   |
   +---------------------------------------------------------------+--------+
   | let a : Real = 1.5, b : Integer = 2 in a <= b                 | true   |
   +---------------------------------------------------------------+--------+
   |let a : String = 'Anteater', b : String = 'Aardvark' in a <= b | false  |
   +---------------------------------------------------------------+--------+

Back to Contents_

>= ( object : T ) : Boolean
-----------------------------------------------------------------
   Returns **true** if *self* is comparable to *object* and greater than or equal to *object*.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+--------+
   | Expression                                                    | Result |
   +===============================================================+========+
   | let a : Integer = 1, b : Integer = 2 in a >= b                | false  |
   +---------------------------------------------------------------+--------+
   | let a : Real = 1.5, b : Integer = 2 in a >= b                 | false  |
   +---------------------------------------------------------------+--------+
   |let a : String = 'Anteater', b : String = 'Aardvark' in a >= b | true   |
   +---------------------------------------------------------------+--------+

Back to Contents_

Ocl operations for type *String*
====================================

**A note on Strings** : OCL Strings begin at index *1*, not *0* as in most languages. Thus *'test'.at(0)* fails in
*invalid* whereas *'test'.at(1)* yields *'t'*. Likewise, *'test'.substring(2, 2)* returns *'e'*.

concat ( s : String ) : String
-----------------------------------------------------------------
   Returns a string containing *self* followed by *s*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------------+--------------------+
   | Expression                                                        | Result             |
   +===================================================================+====================+
   | 'concat'.concat(' ').concat('operation')                          | 'concat operation' |
   +-------------------------------------------------------------------+--------------------+

Back to Contents_

size () : Integer
-----------------------------------------------------------------
   Returns the number of characters composing *self*.

   examples:

   .. class:: exampletable

   +-------------------------------------------------------------+--------+
   | Expression                                                  | Result |
   +=============================================================+========+
   | 'size operation'.size()                                     | 14     |
   +-------------------------------------------------------------+--------+

Back to Contents_

substring ( lower : Integer, upper : Integer ) : String
-----------------------------------------------------------------
   Returns a string containing all characters from *self* starting from index *lower* up to index *upper* included.
   Both *lower* and *upper* parameters should be contained between *1* and *self.size()* included. *lower* cannot be
   greater than *upper*.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | 'substring operation'.substring(11, 19)                       | 'operation'    |
   +---------------------------------------------------------------+----------------+
   | 'substring operation'.substring(1, 1)                         | 's'            |
   +---------------------------------------------------------------+----------------+
   | 'substring operation'.substring(0, 1)                         | |invalid|      |
   +---------------------------------------------------------------+----------------+

Back to Contents_

toInteger () : Integer
-----------------------------------------------------------------
   Returns an Integer of value equal to *self*, or |invalid| if *self* does not represent an integer.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | '3.0'.toInteger()                                             | |invalid|      |
   +---------------------------------------------------------------+----------------+
   | '4'.toInteger()                                               | 4              |
   +---------------------------------------------------------------+----------------+
   | 'toInteger'.toInteger()                                       | |invalid|      |
   +---------------------------------------------------------------+----------------+

Back to Contents_

toLower () : String
-----------------------------------------------------------------
   Returns *self* with all characters converted to lowercase.

   examples:

   .. class:: exampletable

   +------------------------------------------------------------+-------------------+
   | Expression                                                 | Result            |
   +============================================================+===================+
   | 'LoWeR OpErAtIoN'.toLower()                                | 'lower operation' |
   +------------------------------------------------------------+-------------------+

Back to Contents_

toReal () : Real
-----------------------------------------------------------------
   Returns a Real of value equal to *self*, or |invalid| if *self* does not represent a real.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | '3.0'.toReal()                                                | 3.0            |
   +---------------------------------------------------------------+----------------+
   | '4'.toReal()                                                  | 4.0            |
   +---------------------------------------------------------------+----------------+
   | 'toReal'.toReal()                                             | |invalid|      |
   +---------------------------------------------------------------+----------------+

Back to Contents_

toUpper () : String
-----------------------------------------------------------------
   Returns *self* with all characters converted to uppercase.

   examples:

   .. class:: exampletable

   +------------------------------------------------------------+-------------------+
   | Expression                                                 | Result            |
   +============================================================+===================+
   | 'UpPeR OpErAtIoN'.toUpper()                                | 'UPPER OPERATION' |
   +------------------------------------------------------------+-------------------+

Back to Contents_

Ocl operations for type *Number*
====================================

In addition to the basic math functions (+, -, /, \*) are a number of advanced functions. Take note that *Number*
denotes both *Integer* and *Real*, and they're substitutive unless otherwise specified.

Number::abs () : Number
-----------------------------------------------------------------
   Returns the absolute value of *self*, *self* if it is already a positive number.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | (-2.3).abs()                                                  | 2.3            |
   +---------------------------------------------------------------+----------------+
   | -5.abs()                                                      | 5              |
   +---------------------------------------------------------------+----------------+

Back to Contents_

Number::floor () : Integer
-----------------------------------------------------------------
   Returns the integer part of *self* if it is a Real, *self* if it is an Integer.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | (2.3).floor()                                                 | 2              |
   +---------------------------------------------------------------+----------------+
   | (2.8).floor()                                                 | 2              |
   +---------------------------------------------------------------+----------------+
   | 2.floor()                                                     | 2              |
   +---------------------------------------------------------------+----------------+

Back to Contents_

Number::max ( r : Number ) : Number
-----------------------------------------------------------------
   Returns the greatest number between *self* and *r*.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | 6.max(3)                                                      | 6              |
   +---------------------------------------------------------------+----------------+
   | 6.max(5.2)                                                    | 6.0            |
   +---------------------------------------------------------------+----------------+
   | (2.3).max(3)                                                  | 3.0            |
   +---------------------------------------------------------------+----------------+
   | (2.3).max(5.2)                                                | 5.2            |
   +---------------------------------------------------------------+----------------+

Back to Contents_

Number::min ( r : Number ) : Number
-----------------------------------------------------------------
   Returns the lowest number between *self* and *r*.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | 6.min(3)                                                      | 6              |
   +---------------------------------------------------------------+----------------+
   | 6.min(5.2)                                                    | 5.2            |
   +---------------------------------------------------------------+----------------+
   | (2.3).min(3)                                                  | 2.3            |
   +---------------------------------------------------------------+----------------+
   | (2.3).min(5.2)                                                | 2.3            |
   +---------------------------------------------------------------+----------------+

Back to Contents_

Number::round () : Integer
-----------------------------------------------------------------
   Returns the nearest integer to *self* if it is a Real, *self* if it is an Integer.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | (2.3).round()                                                 | 2              |
   +---------------------------------------------------------------+----------------+
   | (2.5).round()                                                 | 3              |
   +---------------------------------------------------------------+----------------+
   | (2.8).round()                                                 | 3              |
   +---------------------------------------------------------------+----------------+
   | 2.round()                                                     | 2              |
   +---------------------------------------------------------------+----------------+

Back to Contents_

Integer::div ( i : Integer ) : Integer
-----------------------------------------------------------------
   Returns the integer quotient of the division of *self* by *i*.
   
   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | 3.div(2)                                                      | 1              |
   +---------------------------------------------------------------+----------------+
   | 11.div(3)                                                     | 3              |
   +---------------------------------------------------------------+----------------+
  
Back to Contents_

Integer::mod ( i : Integer ) : Integer
-----------------------------------------------------------------
   Returns the integer remainder of the division of *self* by *i*.
   
   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | 3.mod(2)                                                      | 1              |
   +---------------------------------------------------------------+----------------+
   | 11.mod(3)                                                     | 2              |
   +---------------------------------------------------------------+----------------+

Back to Contents_

Ocl operations for type *Collection*
====================================

Please note that OCL collections can contain the *null* value (null) but not the *invalid* value (|invalid|). Trying
to add |invalid| within a new or existing collection will yield |invalid| as a result. OCL proposes four distinct kinds
of collections offering all possibilities of ordering/unicity.

 .. list-table::
		:header-rows: 1
		:stub-columns: 1
           
		* - Collection type
		  - Ordered
		  - Unique
		* - Sequence
		  - true
		  - false
		* - OrderedSet
		  - true
		  - true
		* - Bag
		  - false
		  - false
		* - Set
		  - false
		  - true

Back to Contents_

any ( expr : OclExpression ) : T
-----------------------------------------------------------------
	Returns any element contained in *self* that validates the condition *expr*, null otherwise. Evaluation is shortcut as soon
	as an element validating *expr* is found. Note that the result of this on unordered collections will be random if more than
	one element validates *expr*.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{1.2, 2.3, 5.2, 0.9}->any(self < 1)                   | 0.9            |
	+---------------------------------------------------------------+----------------+
	| Sequence{1.2, 2.3, 5.2, 0.9}->any(self < 2)                   | 1.2            |
	+---------------------------------------------------------------+----------------+

Back to Contents_

asBag () : Bag(T)
-----------------------------------------------------------------
	Returns a Bag containing all elements of *self*.
	
	examples:
	
	.. class:: exampletable
	
	+-------------------------------------------------------+-----------------------+
	| Expression                                            | Result                |
	+=======================================================+=======================+
	| Sequence{'3', 1, 2.0, '3'}->asBag()                   | Bag{2.0, '3', 1, '3'} |
	+-------------------------------------------------------+-----------------------+
	| Bag{1, 2.0, '3'}->asBag()                             | Bag{2.0, 1, '3'}      |
	+-------------------------------------------------------+-----------------------+
	| OrderedSet{1, 2.0, '3'}->asBag()                      | Bag{2.0, 1, '3'}      |
	+-------------------------------------------------------+-----------------------+
	| OrderedSet{1, 1, 2.0, '3'}->asBag()                   | Bag{'3', 1, 2.0}      |
	+-------------------------------------------------------+-----------------------+
	| Set{1, 2.0, '3'}->asBag()                             | Bag{2.0, 1, '3'}      |
	+-------------------------------------------------------+-----------------------+
	| Set{1, 1, 2.0, '3'}->asBag()                          | Bag{2.0, '3', 1}      |
	+-------------------------------------------------------+-----------------------+

Back to Contents_

asOrderedSet () : OrderedSet(T)
-----------------------------------------------------------------
	Returns an OrderedSet containing all elements of *self*. Element ordering is preserved when possible.
	
	examples:
	
	.. class:: exampletable
	
	+-------------------------------------------------------+-------------------------+
	| Expression                                            | Result                  |
	+=======================================================+=========================+
	| Sequence{1, 2.0, '3'}->asOrderedSet()                 | OrderedSet{1, '3', 2.0} |
	+-------------------------------------------------------+-------------------------+
	| Sequence{1, 1, 2.0, '3'}->asOrderedSet()              | OrderedSet{'3', 1, 2.0} |
	+-------------------------------------------------------+-------------------------+
	| Bag{1, 2.0, '3'}->asOrderedSet()                      | OrderedSet{2.0, 1, '3'} |
	+-------------------------------------------------------+-------------------------+
	| Bag{1, 1, 2.0, '3'}->asOrderedSet()                   | OrderedSet{1, '3', 2.0} |
	+-------------------------------------------------------+-------------------------+
	| OrderedSet{1, 2.0, '3'}->asOrderedSet()               | OrderedSet{1, 2.0, '3'} |
	+-------------------------------------------------------+-------------------------+
	| Set{1, 2.0, '3'}->asOrderedSet()                      | OrderedSet{'3', 1, 2.0} |
	+-------------------------------------------------------+-------------------------+

Back to Contents_

asSequence () : Boolean
-----------------------------------------------------------------
	Returns a Sequence containing all elements of *self*. Element ordering is preserved when possible.
	
	examples:
	
	.. class:: exampletable
	
	+-------------------------------------------------------+-----------------------+
	| Expression                                            | Result                |
	+=======================================================+=======================+
	| Sequence{1, 2.0, '3'}->asSequence()                   | Sequence{1, 2.0, '3'} |
	+-------------------------------------------------------+-----------------------+
	| Bag{1, 2.0, '3'}->asSequence()                        | Sequence{2.0, 1, '3'} |
	+-------------------------------------------------------+-----------------------+
	| OrderedSet{1, 2.0, '3'}->asSequence()                 | Sequence{1, 2.0, '3'} |
	+-------------------------------------------------------+-----------------------+
	| Set{1, 2.0, '3'}->asSequence()                        | Sequence{'3', 1, 2.0} |
	+-------------------------------------------------------+-----------------------+

Back to Contents_

asSet () : Set(T)
-----------------------------------------------------------------
	Returns a Set containing all elements of *self*.
	
	examples:
	
	.. class:: exampletable
	
	+-------------------------------------------------------+-----------------------+
	| Expression                                            | Result                |
	+=======================================================+=======================+
	| Sequence{1, 2.0, '3'}->asSet()                        | Set{1, '3', 2.0}      |
	+-------------------------------------------------------+-----------------------+
	| Sequence{1, 1, 2.0, '3'}->asSet()                     | Set{'3', 1, 2.0}      |
	+-------------------------------------------------------+-----------------------+
	| Bag{1, 2.0, '3'}->asSet()                             | Set{2.0, 1, '3'}      |
	+-------------------------------------------------------+-----------------------+
	| Bag{1, 1, 2.0, '3'}->asSet()                          | Set{1, '3', 2.0}      |
	+-------------------------------------------------------+-----------------------+
	| OrderedSet{1, 2.0, '3'}->asSet()                      | Set{1, '3', 2.0}      |
	+-------------------------------------------------------+-----------------------+
	| OrderedSet{1, 1, 2.0, '3'}->asSet()                   | Set{'3', 1, 2.0}      |
	+-------------------------------------------------------+-----------------------+
	| Set{1, 2.0, '3'}->asSet()                             | Set{2.0, 1, '3'}      |
	+-------------------------------------------------------+-----------------------+
	| Set{1, 1, 2.0, '3'}->asSet()                          | Set{'3', 1, 2.0}      |
	+-------------------------------------------------------+-----------------------+

Back to Contents_

collect ( expr : OclExpression ) : Collection(T2)
-----------------------------------------------------------------
	Returns a collection containing the result of applying *expr* on all elements contained in *self*.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+-----------------------------+
	| Expression                                                    | Result                      |
	+===============================================================+=============================+
	| Sequence{'first', 'second'}->collect(toUpper())               | Sequence{'FIRST', 'SECOND'} |
	+---------------------------------------------------------------+-----------------------------+

Back to Contents_

collectNested ( expr : OclExpression ) : Collection(T2)
-----------------------------------------------------------------
	Returns a collection containing all the elements contained in *self* on which we applied the OclExpression *expr*.
	The results won't be flattened. The type of the resulting collection depends on the type of *self*.
	
	examples:
	
	For the purpose of these examples we'll assume here that we have a Class *Person* with a reference *children*. Our
	model contains two persons such as *person1.children = {James, Jane}* and *person2.children = {John}*.
	
	.. class:: exampletable
	
	+-------------------------------------------------------+-------------------------------------------------+
	| Expression                                            | Result                                          |
	+=======================================================+=================================================+
	| self.persons->collectNested(children.firstname)       | Sequence{Sequence{James, Jane}, Sequence{John}} |
	+-------------------------------------------------------+-------------------------------------------------+

Back to Contents_

count ( object : T ) : Integer
-----------------------------------------------------------------
   Returns how many times *object* is in the collection *self*.

   examples:

   .. class:: exampletable

   +---------------------------------------------------------------+----------------+
   | Expression                                                    | Result         |
   +===============================================================+================+
   | Sequence{2.3, 5.2}->count(5.2)                                | 1              |
   +---------------------------------------------------------------+----------------+
   | Set{3, 'test', 4.0, 4, 4.0, 'test'}->count(null)              | 0              |
   +---------------------------------------------------------------+----------------+
   | Set{3, null, 4.0, null, 'test'}->count(null)                  | 1              |
   +---------------------------------------------------------------+----------------+
   | Bag{3, null, 4.0, null, 'test'}->count(null)                  | 2              |
   +---------------------------------------------------------------+----------------+

Back to Contents_

excludes ( object : T ) : Boolean
-----------------------------------------------------------------
	Returns **true** if *object* is not contained in *self*, **false** otherwise.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2.3}->excludes(2.1)                                  | true           |
	+---------------------------------------------------------------+----------------+
	| Sequence{2.0}->excludes(2)                                    | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_

excludesAll ( c2 : Collection(T) ) : Boolean
-----------------------------------------------------------------
	Returns **true** if no element of *c2* is contained in *self*, **false** otherwise.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2.3, 5.2, 'a', 3, null}->excludesAll(Set{4, null})   | false          |
	+---------------------------------------------------------------+----------------+
	| Sequence{2.3, 5.2, 'a', 3}->excludesAll(Set{4, null})         | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

excluding ( object : T ) : Collection(T)
-----------------------------------------------------------------
	Returns a collection containing all elements of *self* minus all occurences of *object*.
	**Note** : at the time of writing, the OCL standard library sports a bug which changes *OrderedSets* in *Sets* when
	excluding elements.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| Sequence{'b', 'a', 'b', 'c'}->excluding('b')        | Sequence{'a', 'c'}      |
	+-----------------------------------------------------+-------------------------+
	| Bag{'b', 'a', 'b', 'c'}->excluding('b')             | Bag{'c', 'a'}           |
	+-----------------------------------------------------+-------------------------+
	| OrderedSet{'b', 'a', 'b', 'c'}->excluding('b')      | Set{'c', 'a'}           |
	+-----------------------------------------------------+-------------------------+
	| Set{'b', 'a', 'b', 'c'}->excluding('b')             | Set{'c', 'a'}           |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

exists ( expr : OclExpression ) : Boolean
-----------------------------------------------------------------
	Returns **true** if at least one element in *self* validates the condition *expr*, **false** otherwise. The evaluation
	stops as soon as one element validating *expr* is found.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2.3, 5.2}->exists(self > 3)                          | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

flatten () : Collection(T2)
-----------------------------------------------------------------
	Returns a collection containing all elements of *self* recursively flattened.
	**Note** : at the time of writing, the OCL standard library sports a bug which changes *OrderedSets* in *Sets* when
	flattening. 
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------------------+-------------------------------------+
	| Expression                                                                | Result                              |
	+===========================================================================+=====================================+
	| Sequence{Set{1, 2, 3}, Sequence{2.0, 3.0}, Bag{'test'}}->flatten()        | Sequence{1, 2, 3, 2.0, 3.0, 'test'} |
	+---------------------------------------------------------------------------+-------------------------------------+
	| Bag{Set{Bag{'test', 2, 3.0}}, Sequence{OrderedSet{2.0, 3, 1}}}->flatten() | Bag{1, 2, 3, 2.0, 3.0, 'test'}      |
	+---------------------------------------------------------------------------+-------------------------------------+
	| OrderedSet{Set{Bag{'test', 2, 3.0}}, Sequence{Set{2.0, 3, 1}}}->flatten() | Set{3.0, 2, 1, 3, 'test', 2.0}      |
	+---------------------------------------------------------------------------+-------------------------------------+
	| Set{Set{Bag{'test', 2, 3.0}}, Sequence{OrderedSet{2.0, 3, 1}}}->flatten() | Set{3.0, 2, 1, 3, 'test', 2.0}      |
	+---------------------------------------------------------------------------+-------------------------------------+

Back to Contents_

forAll ( expr : OclExpression ) : Boolean
-----------------------------------------------------------------
	Returns **true** if the all the elements contained in *self* validate the condition *expr*, **false** otherwise.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2.3, 5.2}->forAll(self > 3)                          | false          |
	+---------------------------------------------------------------+----------------+
	| Sequence{2.3, 5.2}->forAll(self > 1.2)                        | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

includes ( object : T ) : Boolean
-----------------------------------------------------------------
	Returns **true** if *object* is contained in *self*, **false** otherwise.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2.3}->includes(2.1)                                  | false          |
	+---------------------------------------------------------------+----------------+
	| Sequence{2.0}->includes(2)                                    | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

includesAll ( c2 : Collection(T) ) : Boolean
-----------------------------------------------------------------
	Returns **true** if all element of *c2* are contained in *self*, **false** otherwise.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2.3, 5.2, 'a', 3, null}->includesAll(Set{3, null})   | true           |
	+---------------------------------------------------------------+----------------+
	| Sequence{2.3, 5.2, 'a', 3}->includesAll(Set{3, null})         | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_

including ( object : T ) : Collection(T)
-----------------------------------------------------------------
	Returns a collection containing all elements of *self* followed by *object*.
	**Note** : at the time of writing, the OCL standard library sports a bug which changes *OrderedSets* in *Sets* when
	including elements.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| Sequence{'a', 'b'}->including('c')                  | Sequence{'a', 'b', 'c'} |
	+-----------------------------------------------------+-------------------------+
	| Bag{'a', 'b'}->including('c')                       | Bag{'a', 'c', 'b'}      |
	+-----------------------------------------------------+-------------------------+
	| OrderedSet{'a', 'b'}->including('c')                | Set{'a', 'c', 'b'}      |
	+-----------------------------------------------------+-------------------------+
	| Set{'a', 'b'}->including('c')                       | Set{'a', 'c', 'b'}      |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

isEmpty () : Boolean
-----------------------------------------------------------------
	Returns **true** if *self* is empty, **false** otherwise.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2, 'a'}->isEmpty()                                   | false          |
	+---------------------------------------------------------------+----------------+
	| Sequence{null}->isEmpty()                                     | false          |
	+---------------------------------------------------------------+----------------+
	| Sequence{}->isEmpty()                                         | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

isUnique ( expr : OclExpression ) : Boolean
-----------------------------------------------------------------
	Returns **true** if all elements contained in *self* evaluate to a distinct value for *expr*.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2.3, 5.2}->isUnique(self > 3)                        | true           |
	+---------------------------------------------------------------+----------------+
	| Sequence{2.3, 5.2}->isUnique(self > 1)                        | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_

notEmpty () : Boolean
-----------------------------------------------------------------
	Returns **true** if *self* contains at least one element, **false** otherwise.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2, 'a'}->notEmpty()                                  | true           |
	+---------------------------------------------------------------+----------------+
	| Sequence{null}->notEmpty()                                    | true           |
	+---------------------------------------------------------------+----------------+
	| Sequence{}->notEmpty()                                        | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_

one ( expr : OclExpression ) : Boolean
-----------------------------------------------------------------
	Returns **true** if there is only one element contained in *self* that validates the condition *expr*, **false** otherwise.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{1.2, 2.3, 5.2, 0.9}->one(self < 1)                   | true           |
	+---------------------------------------------------------------+----------------+
	| Sequence{1.2, 2.3, 5.2, 0.9}->one(self < 2)                   | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_

product ( c2 : Collection(T2) ) : Set(Tuple(first : T, second : T2))
--------------------------------------------------------------------
	Returns a Set of Tuples which represents the cartesian product of *self* with *c2*.
	
	examples (notation of the tuples has been simplified):

	.. class:: exampletable
	
	+------------------------------------------+-----------------------------------------------------------------+
	| Expression                               | Result                                                          |
	+==========================================+=================================================================+ 
	| Sequence{3, 4}->product(Bag{3.0, 4.0})   | Set{Tuple{3, 3.0}, Tuple{3, 4.0}, Tuple{4, 3.0}, Tuple{4, 4.0}} |
	+------------------------------------------+-----------------------------------------------------------------+
	| Set{3, 4}->product(OrderedSet{3.0, 4.0}) | Set{Tuple{3, 3.0}, Tuple{3, 4.0}, Tuple{4, 3.0}, Tuple{4, 4.0}} |
	+------------------------------------------+-----------------------------------------------------------------+

Back to Contents_

reject ( expr : OclExpression ) : Collection(T)
-----------------------------------------------------------------
	Returns a collection with all elements of *self* except for those who validate the OclExpression *expr*. 
	
	examples:
	
	.. class:: exampletable
	
	+-------------------------------------------------------+-------------------------+
	| Expression                                            | Result                  |
	+=======================================================+=========================+
	| Sequence{1, 2, 3}->reject(i : Integer | i > 1 )       | Sequence{1}             |
	+-------------------------------------------------------+-------------------------+
	| Bag{1, 2, 3}->reject(i : Integer | i > 1 )            | Bag{1}                  |
	+-------------------------------------------------------+-------------------------+
	| OrderedSet{1, 2, 3}->reject(i : Integer | i > 1 )     | OrderedSet{1}           |
	+-------------------------------------------------------+-------------------------+
	| Set{1, 2, 3}->reject(i : Integer | i > 1 )            | Set{1}                  |
	+-------------------------------------------------------+-------------------------+

Back to Contents_

select ( expr : OclExpression ) : Collection(T)
-----------------------------------------------------------------
	Returns a collection with all elements of *self* that validate the OclExpression *expr*.
	
	examples:
	
	.. class:: exampletable
	
	+-------------------------------------------------------+-------------------------+
	| Expression                                            | Result                  |
	+=======================================================+=========================+
	| Sequence{1, 2, 3}->select(i : Integer | i > 1)        | Sequence{2, 3}          |
	+-------------------------------------------------------+-------------------------+
	| Bag{1, 2, 3}->select(i : Integer | i > 1 )            | Bag{3, 2}               |
	+-------------------------------------------------------+-------------------------+
	| OrderedSet{1, 2, 3}->select(i : Integer | i > 1 )     | OrderedSet{2, 3}        |
	+-------------------------------------------------------+-------------------------+
	| Set{1, 2, 3}->select(i : Integer | i > 1 )            | Set{3, 2}               |
	+-------------------------------------------------------+-------------------------+

Back to Contents_

size () : Boolean
-----------------------------------------------------------------
	Returns the number of elements contained in *self*.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2.3, 5}->size()                                      | 2              |
	+---------------------------------------------------------------+----------------+
	| Sequence{}->size()                                            | 0              |
	+---------------------------------------------------------------+----------------+

Back to Contents_

sortedBy ( expr : OclExpression ) : Sequence(T)
-----------------------------------------------------------------
	Returns a sorted collection containing all elements from *self* sorted in accordance with the OclExpression *expr*.
	This can be used on all kind of collections yet will always yield a Sequence-typed result except for OrderedSet which
	returns an OrderedSet.
	
	examples:
	
	For the purpose of these examples we'll assume here that we have a Class *Employee* with an attribute *age*. Our
	model contains two employees such as *employee1.age = 24* and *employee2.age = 27*.
	
	.. class:: exampletable
	
	+-------------------------------------------------------+--------------------------------+
	| Expression                                            | Result                         |
	+=======================================================+================================+
	| self.employees->sortedBy(age)                         | Sequence{employee1, employee2} |
	+-------------------------------------------------------+--------------------------------+

Back to Contents_

sum () : Real
-----------------------------------------------------------------
	Returns the sum of all elements contained in *self* if they support the '+' operation.
	
	examples:

	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{2.3, 5.2} in c->sum()                                | 7.5            |
	+---------------------------------------------------------------+----------------+
	| Sequence{2, 4} in c->sum()                                    | 6              |
	+---------------------------------------------------------------+----------------+
	| Sequence{2, '4'} in c->sum()                                  | |invalid|      |
	+---------------------------------------------------------------+----------------+

Back to Contents_

Ocl operations for type *Sequence*
-----------------------------------------------------------------

= ( seq : Sequence(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* contains the very same objects as *seq* in the very same order as they are in *seq*.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{4, 5, 'test'} = Sequence{4, 5, 'test'}               | true           |
	+---------------------------------------------------------------+----------------+
	| Sequence{4, 5, 'test'} = Sequence{4, 'test', 5}               | false          |
	+---------------------------------------------------------------+----------------+
	| Sequence{4, 5, 'test', 5} = Sequence{4, 5, 'test'}            | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_
	
<> ( seq : Sequence(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* does not contain the same objects as *seq*, or if these objects are not in the same order
	as they are in *seq*.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Sequence{4, 5, 'test'} = Sequence{4, 5, 'test'}               | false          |
	+---------------------------------------------------------------+----------------+
	| Sequence{4, 5, 'test'} = Sequence{4, 'test', 5}               | true           |
	+---------------------------------------------------------------+----------------+
	| Sequence{4, 5, 'test', 5} = Sequence{4, 5, 'test'}            | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

append ( object : T ) : Sequence(T)
___________________________________________________________________________
	Returns a Sequence containing all elements of *self* followed by *object*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| Sequence{'a', 'b'}->append('c')                     | Sequence{'a', 'b', 'c'} |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

at ( index : Integer ) : T
___________________________________________________________________________
	Returns the element of *self* at the *index* position.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| Sequence{'a', 'b'}->at(1)                           | a                       |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

first () : T
___________________________________________________________________________
	Returns the first element of *self*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| Sequence{1, 2.0, '3'}->first()                      | 1                       |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

indexOf ( object : T ) : Integer
___________________________________________________________________________
	Returns the position of *object* in sequence *self*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| Sequence{'a', 'b'}->indexOf('a')                    | 1                       |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

insertAt ( index : Integer, object : T) : Sequence(T)
___________________________________________________________________________
	Returns a Sequence containing *self* with *object* inserted at the *index* position.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	|Sequence{'a', 'b'}->insertAt(0, 'c')                 | |invalid|               |
	+-----------------------------------------------------+-------------------------+
	|Sequence{'a', 'b'}->insertAt(1, 'c')                 | Sequence{'c', 'a', 'b'} |
	+-----------------------------------------------------+-------------------------+
	|Sequence{'a', 'b'}->insertAt(3, 'c')                 | Sequence{'a', 'b', 'c'} |
	+-----------------------------------------------------+-------------------------+
	|Sequence{'a', 'b'}->insertAt(4, 'c')                 | |invalid|               |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

last () : T
___________________________________________________________________________
	Returns the last element of *self*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| Sequence{1, 2.0, '3'}->last()                       | '3'                     |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

prepend ( object : T ) : Sequence(T)
___________________________________________________________________________
	Returns a Sequence containing *object* followed by all elements of *self* .
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| Sequence{'a', 'b'}->prepend('c')                    | Sequence{'c', 'a', 'b'} |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

subSequence ( startIndex : Integer, endIndex : Integer ) : Sequence(T)
___________________________________________________________________________
	Returns a Sequence containing all elements of *self* between the positions 'startIndex' and 'endIndex'. 
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| Sequence{'a', 'b', 'c', 'd'}->subSequence(2, 3)     | Sequence{'b', 'c'}      |
	+-----------------------------------------------------+-------------------------+
	| Sequence{'a', 'b', 'c', 'd'}->subSequence(4, 4)     | Sequence{'d'}           |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

union ( seq : Sequence(T) ) : Sequence(T)
___________________________________________________________________________
	Returns a Sequence containing all elements of *self* followed by all elements of *seq*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-----------------------------------+
	| Expression                                          | Result                            |
	+=====================================================+===================================+
	| Sequence{'a', 'b', 'a'}->union(Sequence{'b', 'c'})  | Sequence{'a', 'b', 'a', 'b', 'c'} |
	+-----------------------------------------------------+-----------------------------------+

Back to Contents_

Ocl operations for type *Bag*
-----------------------------------------------------------------

= ( bag : Bag(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* contains the same objects as *bag* in the same quantities.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Bag{4, 5, 'test', 4} = Bag{4, 'test', 5, 4}                   | true           |
	+---------------------------------------------------------------+----------------+
	| Bag{4, 5, 'test'} = Bag{4, 'test', 5}                         | true           |
	+---------------------------------------------------------------+----------------+
	| Bag{4, 5, 'test', 5} = Bag{4, 5, 'test'}                      | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_

<> ( bag : Bag(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* does not contain the same objects as *bag* in the same quantities.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Bag{4, 5, 'test'} = Bag{4, 5, 'test'}                         | false          |
	+---------------------------------------------------------------+----------------+
	| Bag{4, 5, 'test'} = Bag{4, 'test', 5}                         | false          |
	+---------------------------------------------------------------+----------------+
	| Bag{4, 5, 'test', 5} = Bag{4, 5, 'test'}                      | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

intersection ( bag : Bag(T) ) : Bag(T)
___________________________________________________________________________
	Returns a Bag containing all elements of *self* that are also contained by *bag*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------------+-----------------------------------+
	| Expression                                                | Result                            |
	+===========================================================+===================================+
	| Bag{'a', 'b', 'a'}->intersection(Bag{'a', 'b'})           | Bag{'a', 'b'}                     |
	+-----------------------------------------------------------+-----------------------------------+
	| Bag{'a', 'b', 'a', 'b'}->intersection(Bag{'a', 'b', 'b'}) | Bag{'b', 'a', 'b'}                |
	+-----------------------------------------------------------+-----------------------------------+

Back to Contents_
	
intersection ( set : Set(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all elements of *self* that are also contained by *set*.
	
	examples:
	
	.. class:: exampletable
	
	+----------------------------------------------------------+-----------------------------------+
	| Expression                                               | Result                            |
	+==========================================================+===================================+
	| Bag{'a', 'b', 'a'}->intersection(Set{'a', 'b', 'c'})     | Set{'a', 'b'}                     |
	+----------------------------------------------------------+-----------------------------------+

Back to Contents_

union ( bag : Bag(T) ) : Bag(T)
___________________________________________________________________________
	Returns a Bag containing all elements of *self* and all elements of *bag*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-----------------------------------+
	| Expression                                          | Result                            |
	+=====================================================+===================================+
	| Bag{'a', 'b', 'a'}->union(Bag{'b', 'c'})            | Bag{'b', 'a', 'b', 'a', 'c'}      |
	+-----------------------------------------------------+-----------------------------------+

Back to Contents_

union ( set : Set(T) ) : Bag(T)
___________________________________________________________________________
	Returns a Bag containing all elements of *self* and all elements of *set*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-----------------------------------+
	| Expression                                          | Result                            |
	+=====================================================+===================================+
	| Bag{'a', 'b', 'a'}->union(Set{'b', 'c'})            | Bag{'b', 'c', 'a', 'b', 'a'}      |
	+-----------------------------------------------------+-----------------------------------+

Back to Contents_

Ocl operations for type *OrderedSet*
-----------------------------------------------------------------

= ( set : Set(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* contains the same objects as *set*.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| OrderedSet{3, 5, 4} = Set{3, 5, 4}                            | true           |
	+---------------------------------------------------------------+----------------+
	| OrderedSet{3, 5, 4} = Set{4, 3, 5, 4, 4}                      | true           |
	+---------------------------------------------------------------+----------------+
	| OrderedSet{3, 5, 4} = Set{2, 5 ,4, 4}                         | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_
	
= ( orderedset : OrderedSet(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* contains the same objects as *orderedset* regardless of element ordering.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| OrderedSet{3, 5, 4} = OrderedSet{3, 5, 4}                     | true           |
	+---------------------------------------------------------------+----------------+
	| OrderedSet{4, 5, 'test', 5} = OrderedSet{4, 5, 'test'}        | true           |
	+---------------------------------------------------------------+----------------+
	| OrderedSet{4, 5, 'test'} = OrderedSet{4, 'test', 5}           | true           |
	+---------------------------------------------------------------+----------------+
	| OrderedSet{4, 5, 'test'} = OrderedSet{4, 'test'}              | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_

<> ( set : Set(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* does not contain the same objects as *set*.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| OrderedSet{4, 5, 'test', 4} <> Set{4, 5, 'test'}              | false          |
	+---------------------------------------------------------------+----------------+
	| OrderedSet{4, 5, 'test', 4} <> Set{4, 'test', 5, 4}           | false          |
	+---------------------------------------------------------------+----------------+
	| OrderedSet{4, 5, 'test', 4} <> Set{4, 5, 'test', 2}           | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

<> ( orderedset : OrderedSet(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* does not contain the same objects as *orderedset*.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| OrderedSet{4, 5, 'test', 4} <> OrderedSet{4, 5, 'test')       | false          |
	+---------------------------------------------------------------+----------------+
	| OrderedSet{4, 5, 'test', 4} <> OrderedSet{4, 'test', 5, 4}    | false          |
	+---------------------------------------------------------------+----------------+
	| OrderedSet{4, 5, 'test', 4} <> OrderedSet{4, 5, 'test', 2}    | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

`-` ( set : Set(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all elements of *self* minus all elements of *set*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-----------------------------------+
	| Expression                                          | Result                            |
	+=====================================================+===================================+
	| OrderedSet{'a', 'b', 'c'} - Set{'c', 'a'}           | Set{'b'}                          |
	+-----------------------------------------------------+-----------------------------------+

Back to Contents_

append ( object : T ) : OrderedSet(T)
___________________________________________________________________________
	Returns an OrderedSet containing all elements of *self* followed by *object*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+---------------------------+
	| Expression                                          | Result                    |
	+=====================================================+===========================+
	| OrderedSet{'a', 'b'}->append('c')                   | OrderedSet{'a', 'b', 'c'} |
	+-----------------------------------------------------+---------------------------+

Back to Contents_

at ( index : Integer ) : T
___________________________________________________________________________
	Returns the element of *self* located at position *index* in the collection.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| OrderedSet{'a', 'b'}->at(1)                         | 'a'                     |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

first () : T
___________________________________________________________________________
	Returns the first element of *self*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| OrderedSet{1, 2.0, '3'}->first()                    | 1                       |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

indexOf ( object : T ) : Integer
___________________________________________________________________________
	Returns the position of *object* in *self*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	| OrderedSet{'a', 'b'}->indexOf('a')                  | 1                       |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

insertAt ( index : Integer, object : T ) : OrderedSet(T)
___________________________________________________________________________
	Returns an OrderedSet containing *self* with *object* inserted at the *index* position.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+---------------------------+
	| Expression                                          | Result                    |
	+=====================================================+===========================+
	| OrderedSet{'a', 'b'}->insertAt(1, 'c')              | OrderedSet{'c', 'a', 'b'} |
	+-----------------------------------------------------+---------------------------+
	| OrderedSet{'a', 'b'}->insertAt(3, 'c')              | OrderedSet{'a', 'b', 'c'} |
	+-----------------------------------------------------+---------------------------+

Back to Contents_

intersection ( bag : Bag(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all elements of *self* that are also contained by *bag*.
	
	examples:
	
	.. class:: exampletable
	
	+--------------------------------------------------------+--------------------------------+
	| Expression                                             | Result                         |
	+========================================================+================================+
	| OrderedSet{'a', 'b', 'a'}->intersection(Bag{'a', 'b'}) | Set{'a', 'b'}                  |
	+--------------------------------------------------------+--------------------------------+

Back to Contents_

intersection ( set : Set(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all elements of *self* that are also contained by *set*.
	
	examples:
	
	.. class:: exampletable
	
	+--------------------------------------------------------+--------------------------------+
	| Expression                                             | Result                         |
	+========================================================+================================+
	| OrderedSet{'a', 'b', 'a'}->intersection(Set{'a', 'b'}) | Set{'b', 'a'}                  |
	+--------------------------------------------------------+--------------------------------+

Back to Contents_

last () : T
___________________________________________________________________________
	Returns the last element of *self*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-------------------------+
	| Expression                                          | Result                  |
	+=====================================================+=========================+
	|OrderedSet{1, 2.0, '3'}->last()                      | '3'                     |
	+-----------------------------------------------------+-------------------------+

Back to Contents_

prepend ( object : T ) : OrderedSet(T)
___________________________________________________________________________
	Returns an OrderedSet containing *object* followed by all elements of *self*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+---------------------------+
	| Expression                                          | Result                    |
	+=====================================================+===========================+
	| OrderedSet{'a', 'b'}->prepend('c')                  | OrderedSet{'c', 'a', 'b'} |
	+-----------------------------------------------------+---------------------------+

Back to Contents_

subOrderedSet ( startIndex : Integer, endIndex : Integer ) : OrderedSet(T)
___________________________________________________________________________
	Returns an OrderedSet containing all elements of *self* between the positions *startIndex* and *endIndex*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+---------------------------+
	| Expression                                          | Result                    |
	+=====================================================+===========================+
	| OrderedSet{'a', 'b', 'c', 'd'}->subOrderedSet(2, 3) | OrderedSet{'b', 'c'}      |
	+-----------------------------------------------------+---------------------------+
	| OrderedSet{'a', 'b', 'c', 'd'}->subOrderedSet(4, 4) | OrderedSet{'d'}           |
	+-----------------------------------------------------+---------------------------+

Back to Contents_

symmetricDifference ( set : Set(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all of the elements of *self* and *set* that are not present in both.
	
	examples:
	
	.. class:: exampletable
	
	+-------------------------------------------------------------------------+---------------+
	| Expression                                                              | Result        |
	+=========================================================================+===============+
	| OrderedSet{'b', 'a', 'b', 'c'}->symmetricDifference(Set{'a', 'c', 'd'}) | Set{'d', 'b'} |
	+-------------------------------------------------------------------------+---------------+

Back to Contents_

union ( bag : Bag(T) ) : Bag(T)
___________________________________________________________________________
	Returns a Bag containing all elements of *self* followed by all elements of *bag*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-----------------------------------+
	| Expression                                          | Result                            |
	+=====================================================+===================================+
	| OrderedSet{'a', 'b', 'a'}->union(Bag{'b', 'c'})     | Bag{'a', 'c', 'b', 'b'}           |
	+-----------------------------------------------------+-----------------------------------+

Back to Contents_

union ( set : Set(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all elements of *self* followed by all elements of *set*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-----------------------------------+
	| Expression                                          | Result                            |
	+=====================================================+===================================+
	| OrderedSet{'a', 'b', 'a'}->union(Set{'b', 'c'})     | Set{'a', 'c', 'b'}                |
	+-----------------------------------------------------+-----------------------------------+

Back to Contents_

Ocl operations for type *Set*
-----------------------------------------------------------------

= ( set : Set(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* contains the same objects as *set*.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Set{3, 5, 4} = Set{3, 5, 4}                                   | true           |
	+---------------------------------------------------------------+----------------+
	| Set{3, 5, 4} = Set{3, 4, 4, 5}                                | true           |
	+---------------------------------------------------------------+----------------+
	| Set{3, 5, 4} = Set{2, 3, 5, 4}                                | false          |
	+---------------------------------------------------------------+----------------+

Back to Contents_

<> ( set : Set(T) ) : Boolean
___________________________________________________________________________
	Returns **true** if *self* does not contain the same objects as *set*.
	
	examples:
	
	.. class:: exampletable
	
	+---------------------------------------------------------------+----------------+
	| Expression                                                    | Result         |
	+===============================================================+================+
	| Set{4, 5, 'test', 4} <> Set{4, 5, 'test'}                     | false          |
	+---------------------------------------------------------------+----------------+
	| Set{4, 5, 'test', 4} <> Set{5, 4, 'test', 4}                  | false          |
	+---------------------------------------------------------------+----------------+
	| Set{4, 5, 'test', 4} <> Set{4, 'test', 5, 2}                  | true           |
	+---------------------------------------------------------------+----------------+

Back to Contents_

`-` ( set : Set(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all elements of *self* minus all elements of *set*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-----------------------------------+
	| Expression                                          | Result                            |
	+=====================================================+===================================+
	| Set{'a', 'b', 'c'} - Set{'c', 'a'}                  | Set{'b'}                          |
	+-----------------------------------------------------+-----------------------------------+

Back to Contents_

intersection ( bag : Bag(T) ) : Set(T)
___________________________________________________________________________
	Returns a Bag containing all elements of *self* that are also contained in *bag*.
	
	examples:
	
	.. class:: exampletable
	
	+-------------------------------------------------------+-----------------------------------+
	| Expression                                            | Result                            |
	+=======================================================+===================================+
	| Set{'a', 'b', 'a'}->intersection(Bag{'a', 'b', 'c'})  | Set{'a', 'b'}                     |
	+-------------------------------------------------------+-----------------------------------+

Back to Contents_

intersection ( set : Set(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all elements of *self* that are also contained in *set*.
	
	examples:
	
	.. class:: exampletable
	
	+-------------------------------------------------------+-----------------------------------+
	| Expression                                            | Result                            |
	+=======================================================+===================================+
	| Set{'a', 'b', 'a'}->intersection(Set{'a', 'b', 'c'})  | Set{'b', 'a'}                     |
	+-------------------------------------------------------+-----------------------------------+

Back to Contents_

symmetricDifference ( set : Set(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all of the elements of *self* and *set* that are not present in both.
	
	examples:
	
	.. class:: exampletable
	
	+------------------------------------------------------------------+-------------------------+
	| Expression                                                       | Result                  |
	+==================================================================+=========================+
	| Set{'b', 'a', 'b', 'c'}->symmetricDifference(Set{'a', 'c', 'd'}) | Set{'b', 'd'}           |
	+------------------------------------------------------------------+-------------------------+

Back to Contents_

union ( bag : Bag(T) ) : Bag(T)
___________________________________________________________________________
	Returns a Bag containing all elements of *self* and all elements of *bag*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-----------------------------------+
	| Expression                                          | Result                            |
	+=====================================================+===================================+
	| Set{'a', 'b', 'a'}->union(Bag{'b', 'c'})            | Bag{'a', 'c', 'b', 'b'}           |
	+-----------------------------------------------------+-----------------------------------+

Back to Contents_

union ( set : Set(T) ) : Set(T)
___________________________________________________________________________
	Returns a Set containing all elements of *self* and all elements of *set*.
	
	examples:
	
	.. class:: exampletable
	
	+-----------------------------------------------------+-----------------------------------+
	| Expression                                          | Result                            |
	+=====================================================+===================================+
	| Set{'a', 'b', 'a'}->union(Set{'b', 'c'})            | Set{'a', 'c', 'b'}                |
	+-----------------------------------------------------+-----------------------------------+

Back to Contents_

Ocl operations for type *Boolean*
=================================

And
-----------------------------------------------------------------

	.. list-table::
		:class: truthtable
		:header-rows: 1
		:stub-columns: 1
           
		* - And
		  - true
		  - false
		  - |invalid|
		* - true
		  - true
		  - false
		  - true
		* - false
		  - false
		  - false
		  - false
		* - |invalid|
		  - |invalid|
		  - false
		  - |invalid|

Back to Contents_

Implies
-----------------------------------------------------------------

	.. list-table::
		:class: truthtable
		:header-rows: 1
		:stub-columns: 1
           
		* - Implies
		  - true
		  - false
		  - |invalid|
		* - true
		  - true
		  - false
		  - |invalid|
		* - false
		  - true
		  - true
		  - true
		* - |invalid|
		  - true
		  - |invalid|
		  - |invalid|

Back to Contents_

Or
-----------------------------------------------------------------
	
	.. list-table::
		:class: truthtable
		:header-rows: 1
		:stub-columns: 1
		
		* - Or
		  - true
		  - false
		  - |invalid|
		* - true
		  - true
		  - true
		  - true
		* - false
		  - true
		  - false
		  - |invalid|
		* - |invalid|
		  - true
		  - |invalid|
		  - |invalid|

Back to Contents_

Not
-----------------------------------------------------------------

	.. list-table::
		:class: truthtable
		:header-rows: 1
		:stub-columns: 1

		* - Not
		  - Result
		* - true
		  - false
		* - false
		  - true
		* - |invalid|
		  - |invalid|

Back to Contents_

Xor
-----------------------------------------------------------------

	.. list-table::
		:class: truthtable
		:header-rows: 1
		:stub-columns: 1
           
		* - Xor
		  - true
		  - false
		  - |invalid|
		* - true
		  - false
		  - true
		  - |invalid|
		* - false
		  - true
		  - false
		  - |invalid|
		* - |invalid|
		  - |invalid|
		  - |invalid|
		  - |invalid|

Back to Contents_

Glossary
========

 **invalid**
   *invalid* is the singleton instance of the OCLInvalid type. It is returned whenever an evaluation fails,
   whatever the cause. Referred to as |invalid| in this guide.

 |invalid|
   See **invalid**.

Back to Contents_
