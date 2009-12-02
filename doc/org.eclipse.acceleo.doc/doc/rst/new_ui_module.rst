=======================
 Acceleo Tutorial
=======================

:Authors: Laurent Goubet
:Contact: laurent.goubet@obeo.fr

Copyright |copy| 2008, Obeo\ |trade|.

.. |copy| unicode:: 0xA9 
.. |trade| unicode:: U+2122

New Acceleo UI project
======================

Once your templates are ready, you may want to have some wizards to launch the generation from eclipse.
You can use the ant tasks or the java code, but you can also use the *New Acceleo UI project* wizard.
This wizard will create a new eclipse ui project which will allow the user to launch the generation via an eclipse action.
The example below shows the default result of this plugin, a new *generate java* action on the *.uml* files.

.. image:: ../images/new_acceleo_module_ui_project_result.png

First, right click on the Acceleo generator project then *New->Acceleo Module UI Project*.

.. image:: ../images/new_acceleo_module_ui_project.png

Choose a correct plugin name for the project, then click next.

.. image:: ../images/new_acceleo_module_ui_project_1.png

Choose the generator project as referenced project, then click next.

.. image:: ../images/new_acceleo_module_ui_project_2.png

Lastly, modify the model filename filter, and the java code for the target folder.

.. image:: ../images/new_acceleo_module_ui_project_3.png

The wizard will create a new plugin with all the necessary code to have a new action for the selected model file that will generate code inside the specified folder.
