/* $Id: TestMDRModelImplementationCreate.java 17767 2010-01-11 21:21:22Z linus $
 *****************************************************************************
 * Copyright (c) 2009 Contributors - see below
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    linus
 *****************************************************************************
 *
 * Some portions of this file was previously release using the BSD License:
 */

// Copyright (c) 2005-2007 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.model.mdr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import javax.jmi.xmi.XmiReader;

import junit.framework.TestCase;

import org.argouml.model.Facade;
import org.argouml.model.XmiReader;
import org.omg.uml.behavioralelements.commonbehavior.UmlException;
import org.omg.uml.foundation.core.UmlClass;
import org.argouml.model.Model;
import org.omg.uml.modelmanagement.UmlPackage;
import org.xml.sax.InputSource;
//import org.omg.uml.modelmanagement.Model;

/**
 * Testing the set up of the MDR.
 */
public class TestMDRModelImplementationCreate extends TestCase {

    /**
     * Constructor for TestMDRModelImplementation.
     * 
     * @param arg0
     *            Test case name.
     */
    public TestMDRModelImplementationCreate(String arg0) {
        super(arg0);
    }

    /**
     * Tests the constructor.
     * 
     * @throws UmlException
     *             if model subsystem initialization fails.
     * @throws FileNotFoundException
     *             If the test XMI file can't be found.
     * @throws org.argouml.model.UmlException 
     */
    public void testMDRModelImplementation() throws
            FileNotFoundException, org.argouml.model.UmlException {
        MDRModelImplementation mi = new MDRModelImplementation();
        assertNotNull(mi.getFacade());
        org.omg.uml.modelmanagement.Model m = (org.omg.uml.modelmanagement.Model) mi.getModelManagementFactory().createModel();
        assertNotNull(m);
        UmlPackage p = (UmlPackage) mi.getModelManagementFactory().
                createPackage();
        mi.getCoreHelper().setNamespace(p, m);
        UmlClass c = (UmlClass) mi.getCoreFactory().buildClass(m);
        org.omg.uml.modelmanagement.Model m1 = (org.omg.uml.modelmanagement.Model) mi.getFacade().getRoot(c);
        assertNotNull(m1);
        org.omg.uml.modelmanagement.Model m2 = (org.omg.uml.modelmanagement.Model) mi.getFacade().getRoot(p);
        assertNotNull(m2);
        assertEquals(m1, m);
        assertEquals(m2, m);
        XmiReader xmiReader = mi.getXmiReader();
        URL modelUrl = getClass().getClassLoader().getResource(
                "testmodels/test.xmi");
        assertNotNull(modelUrl);
        File fileModel = new File(modelUrl.getPath());
        assertTrue(fileModel.exists());
        /***** REFERENCE STARTS HERE ******/
        InputSource source = new InputSource(new FileInputStream(new File("/home/prat0318/Downloads/ParentChild.xmi")));        
        XmiReader reader = null;
		Model.initialise("org.argouml.model.mdr.MDRModelImplementation");
        reader = Model.getXmiReader();
            List<String> searchPath = reader.getSearchPath();
        String pathList =
            System.getProperty("org.argouml.model.modules_search_path");
        if (pathList != null) {
            String[] paths = pathList.split(",");
            for (String path : paths) {
                if (!searchPath.contains(path)) {
                    reader.addSearchPath(path);
                }
            }
        }
        reader.addSearchPath(source.getSystemId());

       Collection elementsRead = reader.parse(source, false);
        if (elementsRead != null && !elementsRead.isEmpty()) {
            Facade facade = Model.getFacade();
            Object current;
            Iterator elements = elementsRead.iterator();
            while (elements.hasNext()) {
                current = elements.next();
                List contents = facade.getModelElementContents(current);
                System.out.print("dbname("+facade.getName(current).toLowerCase() + ", [");
                Map<String, String> classMapping = new HashMap<String, String>();
                int classId = 0; int index = 0;
                for(Object item: contents){
                    if(facade.isAClass(item)){
                    	if(index != 0) System.out.print(", ");
                    	index++;
                        System.out.print(facade.getName(item).toLowerCase());
                        classMapping.put(facade.getName(item), "class_"+(++classId));
                    }                    
                }
                System.out.println("]).");
                int attrIndex = 0;
                for(Object item: contents){
                    if(facade.isAClass(item)){
                        String className = facade.getName(item);
                    	String parent = "null";
                        Collection parents = facade.getGeneralizations(item);
                        for(Object pp : parents) {
                        	parent = classMapping.get(facade.getName(facade.getGeneral(pp)));
                        }

                        System.out.println("class("+classMapping.get(className)+", "+className.toLowerCase()+", "+facade.getVisibility(item)+", "+parent+").");
                        List items1 = facade.getModelElementContents(item);
                        for(Object item1: items1){
                            System.out.println("attribute("+"attr_"+(++attrIndex)+", "+facade.getName(item1)+", "+facade.getVisibility(item1)+").");
                        }
                        System.out.println();
                    }
                }
                int ass_index = 0;
                int ass_end_index = 0;
                for(Object item: contents){
                    if(facade.isAAssociation(item)){
                        String assoc_id = "assoc_"+(++ass_index);
                        System.out.println("composition("+ assoc_id +", "+facade.getName(item).toLowerCase()+").");
                        Collection items1 = facade.getModelElementContents(item);
                        for(Object item1: items1){
                            Object classifier = facade.getClassifier(item1);
                            String lower = (Integer)facade.getLower(item1) == -1 ? "inf" : ((Integer)(facade.getLower(item1))).toString();
                            String upper = (Integer)facade.getUpper(item1) == -1 ? "inf" : ((Integer)(facade.getUpper(item1))).toString();
                            System.out.println("association("+"assoc_end_"+
                            (++ass_end_index)+", "+assoc_id+ ", "+classMapping.get(facade.getName(classifier).toLowerCase())+
                            ", \""+lower+".."+upper+"\").");
                        }
                        System.out.println();
                    }
                }
            }
        }
        /***** REFERENCE ENDS HERE ******/
        source = new InputSource(new FileInputStream(fileModel));  
        Collection modelElements = xmiReader.parse(source, false);
        assertNotNull(modelElements);
        assertEquals(1, modelElements.size());
        Object o = modelElements.iterator().next();
        assertNotNull(o);
    }

}
