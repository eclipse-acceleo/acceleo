================================================================================
Acceleo User Guide
================================================================================

:Authors: Laurent Delaigue
:Contact: laurent.delaigue@obeo.fr

Copyright |copy| 2008, 2010 Obeo\ |trade|.

.. |copy| unicode:: 0xA9 
.. |trade| unicode:: U+2122
.. _JMerge: http://wiki.eclipse.org/JET_FAQ_How_does_JMerge_work%3F
.. _EMF: http://www.eclipse.org/modeling/emf/
.. contents:: Contents

Introduction
================================================================================

Pre-requisites
--------------------------------------------------------------------------------
The reader should have a minimal knowledge about MDA concepts, the eclipse
platform, and the java language.


Overview
================================================================================

Acceleo is the reference implementation of the OMG MTL Specification.
It is an Open-Source implementation, fully integrated in the eclipse platform,
though also able to run outside of eclipse.
Acceleo is fully compatible with the `Eclipse Modeling Framework
<http://www.eclipse.org/modeling/emf/>`_. Consequently, Acceleo is compatible
with any editor using EMF metamodels, like the eclipse UML implementations.

Installation
--------------------------------------------------------------------------------
The simplest way of installing Acceleo is to use a version of eclipse where it
is already installed!
Some versions of Eclipse Helios contain Acceleo, just pick the version that fits
you most at `the eclipse download site
<http://www.eclipse.org/downloads/>`_.

Otherwise, it is always possible to install Acceleo like any other feature in
eclipse. Use the `Help > Install New Software` menu and use the official eclipse
Helios update site.

Eclipse Integration
--------------------------------------------------------------------------------

Acceleo is fully integrated within the eclipse platform. This means that
editing, running, debugging can all be executed within eclipse, by using the
usual eclipse paradigms.
Let's first examine the main elements available for Acceleo in eclipse.

The Acceleo Perspective
________________________________________________________________________________

The Acceleo perspective allows users to work in an environment which provides
every facility to increase productivity while working with Acceleo.
For instance, Acceleo-specific menus are available by default in the Acceleo
perspective in order to make it easy to create projects or templates, and so on.

To switch to the Acceleo perspective, click on *Window > Open Perspective >
Other...* Select the perspective named *Acceleo* (Beware, if you have Acceleo
2.x installed, you may see two Acceleo perspectives available.)

