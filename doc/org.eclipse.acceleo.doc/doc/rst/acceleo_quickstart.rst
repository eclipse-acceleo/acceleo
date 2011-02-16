===================
 Acceleo Quickstart
===================

:Authors:
	Laurent Goubet,
	Laurent Delaigue
:Contact:
	laurent.goubet@obeo.fr,
	laurent.delaigue@obeo.fr

Copyright |copy| 2008, 2011 Obeo\ |trade|.

.. |copy| unicode:: 0xA9 
.. |trade| unicode:: U+2122
.. contents:: Contents

The goal of this document is to help you get started with Acceleo as fast as
possible. No details, no explanations, just the facts!

We will assume you already have Acceleo installed in Eclipse; Installation
instructions can be consulted on the
`official site <http://www.eclipse.org/acceleo/download/>`_. Eclipse itself
can be downloaded from
`the Eclipse download site <http://www.eclipse.org/downloads/>`_.

First of all, switch to the Acceleo perspective, which will make everything
easier. Open the *Window* menu, then *Open Perspective > Other...* and choose
Acceleo.

Create an Acceleo project, which will contain your first "Hello world"
generator. Right-click in the package explorer, then select *New > Acceleo
Project*.

.. image:: ../images/acceleo_new_project.png

Call it ``org.eclipse.acceleo.helloworld`` or whichever name you like better, and
click *Next >* then *Finish* on the next screen. By default, an Acceleo template
called ``generate.mtl`` is created and opened.

Edit it so that it looks like the following (simply insert some text for the
purpose of the demonstration):

.. image:: ../images/acceleo_quickstart_module.png

Now, you need to run this template. In order to evaluate a generation module, you need to
provide Acceleo with two pieces of information:

- The input model;
- The output folder.

Here we want to use an ecore model, so we'll create one very quickly. Of course,
models are usually produced by software designers, architects, or developers,
and generally not by the same people that write Acceleo modules.

So, let's create a new project since there's no reason for our models to be in
the same project as our generator (in fact, a good practice would be to separate
models from templates). Right-click in the package explorer, and select
*New > Project...*, then *Java Project*.

Click on Next and enter the name of the project that will
contain the model and the java code generated from this model. Something like
``org.eclipse.acceleo.test`` will be perfect! Do *not* switch to the java
perspective when asked to.

Now let's create our ecore model. Right-click on the new project, and select *New
> Other...*.

.. image:: ../images/acceleo_quickstart_uml_new0.png

In the wizard's page, type "ecore" in the top field to filter the choices, and
select "Ecore Model". Click *Next >*.

.. image:: ../images/acceleo_quickstart_uml_new1.png

Give a name to the ecore model to create, for instance ``Sample.ecore``. Click
*Next >*.

.. image:: ../images/acceleo_quickstart_uml_new2.png

Select the root type to use for your model, here we will use "EPackage".
Click *Finish*; this should create and open a new ecore model.

.. image:: ../images/acceleo_quickstart_uml_new3.png

Now add one or more classes to the model. To do this, either you have a graphical
ecore editor such as *Ecore Tools* installed, in which case you can use it,
or follow these instructions:

- Right-click on the root package and select *Show Properties View*

  .. image:: ../images/acceleo_quickstart_properties_view.png

- In the properties view, change the value of the ``name`` property to any name
  you wish for the package. Here we'll make it be ``tutorial``.

  .. image:: ../images/acceleo_quickstart_package_name.png

- Right-click on the package and select *New Child > EClass*;

  .. image:: ../images/acceleo_quickstart_uml_new_class.png

- In the properties view, enter a name for the new class, we'll name it ``Acceleo``
  in this example.

  .. image:: ../images/acceleo_quickstart_class_name.png

Repeat these steps to create as many classes as desired.

Of course, this model is just a dummy to get you started. In real life, models will
contain lots of information, but it is not our purpose here and now.

So you should now have a model that looks like this:

.. image:: ../images/acceleo_quickstart_model.png

Let's run our generator against this model!

Right-click on the Acceleo module file (that is, the ``generate.mtl`` file) and
select *Run As > Launch Acceleo Application*.

.. image:: ../images/acceleo_quickstart_launch1.png

The Acceleo launch configuration page opens, and you need to provide the
information mentionned earlier.

.. image:: ../images/acceleo_quickstart_launch2.png

Use the *Browse...* buttons to easily select the model and the destination
folder (you will need to use ``*.ecore`` as filter instead of the default ``*.xmi`` in
order to locate your model file).

The launch configuration page should now look like this:

.. image:: ../images/acceleo_quickstart_launch3.png

Click on the *Run* button.
A file named "Acceleo.txt" (or whatever name you used for your ecore class) appears
in the target folder.

You can open it from the package explorer. Here is its content:

.. image:: ../images/acceleo_quickstart_output_file.png

It contains the text ``Hello, Acceleo world!``, in which ``Acceleo`` has been
dynamically extracted from the ecore model (it is the class name), while the
rest of the text is statically written in the template itself.

Now it's time to experiment by yourself! You should probably read the
`First Generator <first_acceleo_module.html>`_ tutorial to further your comprehension
but for now you know enough to get to know Acceleo by playing around with it a bit.
