===================================
 Acceleo Plug-ins Features Overview
===================================

:Authors: Laurent Goubet
:Contact: Laurent.Goubet@obeo.fr

Copyright |copy| 2008, 2010 Obeo\ |trade|.

.. |copy| unicode:: 0xA9 
.. |trade| unicode:: U+2122

.. contents:: Contents

Introduction
============

This document provides an overview of the user-visible features of the Acceleo plug-ins which make it easy to develop
and run Acceleo modules inside Eclipse. It assumes some familiarity with the language itself, although you do not need
to be an expert to start using the plug-ins: using the provided example projects and the powerful completion feature
of the Acceleo editor, it is very easy to get started once you understand the basic principles.

Regarding the language, almost all of the OMG specification keywords are supported (those marked with an asterisk are
not fully supported yet):

::
 
  module, import, extends, template, query, public, private, protected, guard, init,
  overrides, each, before, after, for, if, elseif, else, let, elselet, trace*, macro*,
  file, mode, text_explicit*, code_explicit*, super, stdout

For all the details about these keywords and the MOF Model to Text Language in general, you can consult
`the official MTL Specification <http://www.omg.org/spec/MOFM2T/1.0/>`_ available at the OMG website.

back to Contents_

Requirements
============

Acceleo depends on:

#. EMF runtime ( 2.4.X and later )
#. OCL runtime ( 1.2.X and later )

back to Contents_

Acceleo Modules and Projects
============================

The Acceleo plugins come with a perspective called Acceleo. To open this perspective, select *Window -> Open perspective
-> Others*, then click on *Acceleo*.

  .. image:: ../images/acceleo_perspective.png

To create a new Acceleo project, open the *New project* Eclipse wizard, and in the *Model to Text Transformation*
category select *Acceleo Project*. On the next page, enter the project name. You can then click *Finish* directly to
create an empty Acceleo project, or go to the next page to create one or several initial module files:

  .. image:: ../images/acceleo_new_template_wizard.png

In the *Module information* section, you can specify the location and name of the new *.mtl* file to create. In this
section, you can also initialize the contents of the new module file with either a fixed example file (*Copy example
content*), or an existing Acceleo or Xpand template (respectively *Migrate .mt content* and *Migrate .xpt content*).

The *Metamodel information* section allows you to specify the input metamodel of your new module file, either from the
list of registered metamodels or directly by URI. You can also specify the input type of the sample template that will
be created inside this module file.

Once you have filled in all required information, you can either click *Finish* to create the project in the workspace
or use the **Add** button at the lower end of the *module files* section to add more initial modules to this project.

Note that Acceleo projects are also Eclipse Plug-in projects. Acceleo modules themselves are stored inside the
project's source folder, following the same conventions as Java files: a module whose qualified name is
*com::example::acceleo::myModule* must be stored in the file *com/example/acceleo/myModule.mtl* in one of the
project's source folders.

  .. image:: ../images/acceleo_project-structure.png

back to Contents_

Acceleo Editor Features
=======================

The Acceleo editor is associated with Acceleo source files (*.mtl* files) and provides all the features of a modern
programming editor to make you more productive when developing modules:

- Syntax highlighting
- Content assistant (ctrl + space)
- Error detection
- Quick fixes (ctrl + shift + 1)
- Dynamic outline
- Quick outline (ctrl + O)
- Code folding
- Open declaration (either with 'ctrl + left click' or 'F3')
- Search references (ctrl + shift + G)

  .. image:: ../images/acceleo_editor_overview.png

Some of these features are detailed below.

back to Contents_

Smart Completion on Acceleo Syntax Constructs
---------------------------------------------

The Acceleo editor provides smart completion on all of the available Acceleo language constructs.
It is activated with the *Ctrl+Space* key combination by default.

The completion mechanism is smart enough to only present you the syntactically correct choices in the context where it
is invoked. For example, when invoked at the top-level of a file (outside of templates, macros, queries, ...), only the
top-level Acceleo constructs are shown:

  .. image:: ../images/acceleo_completion-top-level.png

The context is also used to sort the proposed choices. For example, inside an *[if]* block, the *[elseif]* and *[else]*
choices appear at the top of the completion proposals:

  .. image:: ../images/acceleo_completion-if.png

Note that although the choices are presented as keywords inside brackets (e.g. *[for]*, *[file]*, etc.), you do not
have to type the opening bracket but can simply type the first letters:

  .. image:: ../images/acceleo_completion-for.png

back to Contents_

Smart Completion on Scripts and Metamodel Elements
---------------------------------------------------

Smart completion also works inside Acceleo expressions, where it can be invoked using *Ctrl+Space*. It is also
auto-activated after a small delay in some circumstances, like after a dot (*.*).

Inside expressions, completion choices include metamodel elements that are compatible with the context and visible
Acceleo elements (variables, templates, queries and modules) alike.

In the example below, the *p* variable is a UML property, so completion on the partial expression *p.n* proposes both
features and operations of the UML2 *Property* class which start with *n*:

  .. image:: ../images/acceleo_completion-mm.png