The views displayed by default in this perspective are :
- Package explorer
- Editor area (okay, it's not a view)
- Outline
- Problems
- Error Log

In the Acceleo perspective, the pop-up menu available on the package explorer
allows users to easily create Acceleo artifacts (such as projects or modules).

The Acceleo Views
________________________________________________________________________________

Acceleo provides a few specific views to improve productivity when working with
templates.
These views will be detailed later in the relevant chapters.
Let's just summarize their purpose to get the big picture.

As usual, they can be opened by clicking on *Window > Show View* and selecting
the appropriate view in the menu. Note that in the Acceleo perspective, Acceleo
views are proposed by default directly in this menu, which will not be the case
in another perspective.

The Generation Patterns View
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


The Overrides View
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This view presents you with every Acceleo element that is accessible in your
workbench (no matter your current project's dependencies). You can select
one or several elements (use the checkboxes) and override them.

If the meaning of "override" is not clear, please refer to the `official MTL
Specification <http://www.omg.org/spec/MOFM2T/1.0/>`_.

TODO Explain how to use this view

The Results View
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This view displays the results of the latest generation run.
It displays the list of projects where some code has been generated.
In each project, the files that have been generated (in their folders).

For each file, the view displays:

- A list of model elements use for their generation, in a hierarchical way;
- A list of Acceleo modules used for their generation.

You can double-click on any element to visualize the related portions of
generated text.

You can right-click on any element and choose *Open Declaration* to navigate
to any atomic element used during the generation, be it a model element or an
Acceleo element (template, query).


Acceleo Projects
--------------------------------------------------------------------------------
Acceleo projects are eclipse projects associated with the Acceleo nature.
Such projects contain Acceleo modules, java code, and anything required for the
needs of the code generation to achieve.

**Note:** It is recommended to use a naming that respects the `eclipse plugins
naming conventions <http://wiki.eclipse.org/Naming_Conventions>`_. Of course, 
the name prefix has to be adapted to your specific context, but remember that
using a naming convention that prevents naming conflicts is a good idea.

Creating an Acceleo Project "From Scratch"
________________________________________________________________________________

The aim of an Acceleo project is to generate text from a model (or from a set of models).
For this tutorial, we will create a new Acceleo module for generating java beans from a UML model.

.. image:: ../images/uml_model_example.png

To create a new Acceleo project, right click on the package explorer view then select *New->Acceleo Project*.

.. image:: ../images/new_acceleo_module_project.png

Choose a correct plugin name for the project, then click next.

.. image:: ../images/new_acceleo_module_project_1.png

This wizard's page allows you to initialize the project by creating one or several Acceleo module files.

- Select the folder in which you want to create the new module file.
- Fill in the module name
- Optionally, you can select an existing file which will be copied into the new module file. This could be useful to create a module from an existing file.
- Then, select the metamodel from which your generation file will take its types (in this example, UML)
- Finally, choose the metaclass that will be used to generate the file (in this example, Class). This can be modified later at any time directly in the module files.

**Note:** Other options are available to initialize the new module with exiting content. These options will be discussed later.

.. image:: ../images/new_acceleo_module_project_2.png

You can create more than one module file in this project by using the "Add" button on the left.

Clicking on finish will create the module file(s), and some files automatically generated from it (more on these below).

.. image:: ../images/new_acceleo_module_project_result.png

back to Contents_


Creating an Acceleo UI Project
________________________________________________________________________________
This action can only be performed on an existing Acceleo project.

It allows you to create and initialize a plugin project that will contain
everything needed to launch the Acceleo generations of your project through
actions available in pop-up menus, integrated in eclipse.
This facilitates a lot the deployment of Acceleo generators in the eclipse
platform.

To perform this, just right-click on your Acceleo project, then select
*Acceleo > Create Acceleo UI Launcher Project*. This will start a wizard that
will guide you through the creation process. The created project consists in an
eclipse plugin project that you can afterward maintain as you would any plugin
project.

Transforming an Existing Project into an Acceleo Project
________________________________________________________________________________
Acceleo provides a facility to convert an existing project into an eclipse
project.
Technically, this means that the *Acceleo nature* will be added to the project's
natures.

This can be achieved by right-clicking in the package explorer, then selecting
*New > Convert to an Acceleo Project*.
This will start a wizard which will guide you through the conversion process.

The *Acceleo nature* can be removed from an Acceleo project simply by
right-clicking on the project and selecting *Acceleo > Remove Acceleo Nature*.


Acceleo Modules
--------------------------------------------------------------------------------

A module is a ``.mtl`` file, which contains templates (to generate code) and/or
queries (to extract information from the manipulated models). 

Templates
________________________________________________________________________________

Templates are sets of Acceleo statements used to generate text. They are
delimited by ``[template]...[/template]`` tags.

Queries
________________________________________________________________________________

Queries are sets of OCL statements used to extract information form the model.
Queries return values, or collections of values. They use the OCL language.


Edition
================================================================================

The Acceleo Editor
--------------------------------------------------------------------------------

The module editor provides the following features:

- Syntax highlighting
- Content assistant (ctrl + space)
- Error detection
- Quick fixes (ctrl + shift + 1)
- Dynamic outline
- Quick outline (ctrl + O)
- Code folding
- Open declaration (either with 'ctrl + left click' or 'F3')
- Search references (ctrl + shift + G)

.. image:: ../images/acceleo_editor.png

For more information about the Acceleo syntax, please read the official OMG
specification accessible from `the official MTL Specification
<http://www.omg.org/spec/MOFM2T/1.0/>`_.

back to Contents_

Editing modules
--------------------------------------------------------------------------------

The Acceleo editor is be default associated with the ``.mtl`` file extension.

Syntax Highlighting
________________________________________________________________________________
The editor uses specific colors for Acceleo templates:

- red is used for template tags
- purple is used for other tags (queries, modules, imports, ...)
- blue is used for dynamic expressions in templates or other places
- green is used for comments and String literals
- black is used for static text or query bodies


Content Assistant
________________________________________________________________________________

The content assistant is traditionally invoked with *Ctrl + space*.
It proposes a choice of all elements that make sense at the place of invocation.
It is available everywhere, so don't hesitate to hit *Ctrl + space* anywhere!

Comments
________________________________________________________________________________

Comments are entered in ``[comment]`` blocks, and appear in some shade of green.
The ``[comment]`` tag can be self closed:

``[comment Here is some comment of utter importance/]``

But you can also surround a block of text between comment tags:

::

	[comment]
	Here is some commented text, which cannot contain a right bracket character.
	[/comment]

Such comments are part of the official MTL specification. They cannot be placed
inside OCL code, such as queries for instance.

It is possible to comment some portion of code or text of an Acceleo module
with the *Ctrl + /* shortcut, or by right-clicking on the selected text and then
*Source > Comment*.
The behavior depends on what is selected:
If nothing is selected, the current line (not the line you right-clicked on, but
the line where the cursor is located) is surrounded with
``[comment]...[\comment]`` tags.
If some text is selected, it is surrounded with ``[comment]...[\comment]`` tags,
whether it spans multiple lines or not.

Code Folding
________________________________________________________________________________

Templates, comments, queries, can be folded thanks to a marker located in the
left margin in front of each of these elements.

Outlines
--------------------------------------------------------------------------------

The Dynamic Outline
________________________________________________________________________________

The traditional eclipse outline view is used be Acceleo to display the module's
structure. imports, templates, queries can be seen there, and double-clicking on
any of them places the cursor at the corresponding position in the module (in
the editor).

The Quick Outline
________________________________________________________________________________

The quick outline, which can be displayed using *Ctrl + O*, displays just the
necessary information to access any element in the current module.
So, hitting *Ctrl + O* displays a pop-up with a list of templates and queries.
A filter at the top allows you to quickly filter the content in order to easily
find what you are looking for. 

Navigating Acceleo Modules
--------------------------------------------------------------------------------

One of the great benefits of modern IDE tooling is the capacity to easily
navigate in code from elements to their declarations and, vice-versa, from
declarations to usages.

These features are available in Acceleo.

Open Declaration
________________________________________________________________________________

The traditional shortcut *F3* is supported by Acceleo, along with *Ctrl + click*,
which both take you to the declaration of the selected or clicked element.
This is supported for all kinds of elements: templates, queries, metamodels,
metamodel elements, EOperations, etc.

This can also be achieve by right-clicking on an element, then *Open
Declaration*.

Search References
________________________________________________________________________________

Conversely, it is possible to get all the elements that refer to a given element.
The shortcut is *Ctrl + Shift + G*, but it can also be achieved by right-clicking
on the element then *Search References*.

The relevant elements are displayed in the Search view.

Detecting and Solving Problems
--------------------------------------------------------------------------------

Error Markers
________________________________________________________________________________

Obviously, Acceleo displays error markers when errors are detected.
Error markers also appear in the eclipse Problems view, as usual. Files with
errors also appear with an error decorator.

Just hover the marker in the editor margin with the mouse to get a tooltip to
appear with an explanation of the problem.

Quick Fixes
________________________________________________________________________________

Quick fixes are available with the shortcut *Ctrl + 1*.

Currently, quick fixes propose to create a supposedly missing template or query,
before or after the current template.

More quick fixes will be provided in the next versions of Acceleo.

Refactoring
--------------------------------------------------------------------------------

Extract as Template
________________________________________________________________________________

When you develop Acceleo templates, you will sooner or later (and probably
sooner than later) wish to extract a piece of code into a template in order to
prevent the copy/paste syndrom.

Well, don't worry : you can do it with no effort!

Just select the piece of text you want to extract in another template, and
right-click then choose *Refactor > Extract Template* (or just hit *Ctrl + Shift
+ T* ).

The selected text is then extracted in another template. Note that the selected
text needs to be consistent : you cannot of course extract text that partially
spans blocks of code and get an adequate result.

Transform to Protected Area
________________________________________________________________________________

Similarly, it is very useful to mark some code area as protected.
Rather than manually entering the ``[protected]`` blocks, you can just select
the portion of code to protect, right-click on it then choose *Source > As
Protected Area...* (or use the *Alt + Shift + P* shortcut).

The selected text is then surrounded with ``[protected]`` markers, with an
automatically inferred id.

Please note that you may have to modify the id used to make sure the marker
works like you want.

Transform to For/If Block
________________________________________________________________________________

TODO Describe this feature

Other Facilities
--------------------------------------------------------------------------------


Wrapping Java Services
________________________________________________________________________________

It is sometimes useful to invoke some java code from inside an Acceleo template.
The acceleo non-standard library provides a service `invoke` which allows just
that. The invoked java service is wrapped in an Acceleo query.

To facilitate the creation of such a wrapper, proceed like this:

#. Right-click on the package you want to create your Acceleo module in, and
   select *New > Acceleo Module File*
#. In the wizard, enter the relevant information in the usual fields, then click
   on the *Advanced >>* button
#. Check the *Initialize Content* checkbox
#. Select *Create a Java services wrapper* in the listbox below
#. Select the java file that contains the services to wrap
#. Click on the *Finish* button

That's it!

An Acceleo module is created, with a query for each service found in the
original java class.

Initializing a Project with an Example
________________________________________________________________________________

It is often useful (actually, it is recommended) to use a bottom-up approach
to develop Acceleo templates.
So, before beginning to write templates, start by prototyping your target files,
make sure they work as expected, and then you are ready to start generating them.

At that time, you'd like to import the content of some files into a new template.

#. Right-click on the package you want to create your Acceleo module in, and
   select *New > Acceleo Module File*
#. In the wizard, enter the relevant information in the usual fields, then click
   on the *Advanced >>* button
#. Check the *Initialize Content* checkbox
#. Select *Copy example content* in the listbox below (which should be selected
   by default)
#. select the file that contains the example code
#. Click on the *Finish* button

An Acceleo module is created, and the content of the example java file is copied
into this module's primary template.

Migrating Acceleo 2.x templates
________________________________________________________________________________

**Warning: This feature will probably evolve in future versions.**

TODO Describe this feature

Execution
================================================================================

Running an Acceleo Generation
--------------------------------------------------------------------------------

There are several ways of launching a generation with Acceleo. We will examine
now how to generate inside, then outside, eclipse.

Launch Acceleo Application
________________________________________________________________________________

Right-click on a ``.mtl`` Acceleo module file, then select *Run As > Launch
Acceleo Application*.

This opens a wizard to create a launch configuration if such a configuration
does not already exist for this file.
If a launch configuration already exists, the generation is launched immediately.

Launch Configurations
________________________________________________________________________________

Launch configurations for Acceleo can be created like described above, or by
opêning the *Run Configurations* window, and then right-clicking on the *Acceleo
Application* category to the left, and selecting *New* or *Duplicate*.

An acceleo launch configuration requires:

- A project (which contains the java entry point to run)
- A java class to run (contained in the above project)
- An input model
- An output folder (where every file will be generated)

Optionally, properties can be specified directly in the launch configuration.

The Generated API
________________________________________________________________________________

To help users, Acceleo creates an API able to launch an Acceleo template.
This API can easily be customized to fit your specificities -- if you have any.

It is important to note that Acceleo inspects modules to look for ``@main``
annotations in comments, and treats those templates specially.
For every module containing at meast one ``@main`` annotation, a java file
is generated beside the template. This class contains whatever plumbing code is 
needed to run the generator.

The generation can then be run by calling the main() of the class, or
instantiating it.

+----------------------------+-------------------------------------------------+
| API                        | Meaning                                         |
+============================+=================================================+
| addProperties()            | This method allows you to add properties or     |
|                            | properties files that will subsequently be      |
|                            | available during the generation                 |
+----------------------------+-------------------------------------------------+
| API                        | Meaning                                         |
+----------------------------+-------------------------------------------------+

Standalone Execution
________________________________________________________________________________


Debugging an Acceleo Generation
--------------------------------------------------------------------------------

Breakpoints
________________________________________________________________________________


Step by Step Execution
________________________________________________________________________________

Profiling an Acceleo Generation
--------------------------------------------------------------------------------

Deployment
================================================================================

Acceleo UI Launcher Projects
--------------------------------------------------------------------------------


