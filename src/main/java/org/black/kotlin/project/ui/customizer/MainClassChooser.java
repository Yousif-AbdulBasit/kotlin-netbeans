/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.black.kotlin.project.ui.customizer;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import javax.lang.model.element.TypeElement;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.netbeans.modules.csl.api.ElementHandle;
import org.openide.awt.Mnemonics;
import org.openide.awt.MouseUtils;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/** Browses and allows to choose a project's main class.
 *
 * @author  Jiri Rechtacek
 */
public class MainClassChooser extends JPanel {

    private ChangeListener changeListener;
    private String dialogSubtitle = null;
            
    /** Creates new form MainClassChooser */
    public MainClassChooser (FileObject[] sourcesRoots) {
        this (sourcesRoots, null);
    }

    public MainClassChooser (FileObject[] sourcesRoots, String subtitle) {
        dialogSubtitle = subtitle;
        initComponents();
        jMainClassList.setCellRenderer(new MainClassRenderer());
        initClassesView();
    }
    
    
    private void initClassesView () {
        jMainClassList.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
        jMainClassList.setListData (getWarmupList ());
        jMainClassList.addListSelectionListener (new ListSelectionListener () {
            public void valueChanged (ListSelectionEvent evt) {
                if (changeListener != null) {
                    changeListener.stateChanged (new ChangeEvent (evt));
                }
            }
        });
        // support for double click to finish dialog with selected class
        jMainClassList.addMouseListener (new MouseListener () {
            public void mouseClicked (MouseEvent e) {
                if (MouseUtils.isDoubleClick (e)) {
                    if (getSelectedMainClass () != null) {
                        if (changeListener != null) {
                            changeListener.stateChanged (new ChangeEvent (e));
                        }
                    }
                }
            }
            public void mousePressed (MouseEvent e) {}
            public void mouseReleased (MouseEvent e) {}
            public void mouseEntered (MouseEvent e) {}
            public void mouseExited (MouseEvent e) {}
        });
        if (dialogSubtitle != null) {
            Mnemonics.setLocalizedText (jLabel1, dialogSubtitle);
        }
    }
    
    
    private Object[] getWarmupList () {        
//        return JMManager.getManager().isScanInProgress() ?
//            new Object[] {NbBundle.getMessage (MainClassChooser.class, "LBL_ChooseMainClass_SCANNING_MESSAGE")}:
//            new Object[] {NbBundle.getMessage (MainClassChooser.class, "LBL_ChooseMainClass_WARMUP_MESSAGE")}; // NOI18N
          return new Object[] {NbBundle.getMessage (MainClassChooser.class, "LBL_ChooseMainClass_WARMUP_MESSAGE")};
    }
    
    private boolean isValidMainClassName (Object value) {
        //TODO
        return false;
    }


    /** Returns the selected main class.
     *
     * @return a binary name of class or null if no class with the main method is selected
     */    
    @SuppressWarnings("unchecked")
    public String getSelectedMainClass () {
        return "";
    }
    
    public void addChangeListener (ChangeListener l) {
        changeListener = l;
    }
    
    public void removeChangeListener (ChangeListener l) {
        changeListener = null;
    }
    
    // Used only from unit tests to suppress check of main method. If value
    // is different from null it will be returned instead.
    public static Boolean unitTestingSupport_hasMainMethodResult = null;
    
    /** Checks if given file object contains the main method.
     *
     * @param classFO file object represents java 
     * @return false if parameter is null or doesn't contain SourceCookie
     * or SourceCookie doesn't contain the main method
     */    
    public static boolean hasMainMethod (FileObject classFO) {
        //TODO
        return false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jMainClassList = new javax.swing.JList();

        setPreferredSize(new java.awt.Dimension(380, 300));

        jLabel1.setLabelFor(jMainClassList);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getBundle(MainClassChooser.class).getString("CTL_AvaialableMainClasses")); // NOI18N
        add(jLabel1);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(100, 200));
        jScrollPane1.setViewportView(jMainClassList);
        jMainClassList.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(MainClassChooser.class).getString("AD_jMainClassList")); // NOI18N

        add(jScrollPane1);

        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getBundle(MainClassChooser.class).getString("AD_MainClassChooser")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jMainClassList;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables


    private static final class MainClassRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String displayName;
            if (value instanceof String) {
                displayName = (String) value;
            } if (value instanceof TypeElement) {
                displayName = ((TypeElement) value).getQualifiedName().toString();
            } else {
                displayName = value.toString ();
            }
            return super.getListCellRendererComponent (list, displayName, index, isSelected, cellHasFocus);
        }
    }
    
    private static class MainClassComparator implements Comparator<TypeElement> {
            
        public int compare(TypeElement arg0, TypeElement arg1) {
            return arg0.getQualifiedName().toString().compareTo(arg1.getQualifiedName().toString());
        }
    }

}