Starting from an empty expression, the completion proposes all visible Acceleo variables and templates which can be
invoked in addition to features and operations of the current element (the latter not visible on the screenshot):

  .. image:: ../images/acceleo_completion.png

back to Contents_

Open Declaration
----------------

The Acceleo editor also supports the "Open Declaration" feature (*F3* on selection, or *Ctrl+left click* on any
element), which allows you to easily navigate from any element to its definition. This works for most if not all
elements you can find in Acceleo module files: variables, modules, templates, queries, types, features and operations
of metamodel elements, even the metamodel themselves from their URI.

back to Contents_

Search References
-----------------

The "Search references" feature can be seen as the reverse of "Open Declaration": instead of navigating from one
specific usage of an element to its definition, it searches for all usages of a particular element. This feature is
invoked by default using the *Ctrl+Shift+g* key combination. It can be used either from the definition of an element
(a variable, template, query, etc.) or any particular usage of it.

In the example below, the cursor was on the definition of the iteration variable *p* in a *for* loop. Invoking
"Search references" by pressing *Ctrl+Shift+g* opened the Eclipse search view with all the references to this variable
inside the current module, and potential matches in other modules of the same project:

  .. image:: ../images/acceleo_search-references-1.png

As for "Open Declaration", the "Search References" feature works for all kind of elements: Acceleo variables, templates,
queries and modules, metamodel types, features and operations.

back to Contents_

Running and debugging Acceleo modules
=====================================

Acceleo modules can be run and debugged very easily using the standard Eclipse mechanisms.

Inside your Acceleo modules, you can annotate some of your templates using a special comment *[comment @main /]*. This
identifies these templates as entry points for the generation. When a module contains at least one such entry point, the
Acceleo project builder automatically creates a Java class which can be used to launch the generation.

That Java class contains a *main()* method which can be used to invoke the generation with that module. It can be called
by itself as a normal Java program (including as a standalone application, outside of Eclipse), or using the more
convenient *Acceleo Application* launcher. Such launchers require two input arguments:

- the input model (for example a *.uml* file)
- the target folder

back to Contents_

Launching a Module
------------------

To launch a generation of an Acceleo module, open the Eclipse *Run configurations* dialog, and create a new
configuration of type *Acceleo Application*.

  .. image:: ../images/acceleo_launch-configuration.png

You can also right-click on a *.mtl* file and select *Run as > Acceleo Application* to open the same dialog with some
of the information already configured to run this template.

The mandatory parameters are:

Project
  The project containing the module to use
  
Main class
  The Java class generated from your module (if it contains entry points)

Model
  The input model for the generation

Target
  The target folder in which to generate files

Properties
  If the templates which serve as entry points to the generation expect arguments, you can specify them in this text
  area, one per line.

Runner
  you have the choice between *Acceleo Plug-in Application* (the default) and *Java Application*. Using the *Java
  Application* runner is strictly equivalent to invoking the generated Java class with the input model file and target
  folder as parameters. This is useful to check that your generator runs correctly in *standalone mode* (outside of
  Eclipse), but in this mode it is not possible to debug the Acceleo templates as such. On the other hand, because the
  *Acceleo Plug-in Application* runner is aware that it is executing an Acceleo generation, it gives you access to the
  Acceleo template debugger described in the next section.

Once you have specified all the required information, the configuration can be invoked like any Eclipse launch
configuration.

back to Contents_

Debugging your Modules
----------------------

The Acceleo plug-ins also include a debugger for your Acceleo modules. The debugger allows you to set breakpoints
inside your Acceleo templates and thus follow their execution step by step.

To debug an Acceleo module, follow the same steps as described above to create a launch configuration, but use the
*Debug as* menu instead of *Run as*. Once you have a launch configuration, it can be invoked either as a normal launch
or as a debug launch.

To put a breakpoint in a template, simply double-click inside the left margin of the Acceleo editor on the target line.
Note that you can only put breakpoints on lines which contain Acceleo expressions, not on lines which only contain fixed
text output.

The Acceleo debugger has some support for conditional breakpoints: once you have set a breakpoint, simply click on it
in the left margin. A dialog box appears where you can enter a name pattern:

  .. image:: ../images/acceleo_breakpoint_condition.png

Once you have set a name pattern, the breakpoint will only be triggered on elements whose name match the pattern
(please note that breakpoint conditions are currently only taken into account for the next debug session).

When you launch a debug session on your module, if you have breakpoints and they are enabled, the generation will pause
when they are hit. If you then switch to the *Debug* perspective, you should see the familiar debug views:

  .. image:: ../images/acceleo_debugger.png

In the *Debug* view itself (top left), you can see the current template call stack. If you select a frame, the Acceleo
editor (below) will open on the corresponding file and line. The *Variables* view (top right) allows you to inspect the
current *self* object for the expression being executed.

As in Java, you can use the *Step Into*, *Step Over* and *Step Return* actions to execute the template step by step,
*Resume* the execution until the end or another breakpoint is hit, or simply *Stop* the session.

back to Contents_
